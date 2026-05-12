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
