package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Modelo.entidades.Producto;

@WebServlet(name = "ConfirmarPagoServlet", urlPatterns = {"/ConfirmarPagoServlet"})
public class ConfirmarPagoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        
        // Aquí puedes guardar la venta en la base de datos
        // Por ahora solo limpiamos el carrito
        
        session.setAttribute("carrito", new ArrayList<Producto>());
        request.setAttribute("mensaje", "¡Gracias por tu compra! Tu pedido ha sido procesado.");
        
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }
}