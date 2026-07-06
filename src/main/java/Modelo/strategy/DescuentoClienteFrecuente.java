package Modelo.strategy;

public class DescuentoClienteFrecuente implements EstrategiaDescuento {
    private static final double PORCENTAJE = 0.10; // 10% descuento
    
    @Override
    public double aplicarDescuento(double subtotal) {
        return subtotal * (1 - PORCENTAJE);
    }
}