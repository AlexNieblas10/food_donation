/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.Alimento;
import InterfacesDAO.IAlimento;
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
public class AlimentoDAO implements IAlimento {
    
    /**
     *
     * @param alimento
     */
    @Override
    public void create(Alimento alimento) {
        String sql = "INSERT INTO Alimento (nombre_alimento, categoria, cantidad_disponible, fecha_caducidad, id_donador) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alimento.getNombreAlimento());
            ps.setString(2, alimento.getCategoria());
            ps.setDouble(3, alimento.getCantidadDisponible());
            ps.setDate(4, new java.sql.Date(alimento.getFechaCaducidad().getTime()));
            ps.setInt(5, alimento.getIdDonador());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar alimento: " + e.getMessage());
        }
    }

    @Override
    public Alimento read(int id) {
        String sql = "SELECT * FROM Alimento WHERE id_alimento=?";
        Alimento alimento = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alimento = new Alimento(
                        rs.getInt("id_alimento"),
                        rs.getString("nombre_alimento"),
                        rs.getString("categoria"),
                        rs.getDouble("cantidad_disponible"),
                        rs.getDate("fecha_caducidad"),
                        rs.getInt("id_donador")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer alimento: " + e.getMessage());
        }
        return alimento;
    }

    @Override
    public void update(Alimento alimento) {
        String sql = "UPDATE Alimento SET nombre_alimento=?, categoria=?, cantidad_disponible=?, fecha_caducidad=?, id_donador=? WHERE id_alimento=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alimento.getNombreAlimento());
            ps.setString(2, alimento.getCategoria());
            ps.setDouble(3, alimento.getCantidadDisponible());
            ps.setDate(4, new java.sql.Date(alimento.getFechaCaducidad().getTime()));
            ps.setInt(5, alimento.getIdDonador());
            ps.setInt(6, alimento.getIdAlimento());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar alimento: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Alimento WHERE id_alimento=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar alimento: " + e.getMessage());
        }
    }

    @Override
    public List<Alimento> readAll() {
        List<Alimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Alimento";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Alimento alimento = new Alimento(
                        rs.getInt("id_alimento"),
                        rs.getString("nombre_alimento"),
                        rs.getString("categoria"),
                        rs.getDouble("cantidad_disponible"),
                        rs.getDate("fecha_caducidad"),
                        rs.getInt("id_donador")
                );
                lista.add(alimento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener alimentos: " + e.getMessage());
        }
        return lista;
    }
}
