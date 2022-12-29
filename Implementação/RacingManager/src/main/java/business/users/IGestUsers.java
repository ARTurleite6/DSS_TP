package business.users;

import business.exceptions.UtilizadorJaExistenteException;
import business.exceptions.UtilizadorNaoExisteException;

import java.util.List;
import java.util.Map;

public interface IGestUsers {
    String getRankingGlobal();
    void atualizaPassword(String username, String password) throws UtilizadorNaoExisteException;
    void logOut(String username) throws UtilizadorNaoExisteException;
    List<String> getTiposConta();
    void registaJogador(JogadorAutenticavel jogador) throws UtilizadorJaExistenteException;
    Autenticavel autenticaUtilizador(String username, String password) throws UtilizadorNaoExisteException;
    void atualizaPontuacoes(Map<String, Integer> pontuacoesJogador);
    boolean existeUtilizador(String username);

    boolean existeJogador(String username);

    JogadorAutenticavel getJogador(String username) throws UtilizadorNaoExisteException;
}
