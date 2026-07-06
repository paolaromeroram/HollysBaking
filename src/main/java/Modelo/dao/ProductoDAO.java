package Modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.conexion.ConexionDB;
import Modelo.entidades.Producto;

public class ProductoDAO {

    public List<Producto> listarProductos() {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT id_producto, nombre_producto, descripcion, precio_venta, stock, estado_stock, categoria, imagen FROM Productos";
    
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Producto p = new Producto();
            p.setIdProducto(rs.getInt("id_producto"));
            p.setNombreProducto(rs.getString("nombre_producto"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setPrecioVenta(rs.getDouble("precio_venta"));
            p.setStock(rs.getInt("stock"));
            p.setEstadoStock(rs.getBoolean("estado_stock"));
            p.setCategoria(rs.getString("categoria"));
            p.setImagen(rs.getBytes("imagen"));  // NUEVO
            lista.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
    
    // NUEVO: Filtrar por categoría
    public List<Producto> listarPorCategoria(String categoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre_producto, precio_venta, categoria FROM Productos WHERE categoria = ?";
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombreProducto(rs.getString("nombre_producto"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setCategoria(rs.getString("categoria"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // NUEVO: Buscar producto por ID
    public Producto buscarPorId(int id) {
        String sql = "SELECT id_producto, nombre_producto, precio_venta, categoria FROM Productos WHERE id_producto = ?";
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombreProducto(rs.getString("nombre_producto"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setCategoria(rs.getString("categoria"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean insertarProducto(Producto p) {
    String sql = "INSERT INTO Productos (nombre_producto, descripcion, precio_venta, stock, estado_stock, categoria, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, p.getNombreProducto());
        ps.setString(2, p.getDescripcion());
        ps.setDouble(3, p.getPrecioVenta());
        ps.setInt(4, p.getStock());
        ps.setBoolean(5, p.isEstadoStock());
        ps.setString(6, p.getCategoria());
        
        // NUEVO: Guardar imagen como BYTEA
        if (p.getImagen() != null) {
            ps.setBytes(7, p.getImagen());
        } else {
            ps.setNull(7, java.sql.Types.BINARY);
        }
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
   public int contarProductos() {
    int total = 0;
    String sql = "SELECT COUNT(*) FROM Productos";
    
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            total = rs.getInt(1);
        }
    } catch (Exception e) {
        System.out.println("Error al contar productos: " + e.getMessage());
        e.printStackTrace();
    }
    return total;
}
   public boolean eliminarProducto(int id) {
    String sql = "DELETE FROM Productos WHERE id_producto = ?";
    
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}