package Modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import Modelo.conexion.ConexionDB;

/**
 * DAO generico con programacion funcional (lambdas y streams)
 * @param <T> Tipo de entidad
 */
public abstract class GenericDAO<T> {
    
    /**
     * Ejecuta consulta SELECT y mapea resultados con lambda
     */
    protected List<T> consultarLista(String sql, Function<ResultSet, T> mapper, Object... parametros) {
        List<T> lista = new ArrayList<>();
        
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Asignar parametros con lambda (programacion funcional)
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapper.apply(rs)); // Lambda para mapear
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error en consultarLista: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Ejecuta consulta SELECT y devuelve un solo resultado
     */
    protected T consultarUno(String sql, Function<ResultSet, T> mapper, Object... parametros) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper.apply(rs);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error en consultarUno: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Ejecuta INSERT, UPDATE o DELETE
     */
    protected boolean ejecutarActualizacion(String sql, Object... parametros) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error en ejecutarActualizacion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}