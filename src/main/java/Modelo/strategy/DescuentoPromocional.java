package Modelo.strategy;

public class DescuentoPromocional implements EstrategiaDescuento {
    private final double montoFijo;
    
    public DescuentoPromocional(double montoFijo) {
        this.montoFijo = montoFijo;
    }
    
    @Override
    public double aplicarDescuento(double subtotal) {
        double total = subtotal - montoFijo;
        return Math.max(total, 0.0);
    }
}