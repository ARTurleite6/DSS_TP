package users;

public class Admin implements Autenticavel {
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

    public Admin(Admin a) {
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.autenticado = a.estaAutenticado();
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
    public Admin clone() {
        return new Admin(this);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

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
