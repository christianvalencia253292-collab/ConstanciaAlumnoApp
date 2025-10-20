package edu.itson.constancia.service;

import edu.itson.constancia.dao.AlumnoDAO;
import edu.itson.constancia.model.Alumno;
import java.util.List;

/**
 * Lógica de negocio del caso de uso: Generar Constancia.
 * - Búsqueda por prefijo de ID
 * - Consulta por ID
 * - Render de constancia (HTML simple)
 */
public class AlumnoService {

    private final AlumnoDAO dao;

    public AlumnoService(final AlumnoDAO dao) {
        this.dao = dao;
    }

    public List<Alumno> buscarPorPrefijo(final String prefijo) {
        return dao.findByIdPrefix(prefijo);
    }

    public Alumno obtenerPorId(final String id) {
        return dao.findById(id);
    }

    public String generarConstancia(final Alumno a) {
        if (a == null) {
            return "<p>No se encontró el alumno.</p>";
        }
        final String nombre   = a.getNombre();
        final String id       = a.getId();
        final int semestre    = a.getSemestre();
        final int materias    = a.getMateriasInscritas();

        StringBuilder sb = new StringBuilder(512);
        sb.append("<html><body style='font-family: Arial, sans-serif'>")
          .append("<div style='border:1px dashed #999; padding:10px; border-radius:8px'>")
          .append("<h2 style='text-align:center'>CONSTANCIA DE ALUMNO INSCRITO</h2>")
          .append("<p>Por medio de la presente se hace constar que:</p>")
          .append("<p><b>").append(escape(nombre)).append("</b> (ID: ").append(escape(id)).append(")</p>")
          .append("<p>se encuentra inscrito en el <b>").append(semestre)
          .append("&ordm; semestre</b>, cursando <b>").append(materias).append("</b> materias.</p>")
          .append("<p>Se expide la presente a solicitud del interesado para los fines que estime convenientes.</p>")
          .append("<p style='text-align:right; margin-top:24px'>_________________________<br/>Control Escolar ITSON</p>")
          .append("</div></body></html>");
        return sb.toString();
    }

    private static String escape(final String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
