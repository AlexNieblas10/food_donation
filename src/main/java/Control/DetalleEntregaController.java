package Control;

import DAO.DetalleEntregaDAO;
import Entidades.DetalleEntrega;
import Excepciones.DetalleEntregaException;
import java.util.List;

public class DetalleEntregaController {

    private DetalleEntregaDAO detalleEntregaDAO;

    public DetalleEntregaController() {
        this.detalleEntregaDAO = new DetalleEntregaDAO();
    }

    public void crearDetalleEntrega(DetalleEntrega detalleEntrega) throws DetalleEntregaException {
        try {
            detalleEntregaDAO.create(detalleEntrega);
        } catch (Exception e) {
            throw new DetalleEntregaException("Error al crear detalle de entrega", e);
        }
    }

    public DetalleEntrega obtenerDetalleEntrega(int id) throws DetalleEntregaException {
        try {
            return detalleEntregaDAO.read(id);
        } catch (Exception e) {
            throw new DetalleEntregaException("Error al obtener detalle de entrega con ID: " + id, e);
        }
    }

    public List<DetalleEntrega> obtenerTodosDetallesEntrega() throws DetalleEntregaException {
        try {
            return detalleEntregaDAO.readAll();
        } catch (Exception e) {
            throw new DetalleEntregaException("Error al obtener todos los detalles de entrega", e);
        }
    }

    public void actualizarDetalleEntrega(DetalleEntrega detalleEntrega) throws DetalleEntregaException {
        try {
            detalleEntregaDAO.update(detalleEntrega);
        } catch (Exception e) {
            throw new DetalleEntregaException("Error al actualizar detalle de entrega con ID: " + detalleEntrega.getIdDetalleEntrega(), e);
        }
    }

    public void eliminarDetalleEntrega(int id) throws DetalleEntregaException {
        try {
            detalleEntregaDAO.delete(id);
        } catch (Exception e) {
            throw new DetalleEntregaException("Error al eliminar detalle de entrega con ID: " + id, e);
        }
    }
}