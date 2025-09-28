/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.DetalleEntrega;
import InterfacesDAO.IDetalleEntrega;
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
public class DetalleEntregaDAO implements IDetalleEntrega {
    
      @Override
    public void create(DetalleEntrega detalle) {
        String sql = "INSERT INTO Detalle_Entrega (id_entrega, id_alimento, cantidad_entregada) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getIdEntrega());
            ps.setInt(2, detalle.getIdAlimento());
            ps.setDouble(3, detalle.getCantidadEntregada());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar detalle de entrega: " + e.getMessage());
        }
    }

    @Override
    public DetalleEntrega read(int id) {
        String sql = "SELECT * FROM Detalle_Entrega WHERE id_detalle_entrega=?";
        DetalleEntrega detalle = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                detalle = new DetalleEntrega(
                        rs.getInt("id_detalle_entrega"),
                        rs.getInt("id_entrega"),
                        rs.getInt("id_alimento"),
                        rs.getDouble("cantidad_entregada")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer detalle de entrega: " + e.getMessage());
        }
        return detalle;
    }

    @Override
    public void update(DetalleEntrega detalle) {
        String sql = "UPDATE Detalle_Entrega SET id_entrega=?, id_alimento=?, cantidad_entregada=? WHERE id_detalle_entrega=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getIdEntrega());
            ps.setInt(2, detalle.getIdAlimento());
            ps.setDouble(3, detalle.getCantidadEntregada());
            ps.setInt(4, detalle.getIdDetalleEntrega());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar detalle de entrega: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Detalle_Entrega WHERE id_detalle_entrega=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle de entrega: " + e.getMessage());
        }
    }

    @Override
    public List<DetalleEntrega> readAll() {
        List<DetalleEntrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM Detalle_Entrega";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DetalleEntrega detalle = new DetalleEntrega(
                        rs.getInt("id_detalle_entrega"),
                        rs.getInt("id_entrega"),
                        rs.getInt("id_alimento"),
                        rs.getDouble("cantidad_entregada")
                );
                lista.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles de entrega: " + e.getMessage());
        }
        return lista;
    }
    
    
}
