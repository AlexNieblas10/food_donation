/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesDAO;

import Entidades.Donador;
import java.util.List;

/**
 *
 * @author Laptop
 */
public interface IDonador {
    
    void create(Donador donador);
    Donador read(int id);
    void update(Donador donador);
    void delete(int id);
    List<Donador> readAll();
}
