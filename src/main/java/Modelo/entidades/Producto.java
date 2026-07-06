package Modelo.entidades;

public class Producto {
    private int idProducto;
    private String nombreProducto;
    private String descripcion;
    private double precioVenta;
    private int stock;
    private boolean estadoStock;
    private String categoria;
    private byte[] imagen;  // NUEVO: para guardar la imagen
    private java.time.LocalDateTime fechaCreacion;
private String usuarioCreacion;
private java.time.LocalDateTime fechaModificacion;
private String usuarioModificacion;
    // Constructor vacío
    public Producto() {}
    
    // Constructor con todos los campos
    public Producto(int idProducto, String nombreProducto, String descripcion, 
                    double precioVenta, int stock, boolean estadoStock, 
                    String categoria, byte[] imagen) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.estadoStock = estadoStock;
        this.categoria = categoria;
        this.imagen = imagen;
    }
    
    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    
    public boolean isEstadoStock() { return estadoStock; }
    public void setEstadoStock(boolean estadoStock) { this.estadoStock = estadoStock; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    // NUEVO: Getter y Setter para imagen
    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }
    
    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

public String getUsuarioCreacion() { return usuarioCreacion; }
public void setUsuarioCreacion(String usuarioCreacion) { this.usuarioCreacion = usuarioCreacion; }

public java.time.LocalDateTime getFechaModificacion() { return fechaModificacion; }
public void setFechaModificacion(java.time.LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

public String getUsuarioModificacion() { return usuarioModificacion; }
public void setUsuarioModificacion(String usuarioModificacion) { this.usuarioModificacion = usuarioModificacion; }
}