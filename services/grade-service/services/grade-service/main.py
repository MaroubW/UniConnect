from fastapi import FastAPI, HTTPException, Depends
from fastapi.middleware.cors import CORSMiddleware
from motor.motor_asyncio import AsyncIOMotorDatabase
from typing import List
from database import get_database
from models import Grade
from schemas import Grade as GradeSchema, GradeCreate, AverageResponse
from bson import ObjectId
from bson.errors import InvalidId

app = FastAPI(title="Grade Service", description="Service for managing student grades")

# ✅ CORS (so your HTML frontend can call the API)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # dev only
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

def to_object_id(id_str: str) -> ObjectId:
    try:
        return ObjectId(id_str)
    except InvalidId:
        raise HTTPException(status_code=400, detail="Invalid grade id (must be a Mongo ObjectId)")

async def ensure_student_exists(student_number: str, db: AsyncIOMotorDatabase) -> None:
    # Your collection name is "student" and the field is "studentNumber"
    student = await db.students.find_one({"studentNumber": student_number}, {"_id": 1})
    if not student:
        raise HTTPException(status_code=404, detail="Student not found")

@app.get("/")
def root():
    return {"status": "ok", "service": "grade-service"}

# ✅ Student numbers come ONLY from students collection
@app.get("/students/numbers", response_model=List[str])
async def list_student_numbers(db: AsyncIOMotorDatabase = Depends(get_database)):
    nums: List[str] = []
    cursor = db.students.find({}, {"studentNumber": 1, "_id": 0})
    async for s in cursor:
        sn = s.get("studentNumber")
        if sn:
            nums.append(sn)
    nums.sort()
    return nums

@app.get("/grades/", response_model=List[GradeSchema])
async def read_grades(skip: int = 0, limit: int = 100, db: AsyncIOMotorDatabase = Depends(get_database)):
    grades = []
    cursor = db.grades.find().skip(skip).limit(limit)
    async for grade in cursor:
        grade["id"] = str(grade.pop("_id"))
        grades.append(GradeSchema(**grade))
    return grades

@app.get("/grades/{grade_id}", response_model=GradeSchema)
async def read_grade(grade_id: str, db: AsyncIOMotorDatabase = Depends(get_database)):
    oid = to_object_id(grade_id)
    grade = await db.grades.find_one({"_id": oid})
    if grade is None:
        raise HTTPException(status_code=404, detail="Grade not found")
    grade["id"] = str(grade.pop("_id"))
    return GradeSchema(**grade)

# ✅ Create grade: validate that studentNumber exists in students collection
@app.post("/grades/", response_model=GradeSchema)
async def create_grade(grade: GradeCreate, db: AsyncIOMotorDatabase = Depends(get_database)):
    await ensure_student_exists(grade.student_id, db)

    grade_doc = Grade(**grade.dict())
    result = await db.grades.insert_one(grade_doc.dict(by_alias=True))

    created = await db.grades.find_one({"_id": result.inserted_id})
    created["id"] = str(created.pop("_id"))
    return GradeSchema(**created)

# ✅ Update grade: validate student exists too
@app.put("/grades/{grade_id}", response_model=GradeSchema)
async def update_grade(grade_id: str, grade: GradeCreate, db: AsyncIOMotorDatabase = Depends(get_database)):
    await ensure_student_exists(grade.student_id, db)
    oid = to_object_id(grade_id)

    update_result = await db.grades.update_one(
        {"_id": oid},
        {"$set": grade.dict()}
    )
    if update_result.matched_count == 0:
        raise HTTPException(status_code=404, detail="Grade not found")

    updated = await db.grades.find_one({"_id": oid})
    updated["id"] = str(updated.pop("_id"))
    return GradeSchema(**updated)

@app.delete("/grades/{grade_id}")
async def delete_grade(grade_id: str, db: AsyncIOMotorDatabase = Depends(get_database)):
    oid = to_object_id(grade_id)
    delete_result = await db.grades.delete_one({"_id": oid})
    if delete_result.deleted_count == 0:
        raise HTTPException(status_code=404, detail="Grade not found")
    return {"message": "Grade deleted"}

# ✅ Average: studentNumber validated from students collection, grades come from grades collection
@app.get("/grades/student/{student_id}/average", response_model=AverageResponse)
async def get_student_average(student_id: str, db: AsyncIOMotorDatabase = Depends(get_database)):
    await ensure_student_exists(student_id, db)

    values: List[float] = []
    cursor = db.grades.find({"student_id": student_id}, {"grade_value": 1, "_id": 0})
    async for g in cursor:
        if "grade_value" in g:
            values.append(g["grade_value"])

    if not values:
        raise HTTPException(status_code=404, detail="No grades found for this student")

    average = sum(values) / len(values)
    return AverageResponse(student_id=student_id, average=average)
