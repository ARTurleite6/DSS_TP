package users;

public interface Autenticavel extends Identificavel {
    boolean login(String username, String password);
    boolean estaAutenticado();
    void setPassword(String password);
    String getPassword();
    void logOut();

    Autenticavel clone();
}
