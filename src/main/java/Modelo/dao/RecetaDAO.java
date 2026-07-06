package Modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.conexion.ConexionDB;
import Modelo.entidades.Receta;

public class RecetaDAO {

    // Listar recetas con nombres de productos e insumos
    public List<Receta> listarRecetas() {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT r.id_receta, r.id_producto, r.id_insumo, r.cantidad_requerida, " +
                     "p.nombre_producto, i.nombre_insumo, i.unidad_medida " +
                     "FROM Recetas r " +
                     "INNER JOIN Productos p ON r.id_producto = p.id_producto " +
                     "INNER JOIN Insumos i ON r.id_insumo = i.id_insumo " +
                     "ORDER BY p.nombre_producto, i.nombre_insumo";
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Receta r = new Receta();
                r.setIdReceta(rs.getInt("id_receta"));
                r.setIdProducto(rs.getInt("id_producto"));
                r.setIdInsumo(rs.getInt("id_insumo"));
                r.setCantidadRequerida(rs.getDouble("cantidad_requerida"));
                r.setNombreProducto(rs.getString("nombre_producto"));
                r.setNombreInsumo(rs.getString("nombre_insumo"));
                r.setUnidadInsumo(rs.getString("unidad_medida"));
                lista.add(r);
            }
            System.out.println("📋 Recetas listadas: " + lista.size());
            
        } catch (SQLException e) {
            System.out.println("❌ Error al listar recetas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    // Insertar nueva receta
    public boolean insertarReceta(Receta r) {
        String sql = "INSERT INTO Recetas (id_producto, id_insumo, cantidad_requerida) VALUES (?, ?, ?)";
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, r.getIdProducto());
            ps.setInt(2, r.getIdInsumo());
            ps.setDouble(3, r.getCantidadRequerida());
            
            int filas = ps.executeUpdate();
            System.out.println("✅ Receta insertada. Filas: " + filas);
            return filas > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar receta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}