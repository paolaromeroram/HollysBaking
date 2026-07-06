package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import Modelo.dao.ProductoDAO;
import Modelo.entidades.Producto;
import org.apache.commons.io.IOUtils;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1 MB
    maxFileSize = 1024 * 1024 * 5,     // 5 MB
    maxRequestSize = 1024 * 1024 * 10  // 10 MB
)
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista = dao.listarProductos();
        
        request.setAttribute("listaProductos", lista);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String precioStr = request.getParameter("precio");
            String categoria = request.getParameter("categoria");
            
            // Procesar imagen
            Part imagenPart = request.getPart("imagen");
            byte[] imagenBytes = null;
            
            if (imagenPart != null && imagenPart.getSize() > 0) {
                InputStream inputStream = imagenPart.getInputStream();
                imagenBytes = IOUtils.toByteArray(inputStream);
            }
            
            double precio = Double.parseDouble(precioStr);
            
            Producto p = new Producto();
            p.setNombreProducto(nombre);
            p.setDescripcion(descripcion);
            p.setPrecioVenta(precio);
            p.setStock(0);
            p.setEstadoStock(true);
            p.setCategoria(categoria);
            p.setImagen(imagenBytes);
            
            ProductoDAO dao = new ProductoDAO();
            boolean exito = dao.insertarProducto(p);
            
            if (exito) {
                request.setAttribute("mensaje", "✅ Producto guardado correctamente");
            } else {
                request.setAttribute("error", "❌ No se pudo guardar el producto");
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        doGet(request, response);
    }
}