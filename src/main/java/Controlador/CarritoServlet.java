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
import modelo.dao.ProductoDAO;
import modelo.entidades.Producto;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/CarritoServlet"})
public class CarritoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        
        // Calcular total
        double total = 0;
        for (Producto p : carrito) {
            total += p.getPrecioVenta();
        }
        
        request.setAttribute("carrito", carrito);
        request.setAttribute("total", total);
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        String idStr = request.getParameter("id");
        
        HttpSession session = request.getSession();
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        
        if ("agregar".equals(accion) && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                ProductoDAO dao = new ProductoDAO();
                Producto producto = dao.buscarPorId(id);
                
                if (producto != null) {
                    carrito.add(producto);
                    session.setAttribute("carrito", carrito);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            response.sendRedirect("CatalogoServlet");
            
        } else if ("eliminar".equals(accion) && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                carrito.removeIf(p -> p.getIdProducto() == id);
                session.setAttribute("carrito", carrito);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("CarritoServlet");
            
        } else if ("vaciar".equals(accion)) {
            carrito.clear();
            session.setAttribute("carrito", carrito);
            response.sendRedirect("CarritoServlet");
        }
    }
}