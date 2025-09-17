package Excepciones;

public class DonadorException extends Exception {

    public DonadorException(String mensaje) {
        super(mensaje);
    }

    public DonadorException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}