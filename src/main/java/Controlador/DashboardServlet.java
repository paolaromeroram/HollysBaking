package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Modelo.dao.DAOFactory;
import Modelo.dao.ProductoDAO;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Usar Factory (patron de diseno)
        ProductoDAO productoDAO = DAOFactory.crearProductoDAO();
        
        // Obtener datos reales de la base de datos
        int totalProductos = productoDAO.contarProductos();
        
        // Datos de ejemplo (luego los sacas de la BD)
        int pedidosDelMes = 12;
        double ventasDelMes = 350.50;
        int bajoStock = 2;
        
        // Enviar datos a la vista
        request.setAttribute("totalProductos", totalProductos);
        request.setAttribute("pedidosDelMes", pedidosDelMes);
        request.setAttribute("ventasDelMes", ventasDelMes);
        request.setAttribute("bajoStock", bajoStock);
        
        // Redirigir al dashboard
        request.getRequestDispatcher("dashboard_principal.jsp").forward(request, response);
    }
}