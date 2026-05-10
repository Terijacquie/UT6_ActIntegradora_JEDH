package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Driver;

public class Conexion {

    //1. Se hacen constantes los datos de acceso para que no cambien
    // jdbc:mysql:// es el protocolo, localhost:3306 en mi pc y al final el nombre de la bd
    private static final String URL = "jdbc:mysql://localhost:3306/la_mafia_inventario";
    //Ahora el user name
    private static final String USER = "root";
    private static final String PASSWORD = "Papitas.02";

    // Ahora creamos un método static para usarlo sin crear objetos
    public static Connection getConnection() throws SQLException,  ClassNotFoundException {
        //Línea importante: Carga el driver en la memoria de Java
        //Esto busca el archivo .jar que se añade MANUALMENTE al proyecto
        Class.forName("com.mysql.cj.jdbc.Driver");

        //Línea de acción: Intenta abrir el puente usando la URL, usuario y pass.
        //Si los datos son correctos, devuelve un objeto "Connection" activo.
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

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

        try (Connection conexion = getConnection();
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
    // Metodo para obtener la lista de la base de dtos y ponerlo en comboBox
    public static List<Secciones> obtenerSecciones() {
        List<Secciones> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM secciones";

        try (Connection con = getConnection();
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


    /**
     * Método para cerrar el puente conector de MySQL
     */
    public static void closeConnection(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()){
                conexion.close();
                System.out.println("¡Conexión cerrada!");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: \n" + e.getMessage());
        }
    }

}
