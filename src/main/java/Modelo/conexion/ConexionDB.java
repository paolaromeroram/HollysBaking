package modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    // Variables de entorno de Railway (o valores locales para desarrollo)
    private static final String HOST = System.getenv().getOrDefault("PGHOST", "localhost");
    private static final String PUERTO = System.getenv().getOrDefault("PGPORT", "5432");
    private static final String BD = System.getenv().getOrDefault("PGDATABASE", "HollysBakingDB");
    private static final String USUARIO = System.getenv().getOrDefault("PGUSER", "postgres");
    private static final String CLAVE = System.getenv().getOrDefault("PGPASSWORD", "123456");
    
    private ConexionDB() {}

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + HOST + ":" + PUERTO + "/" + BD;
            conexion = DriverManager.getConnection(url, USUARIO, CLAVE);
            System.out.println("Conexión a PostgreSQL exitosa.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC de PostgreSQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error de conexión SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
}