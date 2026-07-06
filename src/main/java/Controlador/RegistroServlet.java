package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Modelo.dao.UsuarioDAO;
import Modelo.entidades.Usuario;

@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Recibir los datos del formulario web
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        
        // 2. Armar nuestro objeto Usuario
        Usuario nuevoCliente = new Usuario();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setCorreo(correo);
        nuevoCliente.setClave(clave);
        // OJO AQUÍ: Como es la página pública de registro, obligamos a que el rol sea siempre 'cliente'
        nuevoCliente.setRol("cliente"); 
        
        // 3. Pasarle el objeto al DAO para que lo guarde en SQL Server
        UsuarioDAO dao = new UsuarioDAO();
        boolean exito = dao.registrarUsuario(nuevoCliente);
        
        // 4. Responder al navegador
        if (exito) {
            // Si guardó bien, lo mandamos al login con un mensaje de éxito
            response.sendRedirect("login.jsp?registro=exito");
        } else {
            // Si falló (por ejemplo, si el correo ya existe), lo devolvemos al registro
            response.sendRedirect("registro.jsp?error=1");
        }
    }
}