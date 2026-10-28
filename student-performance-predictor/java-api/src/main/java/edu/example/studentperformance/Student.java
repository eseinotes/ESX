package edu.example.studentperformance;

/**
 * Representa los datos de un estudiante sin contener lógica HTTP ni de almacenamiento.
 * El constructor vacío permite que Gson cree objetos a partir de JSON.
 */
public class Student {
    private int id;
    private String name;
    private double studyHours;

    public Student() {
    }

    public Student(int id, String name, double studyHours) {
        this.id = id;
        this.name = name;
        this.studyHours = studyHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStudyHours() {
        return studyHours;
    }

    public void setStudyHours(double studyHours) {
        this.studyHours = studyHours;
    }
}
