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
    private String seccion;

    public Producto(int id, String nombre, int stock, double precio, String seccion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.seccion = seccion;
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

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", seccion='" + seccion + '\'' +
                '}';
    }
}
