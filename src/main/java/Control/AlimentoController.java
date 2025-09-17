package Control;

import DAO.AlimentoDAO;
import Entidades.Alimento;
import Excepciones.AlimentoException;
import java.util.List;

public class AlimentoController {

    private AlimentoDAO  alimentoDAO;

    public AlimentoController() {
        this.alimentoDAO = new AlimentoDAO();
    }

    public void crearAlimento(Alimento alimento) throws AlimentoException {
        try {
            alimentoDAO.create(alimento);
        } catch (Exception e) {
            throw new AlimentoException("Error al crear alimento", e);
        }
    }

    public Alimento obtenerAlimento(int id) throws AlimentoException {
        try {
            return alimentoDAO.read(id);
        } catch (Exception e) {
            throw new AlimentoException("Error al obtener alimento con ID: " + id, e);
        }
    }

    public List<Alimento> obtenerTodosAlimentos() throws AlimentoException {
        try {
            return alimentoDAO.readAll();
        } catch (Exception e) {
            throw new AlimentoException("Error al obtener todos los alimentos", e);
        }
    }

    public void actualizarAlimento(Alimento alimento) throws AlimentoException {
        try {
            alimentoDAO.update(alimento);
        } catch (Exception e) {
            throw new AlimentoException("Error al actualizar alimento con ID: " + alimento.getIdAlimento(), e);
        }
    }

    public void eliminarAlimento(int id) throws AlimentoException {
        try {
            alimentoDAO.delete(id);
        } catch (Exception e) {
            throw new AlimentoException("Error al eliminar alimento con ID: " + id, e);
        }
    }
}