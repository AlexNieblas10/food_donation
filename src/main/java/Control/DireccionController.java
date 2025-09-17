package Control;

import DAO.DireccionDAO;
import Entidades.Direccion;
import Excepciones.DireccionException;
import java.util.List;

public class DireccionController {

    private DireccionDAO direccionDAO;

    public DireccionController() {
        this.direccionDAO = new DireccionDAO();
    }

    public void crearDireccion(Direccion direccion) throws DireccionException {
        try {
            direccionDAO.create(direccion);
        } catch (Exception e) {
            throw new DireccionException("Error al crear dirección", e);
        }
    }

    public Direccion obtenerDireccion(int id) throws DireccionException {
        try {
            return direccionDAO.read(id);
        } catch (Exception e) {
            throw new DireccionException("Error al obtener dirección con ID: " + id, e);
        }
    }

    public List<Direccion> obtenerTodasDirecciones() throws DireccionException {
        try {
            return direccionDAO.readAll();
        } catch (Exception e) {
            throw new DireccionException("Error al obtener todas las direcciones", e);
        }
    }

    public void actualizarDireccion(Direccion direccion) throws DireccionException {
        try {
            direccionDAO.update(direccion);
        } catch (Exception e) {
            throw new DireccionException("Error al actualizar dirección con ID: " + direccion.getIdDireccion(), e);
        }
    }

    public void eliminarDireccion(int id) throws DireccionException {
        try {
            direccionDAO.delete(id);
        } catch (Exception e) {
            throw new DireccionException("Error al eliminar dirección con ID: " + id, e);
        }
    }
}