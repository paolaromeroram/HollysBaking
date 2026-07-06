package modelo.entidades;

public class Insumo {
    private int idInsumo;
    private String nombreInsumo;      
    private double costoUnidad;       
    private String unidadMedida;

    // Constructor vacío y constructor completo
    public Insumo() {}

    public Insumo(int idInsumo, String nombreInsumo, double costoUnidad, String unidadMedida) {
        this.idInsumo = idInsumo;
        this.nombreInsumo = nombreInsumo;
        this.costoUnidad = costoUnidad;
        this.unidadMedida = unidadMedida;
    }

    // Getters y Setters
  public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }
    
    public String getNombreInsumo() { return nombreInsumo; }
    public void setNombreInsumo(String nombreInsumo) { this.nombreInsumo = nombreInsumo; }
    
    public double getCostoUnidad() { return costoUnidad; }
    public void setCostoUnidad(double costoUnidad) { this.costoUnidad = costoUnidad; }
    
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

}