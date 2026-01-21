FROM python:3.9-slim

WORKDIR /app

COPY services/grade-service/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY services/grade-service/ .

EXPOSE 8000

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]