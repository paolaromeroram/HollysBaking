package Modelo.strategy;

/**
 * Patron de diseno DE COMPORTAMIENTO: Strategy
 * Interfaz funcional para descuentos
 */
@FunctionalInterface
public interface EstrategiaDescuento {
    double aplicarDescuento(double subtotal);
}