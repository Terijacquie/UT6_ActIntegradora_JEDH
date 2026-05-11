package model;

import java.sql.Connection;

public class TablasSQL {

    public static void createTable() {
        String tablaSecciones = "CREATE TABLE IF NOT EXISTS secciones (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(50) NOT NULL)";

        String tablaProductos = "CREATE TABLE IF NOT EXISTS inventario (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "stock INT, " +
                "precio DECIMAL(10,2), "+
                "id_seccion INT, "+
                "FOREIGN KEY (id_seccion) REFERENCES secciones(id))";

        try (Connection conexion = Conexion.getConnection();
             java.sql.Statement stmt = conexion.createStatement()) {

            stmt.execute(tablaSecciones);
            stmt.execute(tablaProductos);

            String checkSecciones = "SELECT COUNT(*) FROM secciones";
            java.sql.ResultSet rs = stmt.executeQuery(checkSecciones);
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO secciones (nombre) VALUES ('Fuego'), ('Pizzería'), ('Ensaladas'), ('Postres')");
            }

            System.out.println("Base de datos creada con éxito");
        } catch (Exception e) {
            System.out.println("Error al crear la BBDD: "+e);
        }
    }
}
