/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesDAO;

import Entidades.DetalleEntrega;
import java.util.List;

/**
 *
 * @author Laptop
 */
public interface IDetalleEntrega {
      void create(DetalleEntrega detalle);
    DetalleEntrega read(int id);
    void update(DetalleEntrega detalle);
    void delete(int id);
    List<DetalleEntrega> readAll();
}
