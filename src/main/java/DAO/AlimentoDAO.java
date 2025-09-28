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
import java.sql.Statement;
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
    
        /**
     * Inserta un nuevo alimento en la base de datos.
     * @param alimento Objeto Alimento con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertarAlimento(Alimento alimento) {
        String sql = "INSERT INTO Alimento (nombre_alimento, categoria, cantidad_disponible, fecha_caducidad, id_donador) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Permite obtener el ID generado

            pstmt.setString(1, alimento.getNombreAlimento());
            pstmt.setString(2, alimento.getCategoria());
            pstmt.setDouble(3, alimento.getCantidadDisponible());
            pstmt.setDate(4, new java.sql.Date(alimento.getFechaCaducidad().getTime()));
            
            // Si idDonador es 0 (o algún valor nulo), inserta NULL en la BD
            if (alimento.getIdDonador() > 0) {
                pstmt.setInt(5, alimento.getIdDonador());
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }

            int filasAfectadas = pstmt.executeUpdate();
            
           
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        alimento.setIdAlimento(rs.getInt(1));
                    }
                }
            }
            return filasAfectadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar alimento: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los alimentos registrados en la base de datos.
     * @return Lista de objetos Alimento.
     */
    public List<Alimento> obtenerTodosLosAlimentos() {
        List<Alimento> alimentos = new ArrayList<>();
        // No filtramos por cantidad disponible o fecha de caducidad aquí,
        // porque en la vista de registro queremos ver TODOS los alimentos.
        String sql = "SELECT * FROM Alimento";

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
            JOptionPane.showMessageDialog(null, "Error al obtener alimentos: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return alimentos;
    }
    
    private Alimento crearAlimentoDesdeResultSet(ResultSet rs) throws SQLException {
        Alimento alimento = new Alimento();
        alimento.setIdAlimento(rs.getInt("id_alimento"));
        alimento.setNombreAlimento(rs.getString("nombre_alimento"));
        alimento.setCategoria(rs.getString("categoria"));
        alimento.setCantidadDisponible(rs.getDouble("cantidad_disponible"));
        alimento.setFechaCaducidad(rs.getDate("fecha_caducidad"));
        alimento.setIdDonador(rs.getInt("id_donador"));
        
        // Manejo si id_donador es NULL en la base de datos
        if (rs.wasNull()) { // Verifica si el último valor leído fue NULL
            alimento.setIdDonador(0); // Establece a 0 o algún valor que indique ausencia de donador
        }
        return alimento;
    }

    /**
     * Actualiza los datos de un alimento existente.
     * @param alimento Objeto Alimento con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarAlimento(Alimento alimento) {
        String sql = "UPDATE Alimento SET nombre_alimento = ?, categoria = ?, cantidad_disponible = ?, fecha_caducidad = ?, id_donador = ? WHERE id_alimento = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, alimento.getNombreAlimento());
            pstmt.setString(2, alimento.getCategoria());
            pstmt.setDouble(3, alimento.getCantidadDisponible());
            pstmt.setDate(4, new java.sql.Date(alimento.getFechaCaducidad().getTime()));
            
            if (alimento.getIdDonador() > 0) {
                pstmt.setInt(5, alimento.getIdDonador());
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.setInt(6, alimento.getIdAlimento()); // Clave para el WHERE

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar alimento: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un alimento de la base de datos por su ID.
     * @param idAlimento ID del alimento a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarAlimento(int idAlimento) {
        String sql = "DELETE FROM Alimento WHERE id_alimento = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAlimento);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            // Manejo específico si hay una FK constraint violation (ej: el alimento está en una entrega)
            if (e.getSQLState().startsWith("23")) { // SQLState para integridad referencial (MySQL)
                 JOptionPane.showMessageDialog(null, "No se puede eliminar el alimento porque está asociado a una entrega.", "Error de Integridad", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar alimento: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
            return false;
        }
    }
    
     /**
     * Busca alimentos por su nombre (o parte del nombre).
     * @param nombreBuscado El nombre (o parte del nombre) del alimento a buscar.
     * @return Lista de objetos Alimento que coinciden con la búsqueda.
     */
    public List<Alimento> buscarAlimentos(String nombreBuscado) {
        List<Alimento> alimentos = new ArrayList<>();
        String sql = "SELECT * FROM Alimento WHERE nombre_alimento LIKE ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombreBuscado + "%"); // Para buscar coincidencias parciales

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Alimento alimento = crearAlimentoDesdeResultSet(rs);
                    alimentos.add(alimento);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar alimentos: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return alimentos;
    }
}
