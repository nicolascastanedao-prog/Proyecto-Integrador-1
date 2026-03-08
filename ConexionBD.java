import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Pacientes;encrypt=true;trustServerCertificate=true";
    private static final String USER = "javaUser";
    private static final String PASSWORD = "1234";

    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
