package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import modelo.dao.ProductoDAO;
import modelo.entidades.Producto;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista = dao.listarProductos();
        
        request.setAttribute("listaProductos", lista);
        // CORREGIDO: El JSP se llama productos.jsp (no gestion_productos.jsp)
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            
            System.out.println("=== DATOS RECIBIDOS ===");
            System.out.println("Nombre: [" + nombre + "]");
            System.out.println("Precio: [" + precioStr + "]");
            
            // Validación
            if (nombre == null || nombre.trim().isEmpty() ||
                precioStr == null || precioStr.trim().isEmpty()) {
                
                request.setAttribute("error", "⚠️ Todos los campos son obligatorios");
                doGet(request, response);  // CORREGIDO: usar forward, no redirect
                return;
            }
            
            double precio = Double.parseDouble(precioStr);
            
Producto nuevo = new Producto(0, nombre.trim(), precio, null);            boolean exito = new ProductoDAO().insertarProducto(nuevo);
            
            if (exito) {
                request.setAttribute("mensaje", "✅ Producto guardado correctamente");
            } else {
                request.setAttribute("error", "❌ No se pudo guardar el producto");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "⚠️ El precio debe ser un número válido");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "❌ Error: " + e.getMessage());
        }
        
        // CORREGIDO: Usar doGet en lugar de sendRedirect para mantener mensajes
        doGet(request, response);
    }
}