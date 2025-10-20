package edu.itson.constancia;

import edu.itson.constancia.controller.Controlador;
import edu.itson.constancia.dao.InMemoryAlumnoDAO;
import edu.itson.constancia.service.AlumnoService;
import edu.itson.constancia.ui.MainFrame;

import javax.swing.SwingUtilities;

/** Punto de entrada de la aplicación. */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame vista = new MainFrame();
                AlumnoService service = new AlumnoService(new InMemoryAlumnoDAO());
                Controlador ctrl = new Controlador(vista, service);
                vista.setController(ctrl);
                vista.setVisible(true);
            }
        });
    }
}
