/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.Direccion;
import InterfacesDAO.IDireccion;
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
public class DireccionDAO implements IDireccion{

     @Override
    public void create(Direccion direccion) {
        String sql = "INSERT INTO Direccion (calle, numero, colonia, ciudad, estado, codigo_postal) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getNumero());
            ps.setString(3, direccion.getColonia());
            ps.setString(4, direccion.getCiudad());
            ps.setString(5, direccion.getEstado());
            ps.setString(6, direccion.getCodigoPostal());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar dirección: " + e.getMessage());
        }
    }

    @Override
    public Direccion read(int id) {
        String sql = "SELECT * FROM Direccion WHERE id_direccion=?";
        Direccion direccion = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                direccion = new Direccion(
                        rs.getInt("id_direccion"),
                        rs.getString("calle"),
                        rs.getString("numero"),
                        rs.getString("colonia"),
                        rs.getString("ciudad"),
                        rs.getString("estado"),
                        rs.getString("codigo_postal")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer dirección: " + e.getMessage());
        }
        return direccion;
    }

    @Override
    public void update(Direccion direccion) {
        String sql = "UPDATE Direccion SET calle=?, numero=?, colonia=?, ciudad=?, estado=?, codigo_postal=? WHERE id_direccion=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getNumero());
            ps.setString(3, direccion.getColonia());
            ps.setString(4, direccion.getCiudad());
            ps.setString(5, direccion.getEstado());
            ps.setString(6, direccion.getCodigoPostal());
            ps.setInt(7, direccion.getIdDireccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar dirección: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Direccion WHERE id_direccion=?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar dirección: " + e.getMessage());
        }
    }

    @Override
    public List<Direccion> readAll() {
        List<Direccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Direccion";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Direccion direccion = new Direccion(
                        rs.getInt("id_direccion"),
                        rs.getString("calle"),
                        rs.getString("numero"),
                        rs.getString("colonia"),
                        rs.getString("ciudad"),
                        rs.getString("estado"),
                        rs.getString("codigo_postal")
                );
                lista.add(direccion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener direcciones: " + e.getMessage());
        }
        return lista;
    }
    
}
