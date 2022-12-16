package business.exceptions;

public class NonLobbyCurrentlyRunningException extends Exception {
    public NonLobbyCurrentlyRunningException(String message) {
        super(message);
    }
}
