package edu.itson.constancia.model;

/** Entidad de dominio simple para el caso de uso. */
public class Alumno {
    private final String id;
    private final String nombre;
    private final int semestre;
    private final int materiasInscritas;

    public Alumno(String id, String nombre, int semestre, int materiasInscritas) {
        this.id = id;
        this.nombre = nombre;
        this.semestre = semestre;
        this.materiasInscritas = materiasInscritas;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getSemestre() { return semestre; }
    public int getMateriasInscritas() { return materiasInscritas; }

    @Override
    public String toString() {
        return id + " — " + nombre;
    }
}
