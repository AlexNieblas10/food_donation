/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.Donador;
import Excepciones.DonadorException;
import InterfacesDAO.IDonador;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
    
    /**
     * Actualiza los datos de un donador existente en la base de datos.
     * @param donador El objeto Donador con los datos actualizados y el ID.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws DonadorException Si ocurre un error de SQL.
     */
    public boolean actualizarDonador(Donador donador) throws DonadorException {
        // La consulta SQL para actualizar todos los campos del donador basado en su ID
        String sql = "UPDATE Donador SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, " +
                     "tipo_donador = ?, correo_electronico = ?, telefono = ? WHERE id_donador = ?";
        
        try (Connection conn = ConexionDB.getConnection(); // Asegúrate de que tu clase de conexión se llame así
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Asignar los valores del objeto donador a la consulta preparada
            pstmt.setString(1, donador.getNombre());
            pstmt.setString(2, donador.getApellidoPaterno());
            pstmt.setString(3, donador.getApellidoMaterno());
            pstmt.setString(4, donador.getTipoDonador());
            pstmt.setString(5, donador.getCorreo()); // Asegúrate de que el getter se llame así
            pstmt.setString(6, donador.getTelefono());
            pstmt.setInt(7, donador.getIdDonador()); // ID para la cláusula WHERE

            int filasAfectadas = pstmt.executeUpdate();
            
            // La actualización es exitosa si se modificó al menos una fila
            return filasAfectadas > 0;

        } catch (SQLException e) {
            
            throw new DonadorException("Error al actualizar donador: " + e.getMessage(), e);
        }
    }
    
     public Donador obtenerDonadorPorId(int id) throws DonadorException {
        // Asegúrate que los nombres de las columnas y la tabla coincidan con tu base de datos
        String sql = "SELECT id_donador, nombre, apellido_paterno, apellido_materno, tipo_donador, " +
                     "correo, telefono, id_direccion FROM Donador WHERE id_donador = ?";
        Donador donador = null;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // Si se encuentra un registro
                    donador = new Donador();
                    donador.setIdDonador(rs.getInt("id_donador"));
                    donador.setNombre(rs.getString("nombre"));
                    donador.setApellidoPaterno(rs.getString("apellido_paterno"));
                    donador.setApellidoMaterno(rs.getString("apellido_materno"));
                    donador.setTipoDonador(rs.getString("tipo_donador"));
                    donador.setCorreo(rs.getString("correo"));
                    donador.setTelefono(rs.getString("telefono"));
                    donador.setIdDireccion(rs.getInt("id_direccion"));
                }
            }
        } catch (SQLException e) {
          
            throw new DonadorException("Error al obtener donador por ID: " + e.getMessage(), e);
        }
        return donador;
    }

}
