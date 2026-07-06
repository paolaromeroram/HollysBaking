package Modelo.entidades;

public class Receta {
    private int idReceta;
    private int idProducto;
    private int idInsumo;
    private double cantidadRequerida;
    
    // Campos adicionales para mostrar nombres
    private String nombreProducto;
    private String nombreInsumo;
    private String unidadInsumo;

    public Receta() {}

    public Receta(int idReceta, int idProducto, int idInsumo, double cantidadRequerida) {
        this.idReceta = idReceta;
        this.idProducto = idProducto;
        this.idInsumo = idInsumo;
        this.cantidadRequerida = cantidadRequerida;
    }

    // Getters y Setters
    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) { this.idReceta = idReceta; }
    
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    
    public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }
    
    public double getCantidadRequerida() { return cantidadRequerida; }
    public void setCantidadRequerida(double cantidadRequerida) { this.cantidadRequerida = cantidadRequerida; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public String getNombreInsumo() { return nombreInsumo; }
    public void setNombreInsumo(String nombreInsumo) { this.nombreInsumo = nombreInsumo; }
    
    public String getUnidadInsumo() { return unidadInsumo; }
    public void setUnidadInsumo(String unidadInsumo) { this.unidadInsumo = unidadInsumo; }
}