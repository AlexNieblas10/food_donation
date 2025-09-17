/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.sql.Date;

/**
 *
 * @author Laptop
 */
public class Entrega {
    private int idEntrega;
    private Date fechaEntrega;
    private String estadoEntrega;
    private int idOrganizacion;

    public Entrega (){
        
    }

    public Entrega(int idEntrega, Date fechaEntrega, String estadoEntrega, int idOrganizacion) {
        this.idEntrega = idEntrega;
        this.fechaEntrega = fechaEntrega;
        this.estadoEntrega = estadoEntrega;
        this.idOrganizacion = idOrganizacion;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    @Override
    public String toString() {
        return "Entrega{" + "idEntrega=" + idEntrega + ", fechaEntrega=" + fechaEntrega + ", estadoEntrega=" + estadoEntrega + ", idOrganizacion=" + idOrganizacion + '}';
    }
    
    
    
}
