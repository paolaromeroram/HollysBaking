package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import Modelo.dao.InsumoDAO;
import Modelo.dao.ProductoDAO;
import Modelo.dao.RecetaDAO;
import Modelo.entidades.Insumo;
import Modelo.entidades.Producto;
import Modelo.entidades.Receta;

@WebServlet(name = "RecetaServlet", urlPatterns = {"/RecetaServlet"})
public class RecetaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Cargar listas para los selects
        ProductoDAO prodDAO = new ProductoDAO();
        InsumoDAO insDAO = new InsumoDAO();
        RecetaDAO recDAO = new RecetaDAO();
        
        List<Producto> productos = prodDAO.listarProductos();
        List<Insumo> insumos = insDAO.listarInsumos();
        List<Receta> recetas = recDAO.listarRecetas();
        
        request.setAttribute("listaProductos", productos);
        request.setAttribute("listaInsumos", insumos);
        request.setAttribute("listaRecetas", recetas);
        
        request.getRequestDispatcher("recetas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String idProductoStr = request.getParameter("idProducto");
            String idInsumoStr = request.getParameter("idInsumo");
            String cantidadStr = request.getParameter("cantidad");
            
            System.out.println("=== DATOS RECETA RECIBIDOS ===");
            System.out.println("Producto ID: [" + idProductoStr + "]");
            System.out.println("Insumo ID: [" + idInsumoStr + "]");
            System.out.println("Cantidad: [" + cantidadStr + "]");
            
            // Validación
            if (idProductoStr == null || idProductoStr.trim().isEmpty() ||
                idInsumoStr == null || idInsumoStr.trim().isEmpty() ||
                cantidadStr == null || cantidadStr.trim().isEmpty()) {
                
                request.setAttribute("error", "⚠️ Todos los campos son obligatorios");
                doGet(request, response);
                return;
            }
            
            int idProducto = Integer.parseInt(idProductoStr);
            int idInsumo = Integer.parseInt(idInsumoStr);
            double cantidad = Double.parseDouble(cantidadStr);
            
            Receta nueva = new Receta(0, idProducto, idInsumo, cantidad);
            boolean exito = new RecetaDAO().insertarReceta(nueva);
            
            if (exito) {
                request.setAttribute("mensaje", "✅ Receta guardada correctamente");
            } else {
                request.setAttribute("error", "❌ No se pudo guardar la receta");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "⚠️ Los IDs deben ser números enteros y la cantidad un número válido");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "❌ Error: " + e.getMessage());
        }
        
        doGet(request, response);
    }
}