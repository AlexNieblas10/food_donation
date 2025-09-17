package Excepciones;

public class AlimentoException extends Exception {

    public AlimentoException(String mensaje) {
        super(mensaje);
    }

    public AlimentoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}