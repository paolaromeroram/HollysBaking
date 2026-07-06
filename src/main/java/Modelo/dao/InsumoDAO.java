package Modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.conexion.ConexionDB;
import Modelo.entidades.Insumo;

public class InsumoDAO {

    public List<Insumo> listarInsumos() {
        List<Insumo> lista = new ArrayList<>();
        String sql = "SELECT id_insumo, nombre_insumo, costo_unidad, unidad_medida FROM Insumos";
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Insumo ins = new Insumo();
                ins.setIdInsumo(rs.getInt("id_insumo"));
                ins.setNombreInsumo(rs.getString("nombre_insumo"));
                ins.setCostoUnidad(rs.getDouble("costo_unidad"));
                ins.setUnidadMedida(rs.getString("unidad_medida"));
                lista.add(ins);
            }
            System.out.println("📋 Insumos listados: " + lista.size());
            
        } catch (SQLException e) {
            System.out.println("❌ Error SQL al listar: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean insertarInsumo(Insumo ins) {
        String sql = "INSERT INTO Insumos (nombre_insumo, costo_unidad, unidad_medida) VALUES (?, ?, ?)";
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, ins.getNombreInsumo());
            ps.setDouble(2, ins.getCostoUnidad());
            ps.setString(3, ins.getUnidadMedida());
            
            int filasAfectadas = ps.executeUpdate();
            System.out.println("✅ Filas insertadas: " + filasAfectadas);
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error SQL al insertar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}