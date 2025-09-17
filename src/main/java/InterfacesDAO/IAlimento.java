/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesDAO;

import Entidades.Alimento;
import java.util.List;

/**
 *
 * @author Laptop
 */
public interface IAlimento {
     void create(Alimento alimento);
    Alimento read(int id);
    void update(Alimento alimento);
    void delete(int id);
    List<Alimento> readAll();
}
