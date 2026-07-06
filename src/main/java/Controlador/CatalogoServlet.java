package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import Modelo.dao.ProductoDAO;
import Modelo.entidades.Producto;

@WebServlet(name = "CatalogoServlet", urlPatterns = {"/CatalogoServlet"})
public class CatalogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String categoria = request.getParameter("categoria");
        String buscar = request.getParameter("buscar");
        
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista;
        
        if (buscar != null && !buscar.trim().isEmpty()) {
            // Buscar por nombre
            lista = dao.buscarPorNombre(buscar.trim());
            request.setAttribute("busqueda", buscar);
        } else if (categoria != null && !categoria.isEmpty()) {
            // Filtrar por categoría
            lista = dao.listarPorCategoria(categoria);
            request.setAttribute("categoriaSeleccionada", categoria);
        } else {
            // Todos los productos
            lista = dao.listarProductos();
        }
        
        // Obtener categorías para los filtros
        List<String> categorias = dao.obtenerCategorias();
        
        request.setAttribute("listaProductos", lista);
        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }
}