package users;

public interface Autenticavel {
    boolean login(String username, String password);
    boolean estaAutenticado();
    void setPassword(String password);
    void logOut();
}
