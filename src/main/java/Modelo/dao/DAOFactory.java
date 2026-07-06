package Modelo.dao;

/**
 * Patron de diseno CREACIONAL: Factory
 * Centraliza la creacion de DAOs
 */
public class DAOFactory {
    
    public enum TipoDAO {
        PRODUCTO, INSUMO, RECETA, USUARIO
    }
    
    private DAOFactory() {} // No se instancia
    
    // Metodos de fabrica especificos
    public static ProductoDAO crearProductoDAO() {
        return new ProductoDAO();
    }
    
    public static InsumoDAO crearInsumoDAO() {
        return new InsumoDAO();
    }
    
    public static RecetaDAO crearRecetaDAO() {
        return new RecetaDAO();
    }
    
    public static UsuarioDAO crearUsuarioDAO() {
        return new UsuarioDAO();
    }
}