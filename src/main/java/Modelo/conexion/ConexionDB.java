package modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    // Instancia única (Singleton)
    private static Connection conexion = null;
    
    // Credenciales de tu SQL Server local
    private static final String HOST = "localhost";
    private static final String PUERTO = "1433";
    private static final String BD = "HollysBakingDB";
    private static final String USUARIO = "sa"; // Cambia esto si usas otro usuario
    private static final String CLAVE = "123456"; // Pon tu clave real de SQL Server
    
    // Constructor privado para evitar que creen objetos con "new"
    private ConexionDB() {
    }

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Registrar el driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                
                // Construir la URL
                String url = "jdbc:sqlserver://" + HOST + ":" + PUERTO + 
                             ";databaseName=" + BD + 
                             ";encrypt=true;trustServerCertificate=true;";
                
                // Abrir la conexión
                conexion = DriverManager.getConnection(url, USUARIO, CLAVE);
                System.out.println("Conexión a SQL Server exitosa.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC de SQL Server.");
        } catch (SQLException e) {
            System.out.println("Error de conexión SQL: " + e.getMessage());
        }
        
        return conexion;
    }
    
    // Método para cerrar la conexión cuando ya no se use
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}