package business.users;

import business.exceptions.UtilizadorJaExistenteException;
import business.exceptions.UtilizadorNaoExisteException;

import java.util.Map;

public interface IGestUsers {
    void logOut(String username) throws UtilizadorNaoExisteException;

    void registaJogador(JogadorAutenticavel jogador) throws UtilizadorJaExistenteException;
    Autenticavel autenticaUtilizador(String username, String password) throws UtilizadorNaoExisteException;
    void atualizaPontuacoes(Map<String, Integer> pontuacoesJogador);

    boolean existeJogador(String username);

    JogadorAutenticavel getJogador(String username) throws UtilizadorNaoExisteException;
}
