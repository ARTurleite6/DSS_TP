package business.exceptions;

/**
 * Excecao que é lancada quando campeonato nao existe
 */
public class CampeonatoNaoExisteException extends Exception {
    public CampeonatoNaoExisteException(String message) {
        super(message);
    }
}
