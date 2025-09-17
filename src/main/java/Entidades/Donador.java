/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Laptop
 */
public class Donador {
    
      private int idDonador;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoDonador;
    private String correo;
    private String telefono;
    private int idDireccion;

    public Donador() {}

    public Donador(int idDonador, String nombre, String apellidoPaterno, String apellidoMaterno, String tipoDonador, String correo, String telefono, int idDireccion) {
        this.idDonador = idDonador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.tipoDonador = tipoDonador;
        this.correo = correo;
        this.telefono = telefono;
        this.idDireccion = idDireccion;
    }



    public int getIdDonador() {
        return idDonador;
    }

    public void setIdDonador(int idDonador) {
        this.idDonador = idDonador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTipoDonador() {
        return tipoDonador;
    }

    public void setTipoDonador(String tipoDonador) {
        this.tipoDonador = tipoDonador;
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
        return "Donador{" + "idDonador=" + idDonador + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", tipoDonador=" + tipoDonador + ", correo=" + correo + ", telefono=" + telefono + ", idDireccion=" + idDireccion + '}';
    }

  
    
}
