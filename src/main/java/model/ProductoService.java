package model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductoService {

    public boolean guardarProducto(String nombre, int stock, double precio, int id_seccion) {
        String sql = "INSERT INTO secciones (nombre, stock, precio, id_seccion) VALUES (?, ?, ?, ?)";

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
}
