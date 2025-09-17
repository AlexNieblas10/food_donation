package Excepciones;

public class DetalleEntregaException extends Exception {

    public DetalleEntregaException(String mensaje) {
        super(mensaje);
    }

    public DetalleEntregaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}