package model;

/**
 * Clase producto para la creación de la BBDD basada en el restaurante
 * donde trabajo.
 */
public class Producto {
    private int id;
    private String nombre;
    private int stock;
    private double precio;
    private int id_seccion; // Aquí dirá si pertenece a la sección de pizzería, fuego, ensaladas o postres.

    public Producto(int id, String nombre, double precio, int id_seccion, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.id_seccion = id_seccion;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId_seccion() {
        return id_seccion;
    }

    public void setId_seccion(int id_seccion) {
        this.id_seccion = id_seccion;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", id_seccion=" + id_seccion +
                '}';
    }
}
