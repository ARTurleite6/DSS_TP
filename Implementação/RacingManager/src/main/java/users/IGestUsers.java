package users;

import java.util.List;
import java.util.Map;

public interface IGestUsers {
    String getRankingGlobal();
    boolean atualizaPassword(String username, String password);
    void logOut(String username);
    List<String> getTiposConta();
    void registaJogador(JogadorAutenticavel jogador);
    Autenticavel autenticaUtilizador(String username, String password);
    void atualizaPontuacao(Map<String, Integer> pontuacoesJogador, int lobby);
    boolean existeUtilizador(String username);
    boolean isJogadorPremium(String username);
    boolean existeJogador(String username);
    boolean jogadorAutenticado(String username);
}
