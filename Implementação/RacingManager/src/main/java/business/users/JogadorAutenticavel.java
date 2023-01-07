package business.users;

/**
 * Classe que representa os jogadores que possuem conta no jogo
 */
public class JogadorAutenticavel implements Autenticavel {
  /**
   * username do jogador
   */
  private final String username;
  /**
   * password do utilizador
   */
  private String password;
  /**
   * pontuacao do rankig global do jogador
   */
  private int pontuacaoGlobal;
  /**
   * variavel que verifica se utilizador é premium ou nao
   */
  private final boolean premium;
  /**
   * Variavel que verifica se jogador está autenticado ou não
   */
  private boolean autenticado;

  /**
   * Contrutor parametrizavel do JogadorAutenticavel
   * @param username username escolhido para jogador
   * @param password password escolhida para jogador
   * @param premium jogador é premium ou não
   */
  public JogadorAutenticavel(String username, String password, boolean premium) {
    this.username = username;
    this.password = password;
    this.pontuacaoGlobal = 0;
    this.premium = premium;
    this.autenticado = false;
  }

  /**
   * Contrutor parametrizado de JogadorAutenticavel
   * @param username username escolhido para jogador
   * @param password password esolhido para jogador
   * @param autenticado jogador autenticado ou não
   * @param pontuacao_global pontuacao global do user no ranking global
   * @param premium jogador é premium ou não
   */
  public JogadorAutenticavel(String username, String password,
                             boolean autenticado, int pontuacao_global,
                             boolean premium
                             ) {
    this.username = username;
    this.password = password;
    this.pontuacaoGlobal = pontuacao_global;
    this.premium = premium;
    this.autenticado = autenticado;
  }

  /**
   * Construtor copia de JogadorAutenticavel
   * @param jogador jogador a copiar
   */
  public JogadorAutenticavel(JogadorAutenticavel jogador) {
    this.username = jogador.getUsername();
    this.password = jogador.getPassword();
    this.autenticado = jogador.estaAutenticado();
    this.premium = jogador.isPremium();
    this.pontuacaoGlobal = jogador.getPontuacaoGlobal();
  }

  /**
   * Metodo que efetua autenticacao do jogador
   * @param username username a verificar
   * @param password password a verificar
   * @return true se autenticacao com suecesso, false caso contrario
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
   * Metodo que verifica se jogador está autenticado
   * @return true se jogador autentciado, false caso contrario
   */
  @Override
  public boolean estaAutenticado() {
    return this.autenticado;
  }

  /**
   * Metodo que altera password do jogador
   * @param password password nova escolhida
   */
  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Metodo que faz o logout do jogador
   */
  @Override
  public void logOut() {
    this.autenticado = false;
  }

  @SuppressWarnings("MethodDoesntCallSuperMethod")
  @Override
  public JogadorAutenticavel clone() {
    return new JogadorAutenticavel(this);
  }

  /**
   * Metodo que retorna o username do jogador
   * @return username do jogador;
   */
  @Override
  public String getUsername() {
    return this.username;
  }

  /**
   * Metodo que retorna password do jogador
   * @return password do jogador
   */
  @Override
  public String getPassword() {
    return this.password;
  }

  /**
   * Metodo que retorna pontuacao global do jogador no ranking global da app
   * @return pontuacao global do jogador no ranking global da app
   */
  public int getPontuacaoGlobal() { return this.pontuacaoGlobal; }

  /**
   * Metodo que retorna se utilizador é premium na ‘app’
   * @return true se utilizador é premium, false caso contrario
   */
  public boolean isPremium() { return this.premium; }

  /**
   * Metodo que altera pontuacao global do jogador no ranking global da app
   * @param pontuacao pontuacao global a adicionar do jogador no ranking global da app
   */
  public void addPontuacao(int pontuacao) {
    this.pontuacaoGlobal += pontuacao;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    JogadorAutenticavel that = (JogadorAutenticavel) o;

    if (getPontuacaoGlobal() != that.getPontuacaoGlobal()) return false;
    if (isPremium() != that.isPremium()) return false;
    if (autenticado != that.autenticado) return false;
    if (!getUsername().equals(that.getUsername())) return false;
    return getPassword().equals(that.getPassword());
  }

  @Override
  public int hashCode() {
    int result = getUsername().hashCode();
    result = 31 * result + getPassword().hashCode();
    result = 31 * result + getPontuacaoGlobal();
    result = 31 * result + (isPremium() ? 1 : 0);
    result = 31 * result + (autenticado ? 1 : 0);
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
            '}';
  }
}
