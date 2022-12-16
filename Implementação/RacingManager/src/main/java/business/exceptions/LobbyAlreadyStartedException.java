package business.exceptions;


public class LobbyAlreadyStartedException extends Exception {
    public LobbyAlreadyStartedException(String message) {
        super(message);
    }
}
