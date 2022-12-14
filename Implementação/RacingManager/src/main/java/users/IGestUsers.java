package users;

import exceptions.UtilizadorJaExistenteException;
import exceptions.UtilizadorNaoExisteException;

import java.util.List;
import java.util.Map;

public interface IGestUsers {
    String getRankingGlobal();
    void atualizaPassword(String username, String password) throws UtilizadorNaoExisteException;
    void logOut(String username) throws UtilizadorNaoExisteException;
    List<String> getTiposConta();
    void registaJogador(JogadorAutenticavel jogador) throws UtilizadorJaExistenteException;
    Autenticavel autenticaUtilizador(String username, String password) throws UtilizadorNaoExisteException;
    void atualizaPontuacao(Map<String, Integer> pontuacoesJogador, int lobby);
    boolean existeUtilizador(String username);
    boolean isJogadorPremium(String username);
    boolean existeJogador(String username);
    boolean jogadorAutenticado(String username);
}
