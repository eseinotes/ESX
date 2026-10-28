package edu.example.studentperformance;

import java.io.IOException;
import java.util.List;

/**
 * Contiene las reglas de negocio y coordina el repositorio con el servicio de predicción.
 */
public class StudentService {
    private final StudentRepository repository;
    private final PredictionClient predictionClient;

    public StudentService(StudentRepository repository, PredictionClient predictionClient) {
        this.repository = repository;
        this.predictionClient = predictionClient;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudent(int id) {
        return repository.findById(id);
    }

    public Student createStudent(Student student) {
        // La validación pertenece al servicio porque es una regla de la aplicación.
        if (!isValid(student)) {
            return null;
        }
        return repository.create(student);
    }

    public Student updateStudent(int id, Student student) {
        if (repository.findById(id) == null) {
            return null;
        }
        if (!isValid(student)) {
            return null;
        }
        return repository.update(id, student);
    }

    public boolean deleteStudent(int id) {
        return repository.delete(id);
    }

    public double predictGrade(int id) throws IOException, InterruptedException {
        Student student = repository.findById(id);
        if (student == null) {
            return -1;
        }

        // Python solo necesita las horas de estudio para aplicar la fórmula ficticia.
        return predictionClient.requestPrediction(student.getStudyHours());
    }

    /**
     * Aplica las reglas mínimas compartidas por la creación y la actualización.
     */
    private boolean isValid(Student student) {
        if (student == null) {
            return false;
        }
        if (student.getName() == null) {
            return false;
        }
        if (student.getName().trim().isEmpty()) {
            return false;
        }
        return student.getStudyHours() >= 0;
    }
}
