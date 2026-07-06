package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Modelo.dao.DAOFactory;
import Modelo.entidades.Producto;

/**
 * Programacion CONCURRENTE: ExecutorService + CompletableFuture
 */
@WebServlet(name = "CatalogoServlet", urlPatterns = {"/CatalogoServlet"})
public class CatalogoServlet extends HttpServlet {
    
    // Pool de hilos para tareas concurrentes
    private static final ExecutorService POOL = Executors.newFixedThreadPool(4);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String categoria = request.getParameter("categoria");
        String buscar = request.getParameter("buscar");
        
        try {
            // Tarea 1: Cargar productos (async)
            CompletableFuture<List<Producto>> futuroProductos = 
                CompletableFuture.supplyAsync(() -> {
                    if (buscar != null && !buscar.trim().isEmpty()) {
                        return DAOFactory.crearProductoDAO().buscarPorNombre(buscar.trim());
                    } else if (categoria != null && !categoria.isEmpty()) {
                        return DAOFactory.crearProductoDAO().listarPorCategoria(categoria);
                    } else {
                        return DAOFactory.crearProductoDAO().listarProductos();
                    }
                }, POOL);
            
            // Tarea 2: Cargar categorias (async, en paralelo)
            CompletableFuture<List<String>> futuroCategorias = 
                CompletableFuture.supplyAsync(() -> {
                    return DAOFactory.crearProductoDAO().obtenerCategorias();
                }, POOL);
            
            // Combinar resultados cuando ambas terminen
            CompletableFuture.allOf(futuroProductos, futuroCategorias).join();
            
            List<Producto> lista = futuroProductos.get();
            List<String> categorias = futuroCategorias.get();
            
            request.setAttribute("listaProductos", lista);
            request.setAttribute("categorias", categorias);
            request.setAttribute("categoriaSeleccionada", categoria);
            request.setAttribute("busqueda", buscar);
            
            request.getRequestDispatcher("catalogo.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException("Error concurrente", e);
        }
    }
}