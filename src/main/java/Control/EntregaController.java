package Control;

import DAO.EntregaDAO;
import Entidades.Entrega;
import Excepciones.EntregaException;
import java.util.List;

public class EntregaController {

    private EntregaDAO entregaDAO;

    public EntregaController() {
        this.entregaDAO = new EntregaDAO();
    }

    public void crearEntrega(Entrega entrega) throws EntregaException {
        try {
            entregaDAO.create(entrega);
        } catch (Exception e) {
            throw new EntregaException("Error al crear entrega", e);
        }
    }

    public Entrega obtenerEntrega(int id) throws EntregaException {
        try {
            return entregaDAO.read(id);
        } catch (Exception e) {
            throw new EntregaException("Error al obtener entrega con ID: " + id, e);
        }
    }

    public List<Entrega> obtenerTodasEntregas() throws EntregaException {
        try {
            return entregaDAO.readAll();
        } catch (Exception e) {
            throw new EntregaException("Error al obtener todas las entregas", e);
        }
    }

    public void actualizarEntrega(Entrega entrega) throws EntregaException {
        try {
            entregaDAO.update(entrega);
        } catch (Exception e) {
            throw new EntregaException("Error al actualizar entrega con ID: " + entrega.getIdEntrega(), e);
        }
    }

    public void eliminarEntrega(int id) throws EntregaException {
        try {
            entregaDAO.delete(id);
        } catch (Exception e) {
            throw new EntregaException("Error al eliminar entrega con ID: " + id, e);
        }
    }
}