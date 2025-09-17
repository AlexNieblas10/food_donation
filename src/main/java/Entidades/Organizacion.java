/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Laptop
 */
public class Organizacion {
    private int idOrganizacion;
    private String nombreOrganizacion;
    private String nombreResponsable;
    private String correo;
    private String telefono;
    private int idDireccion;
    
    public Organizacion() {
}

    public Organizacion(int idOrganizacion, String nombreOrganizacion, String nombreResponsable, String correo, String telefono, int idDireccion) {
        this.idOrganizacion = idOrganizacion;
        this.nombreOrganizacion = nombreOrganizacion;
        this.nombreResponsable = nombreResponsable;
        this.correo = correo;
        this.telefono = telefono;
        this.idDireccion = idDireccion;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    @Override
    public String toString() {
        return "Organizacion{" + "idOrganizacion=" + idOrganizacion + ", nombreOrganizacion=" + nombreOrganizacion + ", nombreResponsable=" + nombreResponsable + ", correo=" + correo + ", telefono=" + telefono + ", idDireccion=" + idDireccion + '}';
    }
    
    
    
}
