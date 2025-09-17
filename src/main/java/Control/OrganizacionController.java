package Control;

import DAO.OrganizacionDAO;
import Entidades.Organizacion;
import Excepciones.OrganizacionException;
import java.util.List;

public class OrganizacionController {

    private OrganizacionDAO organizacionDAO;

    public OrganizacionController() {
        this.organizacionDAO = new OrganizacionDAO();
    }

    public void crearOrganizacion(Organizacion organizacion) throws OrganizacionException {
        try {
            organizacionDAO.create(organizacion);
        } catch (Exception e) {
            throw new OrganizacionException("Error al crear organización", e);
        }
    }

    public Organizacion obtenerOrganizacion(int id) throws OrganizacionException {
        try {
            return organizacionDAO.read(id);
        } catch (Exception e) {
            throw new OrganizacionException("Error al obtener organización con ID: " + id, e);
        }
    }

    public List<Organizacion> obtenerTodasOrganizaciones() throws OrganizacionException {
        try {
            return organizacionDAO.readAll();
        } catch (Exception e) {
            throw new OrganizacionException("Error al obtener todas las organizaciones", e);
        }
    }

    public void actualizarOrganizacion(Organizacion organizacion) throws OrganizacionException {
        try {
            organizacionDAO.update(organizacion);
        } catch (Exception e) {
            throw new OrganizacionException("Error al actualizar organización con ID: " + organizacion.getIdOrganizacion(), e);
        }
    }

    public void eliminarOrganizacion(int id) throws OrganizacionException {
        try {
            organizacionDAO.delete(id);
        } catch (Exception e) {
            throw new OrganizacionException("Error al eliminar organización con ID: " + id, e);
        }
    }
}