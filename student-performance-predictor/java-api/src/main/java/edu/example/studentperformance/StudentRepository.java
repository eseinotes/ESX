package edu.example.studentperformance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mantiene los estudiantes en memoria y concentra las operaciones de almacenamiento.
 */
public class StudentRepository {
    private final Map<Integer, Student> students;
    private int nextId;

    public StudentRepository() {
        students = new HashMap<Integer, Student>();
        nextId = 1;
    }

    /**
     * Devuelve una lista nueva para no exponer directamente la colección interna.
     */
    public List<Student> findAll() {
        return new ArrayList<Student>(students.values());
    }

    public Student findById(int id) {
        return students.get(id);
    }

    public Student create(Student student) {
        // El repositorio asigna el identificador porque es responsable del almacenamiento.
        student.setId(nextId);
        nextId = nextId + 1;
        students.put(student.getId(), student);
        return student;
    }

    public Student update(int id, Student student) {
        // El identificador de la URL prevalece sobre cualquier valor recibido en el JSON.
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public boolean delete(int id) {
        Student removedStudent = students.remove(id);
        return removedStudent != null;
    }
}
