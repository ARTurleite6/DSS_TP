package business.exceptions;


/**
 * Exceção que é lançada quando se tenta criar um campeonato com um nome que já existe.
 */
public class CampeonatoJaExisteException extends Exception {
    public CampeonatoJaExisteException(String msg) {
        super(msg);
    }
}
