package Excepciones;

public class OrganizacionException extends Exception {

    public OrganizacionException(String mensaje) {
        super(mensaje);
    }

    public OrganizacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}