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
import javax.swing.JOptionPane;

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
    
      /**
     * Obtiene una lista de todos los alimentos con cantidad disponible > 0.
     * @return Lista de alimentos.
     */
    public List<Alimento> obtenerAlimentosDisponibles() {
        List<Alimento> alimentos = new ArrayList<>();
        // Solo trae alimentos con existencias y que no hayan caducado
        String sql = "SELECT * FROM Alimento WHERE cantidad_disponible > 0 AND fecha_caducidad >= CURDATE()";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Alimento alimento = new Alimento();
                alimento.setIdAlimento(rs.getInt("id_alimento"));
                alimento.setNombreAlimento(rs.getString("nombre_alimento"));
                alimento.setCategoria(rs.getString("categoria"));
                alimento.setCantidadDisponible(rs.getDouble("cantidad_disponible"));
                alimento.setFechaCaducidad(rs.getDate("fecha_caducidad"));
                alimento.setIdDonador(rs.getInt("id_donador"));
                alimentos.add(alimento);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar alimentos: " + e.getMessage());
        }
        return alimentos;
    }
    
     /**
     * Actualiza la cantidad disponible de un alimento.
     * @param idAlimento ID del alimento a actualizar.
     * @param cantidadARestar Cantidad que se va a restar del inventario.
     * @param conn Conexión a la BD (para transacciones).
     * @return true si la actualización fue exitosa.
     * @throws SQLException Si ocurre un error.
     */
    public boolean actualizarCantidad(int idAlimento, double cantidadARestar, Connection conn) throws SQLException {
        String sql = "UPDATE Alimento SET cantidad_disponible = cantidad_disponible - ? WHERE id_alimento = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, cantidadARestar);
            pstmt.setInt(2, idAlimento);
            return pstmt.executeUpdate() > 0;
        }
    }
    
     
    public boolean devolverCantidad(int idAlimento, double cantidadADevolver, Connection conn) throws SQLException {
        String sql = "UPDATE Alimento SET cantidad_disponible = cantidad_disponible + ? WHERE id_alimento = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, cantidadADevolver);
            pstmt.setInt(2, idAlimento);
            return pstmt.executeUpdate() > 0;
        }
    }
}
