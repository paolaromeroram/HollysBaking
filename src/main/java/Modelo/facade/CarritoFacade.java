package Modelo.facade;

import java.util.List;
import Modelo.dao.DAOFactory;
import Modelo.entidades.Producto;
import Modelo.strategy.EstrategiaDescuento;
import Modelo.strategy.SinDescuento;

/**
 * Patron de diseno ESTRUCTURAL: Facade
 * Simplifica la logica del carrito
 */
public class CarritoFacade {
    
    private EstrategiaDescuento estrategia = new SinDescuento();
    
    public void setEstrategiaDescuento(EstrategiaDescuento e) {
        this.estrategia = (e != null) ? e : new SinDescuento();
    }
    
    public Producto agregarProducto(List<Producto> carrito, int idProducto) {
        Producto p = DAOFactory.crearProductoDAO().buscarPorId(idProducto);
        if (p != null) {
            carrito.add(p);
        }
        return p;
    }
    
    public void eliminarProducto(List<Producto> carrito, int idProducto) {
        carrito.removeIf(p -> p.getIdProducto() == idProducto); // Lambda
    }
    
    public void vaciarCarrito(List<Producto> carrito) {
        carrito.clear();
    }
    
    public double calcularSubtotal(List<Producto> carrito) {
        return carrito.stream() // Stream
                      .mapToDouble(Producto::getPrecioVenta)
                      .sum();
    }
    
    public double calcularTotal(List<Producto> carrito) {
        return estrategia.aplicarDescuento(calcularSubtotal(carrito));
    }
    
    public int contarItems(List<Producto> carrito) {
        return carrito.size();
    }
}