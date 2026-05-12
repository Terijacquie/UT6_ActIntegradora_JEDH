package model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductoService {

    public static boolean guardarProducto(String nombre, int stock, double precio, int id_seccion) {
        String sql = "INSERT INTO inventario (nombre, stock, precio, id_seccion) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

        pstmt.setString(1, nombre);
        pstmt.setInt(2, stock);
        pstmt.setDouble(3, precio);
        pstmt.setInt(4, id_seccion);

        int filas = pstmt.executeUpdate();
        return filas > 0; // esto para que, si se insertó al menos una fila, salga true

        } catch (Exception e) {
            System.err.println("Error al guardar el producto: "+e.getMessage());
            return false;
        }

    }
    //Recordatorio para mí: Luego de terminar de arreglar la base de datos, vuelve acá para hacer las opciones del CRUD

    public static List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.stock, p.precio, s.nombre AS seccion "+
                "FROM inventario p "+
                "JOIN secciones s ON p.id_seccion = s.id";
        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getDouble("precio"),
                        rs.getString("seccion")
                );
                productos.add(p);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar los productos: "+e.getMessage());
        }
        return productos;
    }

    public static boolean actualizar(int id, String nombre, int stock, double precio, int id_seccion) {
        String sql = "UPDATE inventario SET nombre=?, stock=?, precio=?, id_seccion=? WHERE id=?";

        try (Connection con = Conexion.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setInt(2, stock);
            pstmt.setDouble(3, precio);
            pstmt.setInt(4, id_seccion);
            pstmt.setInt(5, id);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
         System.err.println("Error al actualizar el producto: "+e.getMessage());
         return false;
        }
    }

    public static boolean eliminar(int id) {
        String sql = "DELETE FROM inventario WHERE id=?";

        try (Connection con = Conexion.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar el producto: "+e.getMessage());
            return false;
        }
    }
}
