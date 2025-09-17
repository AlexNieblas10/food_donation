/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesDAO;

import Entidades.Direccion;
import java.util.List;

/**
 *
 * @author Laptop
 */
public interface IDireccion {
    
     void create(Direccion direccion);
    Direccion read(int id);
    void update(Direccion direccion);
    void delete(int id);
    List<Direccion> readAll();
}
