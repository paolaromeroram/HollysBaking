package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import Modelo.dao.UsuarioDAO;
import Modelo.entidades.Usuario;

// Esta línea es crucial: conecta el action="AuthServlet" de tu formulario con esta clase
@WebServlet(name = "AuthServlet", urlPatterns = {"/AuthServlet"})
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Recibir los datos escritos en el formulario de login.jsp
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        
        // 2. Comunicarse con la base de datos (Modelo)
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usu = dao.validarLogin(correo, clave);
        
        // 3. Tomar una decisión basada en la base de datos
        if (usu != null) {
            // ¡El usuario existe! Creamos una sesión para mantenerlo conectado
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usu); // Guardamos todos sus datos en memoria
            
            // Redirigir según el rol del usuario
          if (usu.getRol().equals("trabajador")) {
    // CAMBIO AQUÍ: Redirigimos al Servlet que carga los datos, NO directo al JSP
     response.sendRedirect("DashboardServlet"); 
} else {
    response.sendRedirect("catalogo.jsp");
}
        } else {
            // Credenciales incorrectas, lo regresamos al login mandando un mensaje de error por la URL
            response.sendRedirect("login.jsp?error=1");
        }
    }
}