package Users;

public class Admin implements Identificavel, Autenticavel {
    private String username;
    private String password;
    private boolean autenticado;

    public Admin() {
        this.username = "";
        this.password = "";
        this.autenticado = false;
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.autenticado = false;
    }

    public Admin(String username, String password, boolean autenticado) {
        this.username = username;
        this.password = password;
        this.autenticado = autenticado;
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    @Override
    public boolean estaAutenticado() {
        return this.autenticado;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void logOut() {
        this.autenticado = false;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
