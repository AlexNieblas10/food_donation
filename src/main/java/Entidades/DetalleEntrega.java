/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Laptop
 */
public class DetalleEntrega {
    
    private int idDetalleEntrega;
    private int idEntrega;
    private int idAlimento;
    private double cantidadEntregada;
    
    public DetalleEntrega(){
    }

    public DetalleEntrega(int idDetalleEntrega, int idEntrega, int idAlimento, double cantidadEntregada) {
        this.idDetalleEntrega = idDetalleEntrega;
        this.idEntrega = idEntrega;
        this.idAlimento = idAlimento;
        this.cantidadEntregada = cantidadEntregada;
    }

    public int getIdDetalleEntrega() {
        return idDetalleEntrega;
    }

    public void setIdDetalleEntrega(int idDetalleEntrega) {
        this.idDetalleEntrega = idDetalleEntrega;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public double getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(double cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    @Override
    public String toString() {
        return "DetalleEntrega{" + "idDetalleEntrega=" + idDetalleEntrega + ", idEntrega=" + idEntrega + ", idAlimento=" + idAlimento + ", cantidadEntregada=" + cantidadEntregada + '}';
    }
    
    
}
