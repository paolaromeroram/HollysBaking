package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.conexion.ConexionDB;

@WebServlet(name = "ImagenServlet", urlPatterns = {"/ImagenServlet"})
public class ImagenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int idProducto = Integer.parseInt(request.getParameter("id"));
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT imagen FROM Productos WHERE id_producto = ?")) {
            
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                byte[] imagen = rs.getBytes("imagen");
                if (imagen != null) {
                    response.setContentType("image/jpeg");
                    response.getOutputStream().write(imagen);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Imagen por defecto si no hay
        response.sendRedirect("https://via.placeholder.com/80?text=Sin+Imagen");
    }
}