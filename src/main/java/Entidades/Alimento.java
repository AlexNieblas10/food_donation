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
public class Alimento {
    private int idAlimento;
    private String nombreAlimento;
    private String categoria;
    private double cantidadDisponible;
    private Date fechaCaducidad;
    private int idDonador;
    
    public Alimento(){
        
    }

    public Alimento(int idAlimento, String nombreAlimento, String categoria, double cantidadDisponible, Date fechaCaducidad, int idDonador) {
        this.idAlimento = idAlimento;
        this.nombreAlimento = nombreAlimento;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaCaducidad = fechaCaducidad;
        this.idDonador = idDonador;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getIdDonador() {
        return idDonador;
    }

    public void setIdDonador(int idDonador) {
        this.idDonador = idDonador;
    }

    @Override
    public String toString() {
        return "Alimento{" + "idAlimento=" + idAlimento + ", nombreAlimento=" + nombreAlimento + ", categoria=" + categoria + ", cantidadDisponible=" + cantidadDisponible + ", fechaCaducidad=" + fechaCaducidad + ", idDonador=" + idDonador + '}';
    }
    
    
}
