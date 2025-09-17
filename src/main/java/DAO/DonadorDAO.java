/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.Donador;
import InterfacesDAO.IDonador;
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
public class DonadorDAO implements IDonador {
    
    /**
     *
     * @param donador
     */
    @Override
    public void create(Donador donador) {
        String sql = "INSERT INTO Donador (nombre, apellido_paterno, apellido_materno, tipo_donador, correo_electronico, telefono, id_direccion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, donador.getNombre());
            ps.setString(2, donador.getApellidoPaterno());
            ps.setString(3, donador.getApellidoMaterno());
            ps.setString(4, donador.getTipoDonador());
            ps.setString(5, donador.getCorreo());
            ps.setString(6, donador.getTelefono());
            ps.setInt(7, donador.getIdDireccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar donador: " + e.getMessage());
        }
    }

    @Override
    public Donador read(int id) {
        String sql = "SELECT * FROM Donador WHERE id_donador=?";
        Donador donador = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                donador = new Donador(
                        rs.getInt("id_donador"),
                        rs.getString("nombre"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("tipo_donador"),
                        rs.getString("correo_electronico"),
                        rs.getString("telefono"),
                        rs.getInt("id_direccion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer donador: " + e.getMessage());
        }
        return donador;
    }

    @Override
    public void update(Donador donador) {
        String sql = "UPDATE Donador SET nombre=?, apellido_paterno=?, apellido_materno=?, tipo_donador=?, correo_electronico=?, telefono=?, id_direccion=? WHERE id_donador=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, donador.getNombre());
            ps.setString(2, donador.getApellidoPaterno());
            ps.setString(3, donador.getApellidoMaterno());
            ps.setString(4, donador.getTipoDonador());
            ps.setString(5, donador.getCorreo());
            ps.setString(6, donador.getTelefono());
            ps.setInt(7, donador.getIdDireccion());
            ps.setInt(8, donador.getIdDonador());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar donador: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Donador WHERE id_donador=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar donador: " + e.getMessage());
        }
    }

    @Override
    public List<Donador> readAll() {
        List<Donador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Donador";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Donador donador = new Donador(
                        rs.getInt("id_donador"),
                        rs.getString("nombre"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("tipo_donador"),
                        rs.getString("correo_electronico"),
                        rs.getString("telefono"),
                        rs.getInt("id_direccion")
                );
                lista.add(donador);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener donadores: " + e.getMessage());
        }
        return lista;
    }
}
