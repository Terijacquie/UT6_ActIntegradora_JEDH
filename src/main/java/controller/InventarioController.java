package controller;

import model.Secciones;
import model.SeccionService;
import model.TablasSQL;
import view.VentanaInventario;

import java.util.List;

public class InventarioController {

    private VentanaInventario view;
    private SeccionService seccionService;

    public InventarioController() {
        this.view = new VentanaInventario();
        this.seccionService = new SeccionService();
    }

    public void iniciarPrograma() {
        // Notas de estudio: Primero hay que crear las tablas:
        TablasSQL.createTable();
        // Ahora vamos a rellenar el ComboBox
        cargarComboBox();
        // Ahora el setVisible para que veamos la interfaz
        // Normalmente lo hacíamos en el main, pero según lo visto, lo profesional es que esté aquí
        view.setVisible(true);
    }

    //Método para cargar los datos del ComboBox con los propios datos de SQL
    private void cargarComboBox() {
        System.out.println("Rellenando ComboBox");

        List<Secciones> lista = seccionService.obtenerSecciones();

        // este mensaje es para yo ver lo que encontró, luego borrar y finalmente llenar con el for
        System.out.println("Lista de Secciones: "+lista.size());

        view.comboSeccion.removeAllItems();
        for (Secciones seccion : lista) {
            view.comboSeccion.addItem(seccion);
        }

        System.out.println("Combo añadido a la interfaz");
    }


}

