package Modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import Modelo.conexion.ConexionDB;
import Modelo.entidades.Producto;

/**
 * DAO de Producto con programacion funcional (extiende GenericDAO)
 */
public class ProductoDAO extends GenericDAO<Producto> {
    
    // Lambda: mapea ResultSet -> Producto (PROGRAMACION FUNCIONAL)
    private final Function<ResultSet, Producto> mapper = rs -> {
        try {
            Producto p = new Producto();
            p.setIdProducto(rs.getInt("id_producto"));
            p.setNombreProducto(rs.getString("nombre_producto"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setPrecioVenta(rs.getDouble("precio_venta"));
            p.setStock(rs.getInt("stock"));
            p.setEstadoStock(rs.getBoolean("estado_stock"));
            p.setCategoria(rs.getString("categoria"));
            p.setImagen(rs.getBytes("imagen"));
            
            // Auditoria
            if (rs.getTimestamp("fecha_creacion") != null) {
                p.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
            }
            p.setUsuarioCreacion(rs.getString("usuario_creacion"));
            
            return p;
        } catch (SQLException e) {
            throw new RuntimeException("Error mapeando Producto", e);
        }
    };
    
    // Consultas usando GenericDAO (programacion funcional)
    public List<Producto> listarProductos() {
        String sql = "SELECT * FROM Productos";
        return consultarLista(sql, mapper);
    }
    
    public List<Producto> listarPorCategoria(String categoria) {
        String sql = "SELECT * FROM Productos WHERE categoria = ?";
        return consultarLista(sql, mapper, categoria);
    }
    
    public Producto buscarPorId(int id) {
        String sql = "SELECT * FROM Productos WHERE id_producto = ?";
        return consultarUno(sql, mapper, id);
    }
    
    // Buscador por nombre (para el catalogo)
    public List<Producto> buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM Productos WHERE LOWER(nombre_producto) LIKE LOWER(?)";
        return consultarLista(sql, mapper, "%" + nombre + "%");
    }
    
    // Obtener categorias distintas (sin lambda compleja)
    public List<String> obtenerCategorias() {
        String sql = "SELECT DISTINCT categoria FROM Productos WHERE categoria IS NOT NULL";
        List<String> categorias = new ArrayList<>();
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                categorias.add(rs.getString("categoria"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categorias;
    }
    
    // Contar productos (para el dashboard)
    public int contarProductos() {
        String sql = "SELECT COUNT(*) as total FROM Productos";
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Insertar con auditoria
    public boolean insertarProducto(Producto p, String usuarioResponsable) {
    String sql = "INSERT INTO Productos (nombre_producto, descripcion, precio_venta, stock, estado_stock, categoria, imagen) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try {
        boolean resultado = ejecutarActualizacion(sql,
            p.getNombreProducto(),
            p.getDescripcion(),
            p.getPrecioVenta(),
            p.getStock(),
            p.isEstadoStock(),
            p.getCategoria(),
            p.getImagen()  // Puede ser null, PostgreSQL lo acepta
        );
        
        System.out.println("Insert resultado: " + resultado);
        return resultado;
        
    } catch (Exception e) {
        System.out.println("Error en insertarProducto: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
}