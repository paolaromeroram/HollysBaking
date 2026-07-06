package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Modelo.entidades.Producto;
import Modelo.entidades.Usuario;
import Modelo.facade.CarritoFacade;
import Modelo.strategy.DescuentoClienteFrecuente;
import Modelo.strategy.SinDescuento;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/CarritoServlet"})
public class CarritoServlet extends HttpServlet {
    
    private final CarritoFacade carritoFacade = new CarritoFacade();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Producto> carrito = obtenerCarrito(session);
        
        // Aplicar descuento segun rol del usuario (Patron Strategy)
        Object usuarioObj = session.getAttribute("usuarioLogueado");
        if (usuarioObj instanceof Usuario && "cliente".equals(((Usuario) usuarioObj).getRol())) {
            carritoFacade.setEstrategiaDescuento(new DescuentoClienteFrecuente());
            request.setAttribute("tipoDescuento", "Cliente Frecuente (-10%)");
        } else {
            carritoFacade.setEstrategiaDescuento(new SinDescuento());
        }
        
        double subtotal = carritoFacade.calcularSubtotal(carrito);
        double total = carritoFacade.calcularTotal(carrito);
        
        request.setAttribute("carrito", carrito);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("total", total);
        
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        String idStr = request.getParameter("id");
        HttpSession session = request.getSession();
        List<Producto> carrito = obtenerCarrito(session);
        
        if ("agregar".equals(accion) && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                carritoFacade.agregarProducto(carrito, id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            response.sendRedirect("CatalogoServlet");
            return;
            
        } else if ("eliminar".equals(accion) && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                carritoFacade.eliminarProducto(carrito, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if ("vaciar".equals(accion)) {
            carritoFacade.vaciarCarrito(carrito);
        }
        
        session.setAttribute("carrito", carrito);
        response.sendRedirect("CarritoServlet");
    }
    
    private List<Producto> obtenerCarrito(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }
}