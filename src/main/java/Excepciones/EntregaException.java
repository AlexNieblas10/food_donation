package Excepciones;

public class EntregaException extends Exception {

    public EntregaException(String mensaje) {
        super(mensaje);
    }

    public EntregaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}