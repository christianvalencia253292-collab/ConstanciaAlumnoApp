package edu.itson.constancia.dao;

import edu.itson.constancia.model.Alumno;
import java.util.List;

public interface AlumnoDAO {
    List<Alumno> findByIdPrefix(String prefix);
    Alumno findById(String id);
}
