package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import Modelo.dao.DAOFactory;
import Modelo.dao.ProductoDAO;
import Modelo.entidades.Producto;
import Modelo.entidades.Usuario;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        ProductoDAO dao = DAOFactory.crearProductoDAO();
        
        // Eliminar producto
        if ("eliminar".equals(accion)) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    
                    // Primero eliminar recetas asociadas (por la clave foranea)
                    String sqlRecetas = "DELETE FROM Recetas WHERE id_producto = ?";
                    try (java.sql.Connection con = Modelo.conexion.ConexionDB.getConexion();
                         java.sql.PreparedStatement ps = con.prepareStatement(sqlRecetas)) {
                        ps.setInt(1, id);
                        ps.executeUpdate();
                    } catch (Exception e) {
                        System.out.println("Error eliminando recetas: " + e.getMessage());
                    }
                    
                    boolean exito = dao.eliminarProducto(id);
                    if (exito) {
                        request.setAttribute("mensaje", "Producto eliminado correctamente");
                    } else {
                        request.setAttribute("error", "No se pudo eliminar el producto");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID invalido");
                }
            }
        }
        
        request.setAttribute("listaProductos", dao.listarProductos());
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
            
            System.out.println("=== DATOS RECIBIDOS ===");
            System.out.println("Nombre: [" + nombre + "]");
            System.out.println("Precio: [" + precioStr + "]");
            System.out.println("Categoria: [" + categoria + "]");
            
            // Validacion
            if (nombre == null || nombre.trim().isEmpty() ||
                precioStr == null || precioStr.trim().isEmpty() ||
                categoria == null || categoria.trim().isEmpty()) {
                
                request.setAttribute("error", "Nombre, precio y categoria son obligatorios");
                doGet(request, response);
                return;
            }
            
            double precio = Double.parseDouble(precioStr);
            
            // Procesar imagen (OPCIONAL)
            byte[] imagenBytes = null;
            try {
                Part imagenPart = request.getPart("imagen");
                if (imagenPart != null && imagenPart.getSize() > 0) {
                    System.out.println("Imagen recibida: " + imagenPart.getSize() + " bytes");
                    InputStream inputStream = imagenPart.getInputStream();
                    imagenBytes = inputStream.readAllBytes();
                } else {
                    System.out.println("Sin imagen");
                }
            } catch (Exception e) {
                System.out.println("Error leyendo imagen: " + e.getMessage());
            }
            
            Producto p = new Producto();
            p.setNombreProducto(nombre.trim());
            p.setDescripcion(descripcion);
            p.setPrecioVenta(precio);
            p.setStock(0);
            p.setEstadoStock(true);
            p.setCategoria(categoria);
            p.setImagen(imagenBytes);
            
            // AUDITORIA: Obtener usuario logueado
            HttpSession session = request.getSession(false);
            String usuarioResponsable = "sistema";
            if (session != null && session.getAttribute("usuarioLogueado") != null) {
                Usuario usu = (Usuario) session.getAttribute("usuarioLogueado");
                usuarioResponsable = usu.getCorreo();
            }
            
            ProductoDAO dao = DAOFactory.crearProductoDAO();
            boolean exito = dao.insertarProducto(p, usuarioResponsable);
            
            if (exito) {
                request.setAttribute("mensaje", "Producto guardado correctamente");
            } else {
                request.setAttribute("error", "No se pudo guardar el producto");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR GENERAL: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        doGet(request, response);
    }
}