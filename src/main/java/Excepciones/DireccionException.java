package Excepciones;

public class DireccionException extends Exception {

    public DireccionException(String mensaje) {
        super(mensaje);
    }

    public DireccionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}