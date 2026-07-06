package modelo.entidades;

public class Producto {
    private int idProducto;
    private String nombreProducto;
    private double precioVenta;
    private String categoria;  // NUEVO

    public Producto() {}

    public Producto(int idProducto, String nombreProducto, double precioVenta, String categoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioVenta = precioVenta;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    
    public String getCategoria() { return categoria; }  // NUEVO
    public void setCategoria(String categoria) { this.categoria = categoria; }  // NUEVO
}