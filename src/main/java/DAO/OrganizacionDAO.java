/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.Organizacion;
import InterfacesDAO.IOrganizacion;
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
public class OrganizacionDAO implements IOrganizacion {
    
        @Override
    public void create(Organizacion org) {
        String sql = "INSERT INTO Organizacion (nombre_organizacion, nombre_responsable, correo_electronico, telefono, id_direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, org.getNombreOrganizacion());
            ps.setString(2, org.getNombreResponsable());
            ps.setString(3, org.getCorreo());
            ps.setString(4, org.getTelefono());
            ps.setInt(5, org.getIdDireccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar organización: " + e.getMessage());
        }
    }

    @Override
    public Organizacion read(int id) {
        String sql = "SELECT * FROM Organizacion WHERE id_organizacion=?";
        Organizacion org = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                org = new Organizacion(
                        rs.getInt("id_organizacion"),
                        rs.getString("nombre_organizacion"),
                        rs.getString("nombre_responsable"),
                        rs.getString("correo_electronico"),
                        rs.getString("telefono"),
                        rs.getInt("id_direccion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer organización: " + e.getMessage());
        }
        return org;
    }

    @Override
    public void update(Organizacion org) {
        String sql = "UPDATE Organizacion SET nombre_organizacion=?, nombre_responsable=?, correo_electronico=?, telefono=?, id_direccion=? WHERE id_organizacion=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, org.getNombreOrganizacion());
            ps.setString(2, org.getNombreResponsable());
            ps.setString(3, org.getCorreo());
            ps.setString(4, org.getTelefono());
            ps.setInt(5, org.getIdDireccion());
            ps.setInt(6, org.getIdOrganizacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar organización: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Organizacion WHERE id_organizacion=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar organización: " + e.getMessage());
        }
    }

    @Override
    public List<Organizacion> readAll() {
        List<Organizacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Organizacion";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Organizacion org = new Organizacion(
                        rs.getInt("id_organizacion"),
                        rs.getString("nombre_organizacion"),
                        rs.getString("nombre_responsable"),
                        rs.getString("correo_electronico"),
                        rs.getString("telefono"),
                        rs.getInt("id_direccion")
                );
                lista.add(org);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener organizaciones: " + e.getMessage());
        }
        return lista;
    }
}
