package edu.itson.constancia.controller;

import edu.itson.constancia.model.Alumno;
import edu.itson.constancia.service.AlumnoService;
import edu.itson.constancia.ui.MainFrame;
import java.util.List;
import java.util.Objects;

/** Orquesta la interacción entre la vista (Swing) y la lógica (service). */
public class Controlador {

    private final MainFrame vista;
    private final AlumnoService service;
    private Alumno seleccionActual;

    public Controlador(final MainFrame vista, final AlumnoService service) {
        this.vista = Objects.requireNonNull(vista, "vista");
        this.service = Objects.requireNonNull(service, "service");
    }

    public void buscarPorPrefijo(final String prefijo) {
        final List<Alumno> lista = service.buscarPorPrefijo(prefijo);
        vista.mostrarCoincidencias(lista);
    }

    public void cargarDetalle(final String id) {
        seleccionActual = service.obtenerPorId(id);
        vista.mostrarConfirmacion(seleccionActual);
    }

    public void generarConstancia() {
        final String html = service.generarConstancia(seleccionActual);
        vista.mostrarConstancia(html);
    }
}
