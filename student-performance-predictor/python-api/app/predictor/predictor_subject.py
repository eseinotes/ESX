def calculate_predicted_grade(study_hours: float) -> float:
    """Calcula una nota ficticia para demostrar la separación de servicios."""
    predicted_grade = study_hours * 0.15
    if predicted_grade > 10.0:
        return 10.0
    return predicted_grade
