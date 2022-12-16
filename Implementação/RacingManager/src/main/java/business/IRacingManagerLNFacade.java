package business;

import business.campeonatos.*;
import business.carros.Carro;
import business.carros.ModoMotor;
import business.carros.TipoPneu;
import business.exceptions.*;
import business.users.Autenticavel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRacingManagerLNFacade {
    Lobby criaLobby(String nomeCampeonato, String username) throws CampeonatoNaoExisteException, CircuitoNaoExisteException, UtilizadorNaoExisteException, CriarLobbySemAutenticacaoException;
    void addCampeonato(String nomeCampeonato, Set<String> circuitos) throws CampeonatoJaExisteException, CircuitoNaoExisteException;
    void inscreveJogador(String username, String modeloCarro, String nomePiloto) throws NaoExisteLobbyAbertoException, UsernameInvalidoException, CarroInexistenteException, PilotoInexistenteException, LobbyAtivoInexistenteException;
    List<Campeonato> getCampeonatos();
    void comecaCampeonato() throws LobbyAtivoInexistenteException, LobbyAlreadyStartedException;
    String startNextRace() throws LobbyAtivoInexistenteException, NaoExistemMaisCorridas;
    String terminaCampeonato() throws LobbyAtivoInexistenteException, NonLobbyCurrentlyRunningException;
    void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws LobbyAtivoInexistenteException, PilotoInexistenteException;
    void alteraAfinacao(String nomePiloto, float afinacao) throws CarroNaoAfinavel, PilotoInexistenteException, LobbyAtivoInexistenteException, MaximoAfinacoesExceptions;
    String getTabelaClassificativa();
    List<Lobby> getHistoricoParticipacoes(String username);
    void addCircuito(String nomeCircuito, float distancia, List<GDU> retas, List<GDU> curvas, List<GDU> chicanes);
    List<Circuito> getCircuitos();
    List<String> getCategorias();
    void addCarro(Carro carro);
    List<Carro> getCarros();
    List<String> getModosMotor();
    List<String> getTiposPneus();
    void addPiloto(String piloto, float cts, float sva);
    List<Piloto> getPilotos();
    String getRankingGlobal();
    void atualizaPassword(String username, String password);
    void logout(String username);
    List<String> getTiposConta();
    void registaJogador(String username, String password, boolean premium);
    Autenticavel autenticaUtilizador(String username, String password);
    boolean existeUsername(String username);
    void atualizaPontuacoes(Map<String, Integer> classificacoesJogador);
}
