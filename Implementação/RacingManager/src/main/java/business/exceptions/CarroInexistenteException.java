package business.exceptions;

/**
 * Excecao qu eé lancada quando carro nao existe
 */
public class CarroInexistenteException extends Exception {
    public CarroInexistenteException(String message) {
        super(message);
    }
}
