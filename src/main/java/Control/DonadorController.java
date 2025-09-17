package Control;

import DAO.DonadorDAO;
import Entidades.Donador;
import Excepciones.DonadorException;
import java.util.List;

public class DonadorController {

    private DonadorDAO donadorDAO;

    public DonadorController() {
        this.donadorDAO = new DonadorDAO();
    }

    public void crearDonador(Donador donador) throws DonadorException {
        try {
            donadorDAO.create(donador);
        } catch (Exception e) {
            throw new DonadorException("Error al crear donador", e);
        }
    }

    public Donador obtenerDonador(int id) throws DonadorException {
        try {
            return donadorDAO.read(id);
        } catch (Exception e) {
            throw new DonadorException("Error al obtener donador con ID: " + id, e);
        }
    }

    public List<Donador> obtenerTodosDonadores() throws DonadorException {
        try {
            return donadorDAO.readAll();
        } catch (Exception e) {
            throw new DonadorException("Error al obtener todos los donadores", e);
        }
    }

    public void actualizarDonador(Donador donador) throws DonadorException {
        try {
            donadorDAO.update(donador);
        } catch (Exception e) {
            throw new DonadorException("Error al actualizar donador con ID: " + donador.getIdDonador(), e);
        }
    }

    public void eliminarDonador(int id) throws DonadorException {
        try {
            donadorDAO.delete(id);
        } catch (Exception e) {
            throw new DonadorException("Error al eliminar donador con ID: " + id, e);
        }
    }
}