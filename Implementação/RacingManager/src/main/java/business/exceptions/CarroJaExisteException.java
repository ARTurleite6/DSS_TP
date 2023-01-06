package business.exceptions;

/**
 * Excecao que é lancada quando Carro já existe
 */
public class CarroJaExisteException extends Exception {
    public CarroJaExisteException(String message) {
        super(message);
    }
}
