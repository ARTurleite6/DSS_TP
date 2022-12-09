package users;

import java.util.Set;

public class JogadorAutenticavel implements Identificavel, Autenticavel{
    private String username;
    private String password;
    private int pontuacaoGlobal;
    private boolean premium;
    private boolean autenticado;
    private Set<Integer> lobbiesParticipados;

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
