package Modelo.strategy;

public class SinDescuento implements EstrategiaDescuento {
    @Override
    public double aplicarDescuento(double subtotal) {
        return subtotal;
    }
}