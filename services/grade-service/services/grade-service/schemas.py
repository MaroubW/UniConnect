from pydantic import BaseModel
from datetime import datetime

class GradeBase(BaseModel):
    # ✅ studentNumber format
    student_id: str
    course_id: int
    grade_value: float

class GradeCreate(GradeBase):
    pass

class Grade(GradeBase):
    id: str
    date_assigned: datetime

    class Config:
        from_attributes = True

class AverageResponse(BaseModel):
    student_id: str
    average: float
