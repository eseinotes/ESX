from fastapi import FastAPI
from pydantic import BaseModel, Field

from .predictor import calculate_predicted_grade


app = FastAPI(title="Student Prediction Service")


class PredictionRequest(BaseModel):
    study_hours: float = Field(ge=0)


class PredictionResponse(BaseModel):
    predicted_grade: float


@app.post("/predict", response_model=PredictionResponse)
def predict_grade(request: PredictionRequest) -> PredictionResponse:
    """Expone la fórmula ficticia mediante una operación HTTP sencilla."""
    predicted_grade = calculate_predicted_grade(request.study_hours)
    return PredictionResponse(predicted_grade=predicted_grade)
