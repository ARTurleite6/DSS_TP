package users;

import java.util.HashSet;
import java.util.Set;

public class JogadorAutenticavel implements Autenticavel {
  private String username;
  private String password;
  private int pontuacaoGlobal;
  private boolean premium;
  private boolean autenticado;
  private Set<Integer> lobbiesParticipados;

  public JogadorAutenticavel(String username, String password,
                             boolean autenticado, int pontuacao_global,
                             boolean premium,
                             Set<Integer> lobbiesParticipados) {
    this.username = username;
    this.password = password;
    this.pontuacaoGlobal = pontuacao_global;
    this.premium = premium;
    this.autenticado = autenticado;
    this.lobbiesParticipados = new HashSet<>(lobbiesParticipados);
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

  @Override
  public String getPassword() {
    return this.password;
  }

  public int getPontuacaoGlobal() { return this.pontuacaoGlobal; }

  public boolean isPremium() { return this.premium; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    JogadorAutenticavel that = (JogadorAutenticavel) o;

    if (getPontuacaoGlobal() != that.getPontuacaoGlobal()) return false;
    if (isPremium() != that.isPremium()) return false;
    if (autenticado != that.autenticado) return false;
    if (!getUsername().equals(that.getUsername())) return false;
    if (!getPassword().equals(that.getPassword())) return false;
    return lobbiesParticipados.equals(that.lobbiesParticipados);
  }

  @Override
  public int hashCode() {
    int result = getUsername().hashCode();
    result = 31 * result + getPassword().hashCode();
    result = 31 * result + getPontuacaoGlobal();
    result = 31 * result + (isPremium() ? 1 : 0);
    result = 31 * result + (autenticado ? 1 : 0);
    result = 31 * result + lobbiesParticipados.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "JogadorAutenticavel{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", pontuacaoGlobal=" + pontuacaoGlobal +
            ", premium=" + premium +
            ", autenticado=" + autenticado +
            ", lobbiesParticipados=" + lobbiesParticipados +
            '}';
  }
}
