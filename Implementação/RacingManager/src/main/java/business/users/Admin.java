package business.users;

import org.jetbrains.annotations.NotNull;

public class Admin implements Autenticavel {
    /**
     * Username do Admin
     */
    private final String username;
    /**
     * Password para autenticar utilizador
     */
    private String password;
    /**
     * Verifica se utilizador se encontra autenticado
     */
    private boolean autenticado;

    /**
     * Construtor parametrizado
     * @param username username escolhido para Admin
     * @param password password escolhida para Admin
     * @param autenticado verifica se utilizador se encontra autenticado
     */
    public Admin(String username, String password, boolean autenticado) {
        this.username = username;
        this.password = password;
        this.autenticado = autenticado;
    }

    /**
     * Construtor copia do Admin
     * @param a Admin que deseja copiar
     */
    public Admin(@NotNull Admin a) {
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.autenticado = a.estaAutenticado();
    }

    /**
     * Metodo que efetua a autenticacao do utilizador
     * @param username username a verificar
     * @param password password a verificar
     * @return user autenticado ou nao
     */
    @Override
    public boolean login(String username, String password) {
        if(this.username.equals(username) && this.password.equals(password)) {
            this.autenticado = true;
            return true;
        }
        return false;
    }

    /**
     * Metodo que verifica se utilizador esta autenticado
     * @return true se utilizador se encontra autenticado ou false caso contrario
     */
    @Override
    public boolean estaAutenticado() {
        return this.autenticado;
    }

    /**
     * Metodo que altera password do Admin
     * @param password password nova escolhida
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Metodo que coloca admin logged out
     */
    @Override
    public void logOut() {
        this.autenticado = false;
    }

    /**
     * Metodo que copia instancia do Admin
     * @return Admin copiado
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Admin clone() {
        return new Admin(this);
    }

    /**
     * Metodo que retorna username de uma instancia do Admin
     * @return username do utilizador
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Metodo que altera password do Admin
     * @return password do utilizador
     */
	@Override
	public String getPassword() {
    return this.password;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        if (autenticado != admin.autenticado) return false;
        if (!getUsername().equals(admin.getUsername())) return false;
        return getPassword().equals(admin.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (autenticado ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", autenticado=" + autenticado +
                '}';
    }
}
