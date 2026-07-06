package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import Modelo.dao.InsumoDAO;
import Modelo.entidades.Insumo;

@WebServlet(name = "InsumoServlet", urlPatterns = {"/InsumoServlet"})
public class InsumoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        InsumoDAO dao = new InsumoDAO();
        List<Insumo> lista = dao.listarInsumos();
        
        request.setAttribute("listaInsumos", lista);
request.getRequestDispatcher("insumos.jsp").forward(request, response);    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String nombre = request.getParameter("nombre");
            String costoStr = request.getParameter("cantidad"); // El formulario sigue enviando "cantidad"
            String unidad = request.getParameter("unidad");
            
            System.out.println("=== DATOS RECIBIDOS ===");
            System.out.println("Nombre: [" + nombre + "]");
            System.out.println("Costo: [" + costoStr + "]");
            System.out.println("Unidad: [" + unidad + "]");
            
            // Validación
            if (nombre == null || nombre.trim().isEmpty() ||
                costoStr == null || costoStr.trim().isEmpty() ||
                unidad == null || unidad.trim().isEmpty()) {
                
                request.setAttribute("error", "⚠️ Todos los campos son obligatorios");
                doGet(request, response);
                return;
            }
            
            double costo = Double.parseDouble(costoStr);
            
            // Crear objeto (solo 4 parámetros ahora)
            Insumo nuevo = new Insumo(0, nombre.trim(), costo, unidad.trim());
            boolean exito = new InsumoDAO().insertarInsumo(nuevo);
            
            if (exito) {
                request.setAttribute("mensaje", "✅ Insumo guardado correctamente");
            } else {
                request.setAttribute("error", "❌ No se pudo guardar el insumo en la base de datos");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "⚠️ El costo debe ser un número válido");
        } catch (Exception e) {
            System.out.println("ERROR GENERAL: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "❌ Error del sistema: " + e.getMessage());
        }
        
        doGet(request, response);
    }
}