package controller;

import model.*;
import view.VentanaInventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        actualizarTabla();
        configurarBotones();
        // Ahora el setVisible para que veamos la interfaz
        // Normalmente lo hacíamos en el main, pero según lo visto, lo profesional es que esté aquí
        view.setVisible(true);
        JOptionPane.showMessageDialog(view, "                Si desea actualizar algún producto: " +
                "\nHaga clic en la fila que desee actualizar, llene los datos en \n" +
                "  las cajas de texto correspondientes y pulse 'Actualizar'.");
    }

    //Método para cargar los datos del ComboBox con los propios datos de SQL
    private void cargarComboBox() {
        System.out.println("Rellenando ComboBox");

        List<Secciones> lista = seccionService.obtenerSecciones();

        // este mensaje es para yo ver lo que encontró, luego borrar y finalmente llenar con el for
        System.out.println("Lista de Secciones: " + lista.size());

        view.comboSeccion.removeAllItems();
        for (Secciones seccion : lista) {
            view.comboSeccion.addItem(seccion);
        }

        System.out.println("Combo añadido a la interfaz");
    }

    public void actualizarTabla() {
        // Primero coloco la lista desde ProductoService
        List<Producto> lista = ProductoService.obtenerProductos();

        //Ahora pongo las columnas de la tabla
        String[] columnas = new String[]{"ID", "Nombre", "Stock", "Precio", "Sección"};

        //Creo el modelo de la tabla
        //Quería que se viera bonito, así que recurrí a internet y vi este post en Stack Overflow: https://stackoverflow.com/questions/9761673/java-tablemodel-and-defaulttablemodel
        // https://old.chuidiang.org/java/tablas/tablamodelo/tablamodelo.php
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0); /* {
            // Clase anónima: me di cuenta de que se editaba la tabla si dabas doble clic sobre cualquier elemento de la tabla. Un compañero me explicaba
            //que si no utilizaba esto, podrían editar la tabla a placer. Lo dejo como comentario para estudios posteriores.
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        }; */

        //Ahora uso el for para recorrer la lista y añadir el producto como una fila
        for (Producto p : lista) {
            Object[] fila = {
                    p.getId(),
                    p.getNombre(),
                    p.getStock(),
                    p.getPrecio(),
                    p.getSeccion() // se supone que debería aparecer el String, ya lo probaré
            };
            modelo.addRow(fila);
        }
        // Ahora hay que pasar el modelo a la tabla de la vista:
        view.tablaInventario.setModel(modelo);
    }

    // Lógica de botones
    private void configurarBotones() {
        view.btnGuardar.addActionListener(e -> {
            try {
                // Primero guardamos los datos de los textos
                String nombre = view.txtNombre.getText();
                int stock = Integer.parseInt(view.txtStock.getText());
                double precio = Double.parseDouble(view.txtPrecio.getText());

                //Ahora los objetos del ComboBox (no me deja en paz) porque tengo que sacarle el id
                Secciones seccion = (Secciones) view.comboSeccion.getSelectedItem();
                int idSeccion = seccion.getId();

                // Ahora llamo al service para guardar en MySQL
                boolean cambioAprobado = ProductoService.guardarProducto(nombre, stock, precio, idSeccion);

                if (cambioAprobado) {
                    JOptionPane.showMessageDialog(view, "¡Producto guardado!");
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(view, "No se pudo guardar");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Revisa los siguientes datos: " + ex.getMessage());
            }
        });

        view.btnEliminar.addActionListener(e -> {
            // Verificación de fila seleccionada:
            int seleccion = view.tablaInventario.getSelectedRow();

            if (seleccion == -1) {
                JOptionPane.showMessageDialog(view, "Seleccione un Producto");
                return;
            }

            // Ahora obtenemos el id del producto y convertimos a String porque el modelo devuelve un objeto
            int id = Integer.parseInt(view.tablaInventario.getValueAt(seleccion, 0).toString());
            String nombre = view.tablaInventario.getValueAt(seleccion, 1).toString();

            //Detalle de control (Lo descubrí jugando con JOptionPane)
            int seguridad = JOptionPane.showConfirmDialog(view,
                    "¿Desea eliminar " + nombre + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (seguridad == JOptionPane.YES_OPTION) {
                boolean cambioAprobado = ProductoService.eliminar(id);

                if (cambioAprobado) {
                    JOptionPane.showMessageDialog(view, "Producto eliminado");
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(view, "No se pudo eliminar " + nombre);
                }
            }
        });

        // Botón Limpiar:
        view.btnLimpiar.addActionListener(e -> {
            limpiarCampos();
            view.tablaInventario.clearSelection();
        });

        // Botón de actualizar
        // Hay varias acciones dentro de MouseListener, pero esta vez, usaré MouseClicked
        view.tablaInventario.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = view.tablaInventario.getSelectedRow();
                if (fila == -1) {
                    view.txtNombre.setText(view.tablaInventario.getValueAt(fila, 1).toString());
                    view.txtStock.setText(view.tablaInventario.getValueAt(fila, 2).toString());
                    view.txtPrecio.setText(view.tablaInventario.getValueAt(fila, 3).toString());

                    // Esta es la parte más "complicada". Hay que buscar la seccion que coincida con el nombre.
                    // Funciona como el recorrido común de toda la vida, pero hay que seleccionar especificamente el
                    // comboSeccion, que al final es una lista y lo recorremos con for
                    String seccion = view.tablaInventario.getValueAt(fila, 4).toString();
                    for (int i = 0; i < view.comboSeccion.getItemCount(); i++) {
                        if (view.comboSeccion.getItemAt(i).toString().equals(seccion)) {
                            view.comboSeccion.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        //Ahora si, ya configurado el MouseListener, voy con el botón actualizar:
        // Nota importante sobre el funcionamiento: Mi idea principal era que al cliquear la row, se pasara la información a los cuadros de texto y se editaran
        // Sin embargo, no funcionó como esperaba, hay que cliquear la row, escribir el texto en los cuadros, da actualizar y se verán los cambios.
        // Hace falta investigar más al respecto, pero me considero conforme con el resultado por el momento.
        view.btnActualizar.addActionListener(e -> {
            int fila = view.tablaInventario.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(view, "Seleccione un Producto");
                return;
            }

            try {
                // 1. Primero cojo el ID (posición 0)
                int id = Integer.parseInt(view.tablaInventario.getValueAt(fila, 0).toString());
               // Ahora cojo los textos
                String nombre = view.txtNombre.getText();
                int stock = Integer.parseInt(view.txtStock.getText());
                double precio = Double.parseDouble(view.txtPrecio.getText());

                //Ahora el dato del ComboBox
                Secciones seccion = (Secciones) view.comboSeccion.getSelectedItem();
                int idSeccion = seccion.getId();

                //Ahora recurrimos al service
                boolean aprobado = ProductoService.actualizar(id, nombre, stock, precio, seccion.getId());

                if (aprobado) {
                    JOptionPane.showMessageDialog(view, "Producto actualizado");
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(view, "No se pudo actualizar " + nombre);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Revisa los siguientes datos: " + ex.getMessage());
            }
        });

        // Botón Buscar
        view.btnBuscar.addActionListener(e -> {
            List<Producto> lista = ProductoService.obtenerProductos();

            int id = Integer.parseInt(JOptionPane.showInputDialog(view, "Ingrese el ID del producto que desea encontrar: "));
            boolean encontrado = false;
            for (Producto p : lista) {
                if (p.getId() == id) {
                    JOptionPane.showMessageDialog(view, "Producto encontrado: \n" + p.getNombre() + "\nStock: " + p.getStock());
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(view, "Producto no encontrado");
            }
        });

    }


    private void limpiarCampos() {
        view.txtNombre.setText("");
        view.txtStock.setText("");
        view.txtPrecio.setText("");

        //Lo siguiente es para que también resetee el comboBox. Así siempre aparecerá en "Fuego"
        if (view.comboSeccion.getItemCount() > 0) {
            view.comboSeccion.setSelectedIndex(0);
        }

        //Este es un detallito que también vi en Stack Overflow, sirve para que una vez reseteado,
        //el cursor empiece de una vez en "nombre" y puedas a empezar a escribir sin tener que seleccionar la caja de texto
        view.txtNombre.requestFocus();
    }


}

