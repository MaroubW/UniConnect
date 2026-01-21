from motor.motor_asyncio import AsyncIOMotorClient
from pymongo.database import Database

MONGODB_URL = "mongodb://localhost:27017"
DATABASE_NAME = "soa_project"

client = AsyncIOMotorClient(MONGODB_URL)
database: Database = client[DATABASE_NAME]

async def get_database():
    return database