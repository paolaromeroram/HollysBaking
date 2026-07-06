package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import Modelo.dao.ProductoDAO;
import Modelo.entidades.Producto;

@WebServlet(name = "CatalogoServlet", urlPatterns = {"/CatalogoServlet", "/catalogo"})
public class CatalogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista = dao.listarProductos();
        
        request.setAttribute("listaProductos", lista);
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }
}