package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import modelo.dao.ProductoDAO;
import modelo.entidades.Producto;

@WebServlet(name = "DetalleProductoServlet", urlPatterns = {"/DetalleProductoServlet"})
public class DetalleProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ProductoDAO dao = new ProductoDAO();
                Producto producto = dao.buscarPorId(id);
                
                if (producto != null) {
                    request.setAttribute("producto", producto);
                    request.getRequestDispatcher("detalle_producto.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        // Si no encuentra el producto, redirigir al catálogo
        response.sendRedirect("CatalogoServlet");
    }
}