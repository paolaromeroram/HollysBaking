package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.conexion.ConexionDB;
import modelo.entidades.Usuario;

public class UsuarioDAO {
    
    // Método para validar el inicio de sesión
    public Usuario validarLogin(String correo, String clave) {
        Usuario usu = null;
        String sql = "SELECT * FROM Usuarios WHERE correo = ? AND clave = ?";
        
        try {
            // Traemos la conexión única (Singleton) que creaste
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            
            // Reemplazamos los signos de interrogación (?) por los datos del usuario
            ps.setString(1, correo);
            ps.setString(2, clave);
            
            // Ejecutamos la consulta
            ResultSet rs = ps.executeQuery();
            
            // Si la consulta encuentra un resultado, armamos el objeto Usuario
            if (rs.next()) {
                usu = new Usuario();
                usu.setIdUsuario(rs.getInt("id_usuario"));
                usu.setNombre(rs.getString("nombre"));
                usu.setCorreo(rs.getString("correo"));
                usu.setClave(rs.getString("clave"));
                usu.setRol(rs.getString("rol"));
            }
            
        } catch (Exception e) {
            System.out.println("Error en validarLogin: " + e.getMessage());
        }
        
        return usu; // Si no encuentra a nadie, devolverá null
    }
    // Método para registrar un nuevo usuario (cliente) en la BD
    public boolean registrarUsuario(Usuario usu) {
        // La consulta SQL con signos de interrogación para proteger contra hackeos (Inyección SQL)
        String sql = "INSERT INTO Usuarios (nombre, correo, clave, rol) VALUES (?, ?, ?, ?)";
        
        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            
            // Llenamos los signos de interrogación con los datos del nuevo cliente
            ps.setString(1, usu.getNombre());
            ps.setString(2, usu.getCorreo());
            ps.setString(3, usu.getClave());
            ps.setString(4, usu.getRol());
            
            // executeUpdate() se usa para INSERT, UPDATE o DELETE. Devuelve un número mayor a 0 si tuvo éxito.
            int filasAfectadas = ps.executeUpdate();
            
            return filasAfectadas > 0; // Si es mayor a 0, devuelve true (éxito)
            
        } catch (Exception e) {
            System.out.println("Error en registrarUsuario: " + e.getMessage());
            return false;
        }
    }
}