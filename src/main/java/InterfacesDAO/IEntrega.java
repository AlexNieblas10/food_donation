/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesDAO;

import Entidades.Alimento;
import Entidades.Entrega;
import java.util.List;

/**
 *
 * @author Laptop
 */
public interface IEntrega {
    void create(Entrega entrega);
    Entrega read(int id);
    void update(Entrega entrega);
    void delete(int id);
    List<Entrega> readAll();
}
