package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SeccionService {
    // Metodo para obtener la lista de la base de dtos y ponerlo en comboBox
    public static List<Secciones> obtenerSecciones() {
        List<Secciones> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM secciones";

        try (Connection con = Conexion.getConnection();
             // stmt de Statement
             java.sql.Statement stmt = con.createStatement();
             // rs de result
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Secciones(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener secciones: " + e.getMessage());
        }
        return lista;
    }
}
