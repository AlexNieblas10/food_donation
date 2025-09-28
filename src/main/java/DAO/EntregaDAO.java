/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConexionDB.ConexionDB;
import Entidades.DetalleEntrega;
import Entidades.Entrega;
import InterfacesDAO.IEntrega;
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
    
    /**
     * Obtiene una lista de todas las entregas con detalles para mostrar en la tabla.
     * @return Una lista de arreglos de objetos para la JTable.
     */
    public List<Object[]> obtenerEntregasParaTabla() {
        List<Object[]> entregas = new ArrayList<>();
        String sql = "SELECT e.id_entrega, o.nombre_organizacion, a.nombre_alimento, de.cantidad_entregada, e.fecha_entrega, e.estado_entrega, de.id_alimento " +
                     "FROM Entrega e " +
                     "JOIN Organizacion o ON e.id_organizacion = o.id_organizacion " +
                     "JOIN Detalle_Entrega de ON e.id_entrega = de.id_entrega " +
                     "JOIN Alimento a ON de.id_alimento = a.id_alimento " +
                     "ORDER BY e.fecha_entrega DESC";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_entrega"),
                    rs.getString("nombre_organizacion"),
                    rs.getString("nombre_alimento"),
                    rs.getDouble("cantidad_entregada"),
                    rs.getTimestamp("fecha_entrega"),
                    rs.getString("estado_entrega"),
                    rs.getInt("id_alimento") // ID del alimento oculto para devoluciones
                };
                entregas.add(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar entregas: " + e.getMessage());
        }
        return entregas;
    }
    
    /**
     * Actualiza el estado de una entrega específica.
     * @param idEntrega El ID de la entrega a actualizar.
     * @param nuevoEstado El nuevo estado.
     * @return true si fue exitoso.
     */
    public boolean actualizarEstado(int idEntrega, String nuevoEstado) {
        String sql = "UPDATE Entrega SET estado_entrega = ? WHERE id_entrega = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idEntrega);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cancela una entrega: cambia el estado y devuelve la cantidad al inventario.
     * @param idEntrega El ID de la entrega a cancelar.
     * @param idAlimento El ID del alimento a devolver.
     * @param cantidadDevolver La cantidad a devolver.
     * @return true si la transacción fue exitosa.
     */
    public boolean cancelarEntrega(int idEntrega, int idAlimento, double cantidadDevolver) {
         Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Cambiar estado a 'cancelada'
            if (!actualizarEstado(idEntrega, "cancelada")) {
                throw new SQLException("No se pudo actualizar el estado de la entrega.");
            }
            
            // 2. Devolver cantidad al inventario
            AlimentoDAO.devolverCantidad(idAlimento, cantidadDevolver, conn);

            conn.commit(); // Confirmar transacción
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la transacción de cancelación: " + e.getMessage());
            if (conn != null) { try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            return false;
        } finally {
            if (conn != null) { try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }
    
     private AlimentoDAO AlimentoDAO = new AlimentoDAO();

 
     /**
     * Inserta una nueva entrega y su detalle, y actualiza el stock del alimento.
     * Todo dentro de una transacción para garantizar la integridad de los datos.
     * @param entrega Objeto Entrega a insertar.
     * @param detalle Objeto DetalleEntrega a insertar.
     * @return true si toda la operación es exitosa.
     */
    public boolean registrarEntregaCompleta(Entrega entrega, DetalleEntrega detalle) {
        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            // Iniciar transacción
            conn.setAutoCommit(false);

            // 1. Insertar la Entrega y obtener el ID generado
            String sqlEntrega = "INSERT INTO Entrega (fecha_entrega, estado_entrega, id_organizacion) VALUES (NOW(), ?, ?)";
            try (PreparedStatement pstmtEntrega = conn.prepareStatement(sqlEntrega, Statement.RETURN_GENERATED_KEYS)) {
                pstmtEntrega.setString(1, entrega.getEstadoEntrega());
                pstmtEntrega.setInt(2, entrega.getIdOrganizacion());
                pstmtEntrega.executeUpdate();

                try (ResultSet generatedKeys = pstmtEntrega.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        detalle.setIdEntrega(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("No se pudo obtener el ID de la entrega.");
                    }
                }
            }
    
            
            
            // 2. Insertar el Detalle de la Entrega
            String sqlDetalle = "INSERT INTO Detalle_Entrega (id_entrega, id_alimento, cantidad_entregada) VALUES (?, ?, ?)";
            try (PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle)) {
                pstmtDetalle.setInt(1, detalle.getIdEntrega());
                pstmtDetalle.setInt(2, detalle.getIdAlimento());
                pstmtDetalle.setDouble(3, detalle.getCantidadEntregada());
                pstmtDetalle.executeUpdate();
            }

            // 3. Actualizar la cantidad del alimento
            
            AlimentoDAO.actualizarCantidad(detalle.getIdAlimento(),detalle.getCantidadEntregada(), conn);

            // Si todo fue bien, confirmar la transacción
            conn.commit();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la transacción de entrega: " + e.getMessage());
            if (conn != null) {
                try {
                    // Si algo falló, revertir todos los cambios
                    conn.rollback();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al revertir la transacción: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    // Devolver al modo de auto-commit
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
