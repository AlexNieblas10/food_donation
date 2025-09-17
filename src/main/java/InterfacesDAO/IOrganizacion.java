/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesDAO;

import Entidades.Organizacion;
import java.util.List;

/**
 *
 * @author Laptop
 */
public interface IOrganizacion {
    void create(Organizacion organizacion);
    Organizacion read(int id);
    void update(Organizacion organizacion);
    void delete(int id);
    List<Organizacion> readAll();
}
