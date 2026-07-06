package controlador;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import modelo.dao.ProductoDAO;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Instanciar los DAOs que necesitemos
        ProductoDAO productoDAO = new ProductoDAO();
        
        // 2. Obtener los datos reales de la base de datos
        int totalProductos = productoDAO.contarProductos();
        
        // (Aquí luego agregaremos ventas, pedidos, etc. Por ahora enviamos datos simulados para el resto)
        int pedidosDelMes = 12; 
        double ventasDelMes = 350.50;
        int bajoStock = 2;

        // 3. Enviar los datos a la vista (JSP)
        request.setAttribute("totalProductos", totalProductos);
        request.setAttribute("pedidosDelMes", pedidosDelMes);
        request.setAttribute("ventasDelMes", ventasDelMes);
        request.setAttribute("bajoStock", bajoStock);
        
        // 4. Redirigir al dashboard_principal.jsp
        request.getRequestDispatcher("dashboard_principal.jsp").forward(request, response);
    }
}


