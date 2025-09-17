/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.Entrega;
import InterfacesDAO.IEntrega;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Laptop
 */
public class EntregaDAO implements IEntrega {
    
     @Override
    public void create(Entrega entrega) {
        String sql = "INSERT INTO Entrega (fecha_entrega, estado_entrega, id_organizacion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(entrega.getFechaEntrega().getTime()));
            ps.setString(2, entrega.getEstadoEntrega());
            ps.setInt(3, entrega.getIdOrganizacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar entrega: " + e.getMessage());
        }
    }

     @Override
    public Entrega read(int id) {
        String sql = "SELECT * FROM Entrega WHERE id_entrega=?";
        Entrega entrega = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entrega = new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getDate("fecha_entrega"),
                        rs.getString("estado_entrega"),
                        rs.getInt("id_organizacion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer entrega: " + e.getMessage());
        }
        return entrega;
    }

    @Override
    public void update(Entrega entrega) {
        String sql = "UPDATE Entrega SET fecha_entrega=?, estado_entrega=?, id_organizacion=? WHERE id_entrega=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(entrega.getFechaEntrega().getTime()));
            ps.setString(2, entrega.getEstadoEntrega());
            ps.setInt(3, entrega.getIdOrganizacion());
            ps.setInt(4, entrega.getIdEntrega());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar entrega: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Entrega WHERE id_entrega=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar entrega: " + e.getMessage());
        }
    }

    @Override
    public List<Entrega> readAll() {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM Entrega";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Entrega entrega = new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getDate("fecha_entrega"),
                        rs.getString("estado_entrega"),
                        rs.getInt("id_organizacion")
                );
                lista.add(entrega);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener entregas: " + e.getMessage());
        }
        return lista;
    }
    
}
