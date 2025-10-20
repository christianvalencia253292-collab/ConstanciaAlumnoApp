package edu.itson.constancia.dao;

import edu.itson.constancia.model.Alumno;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryAlumnoDAO implements AlumnoDAO {

    private final List<Alumno> data = new ArrayList<Alumno>();

    public InMemoryAlumnoDAO() {
        // Datos de ejemplo (ajustables)
        data.add(new Alumno("253292", "Christian Daniel Valencia Patron", 5, 6));
        data.add(new Alumno("252347", "Alejandro Erro Gonzalez", 6, 5));
        data.add(new Alumno("253170", "Paul Candelario Seanez Villalobos", 4, 5));
        data.add(new Alumno("250001", "Rubi Esmeralda Reyna Jusaino", 3, 5));
        data.add(new Alumno("250002", "Jesus Emmanuel Valenzuela Esquer", 7, 4));
    }

    public List<Alumno> findByIdPrefix(String prefix) {
        String p = prefix == null ? "" : prefix.trim();
        List<Alumno> result = new ArrayList<Alumno>();
        for (Alumno a : data) {
            if (a.getId().startsWith(p)) {
                result.add(a);
                if (result.size() >= 20) break;
            }
        }
        return result;
    }

    public Alumno findById(String id) {
        if (id == null) return null;
        String x = id.trim();
        for (Alumno a : data) {
            if (Objects.equals(a.getId(), x)) return a;
        }
        return null;
    }
}
