package business;

import business.campeonatos.*;
import business.carros.Carro;
import business.carros.ModoMotor;
import business.carros.TipoPneu;
import business.exceptions.*;
import business.users.Autenticavel;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRacingManagerLNFacade {
    Lobby criaLobby(String nomeCampeonato, String username) throws CampeonatoNaoExisteException, CircuitoNaoExisteException, UtilizadorNaoExisteException, CriarLobbySemAutenticacaoException;
    void addCampeonato(String nomeCampeonato, Set<String> circuitos) throws CampeonatoJaExisteException, CircuitoNaoExisteException;
    void inscreveJogador(String username, String modeloCarro, String nomePiloto) throws NaoExisteLobbyAbertoException, CarroInexistenteException, PilotoInexistenteException, LobbyAtivoInexistenteException;
    List<Campeonato> getCampeonatos();

    String startNextRace() throws LobbyAtivoInexistenteException, NaoExistemMaisCorridas;
    String terminaCampeonato() throws LobbyAtivoInexistenteException, NonLobbyCurrentlyRunningException;
    void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws LobbyAtivoInexistenteException, PilotoInexistenteException;
    void alteraAfinacao(String nomePiloto, float afinacao) throws CarroNaoAfinavel, PilotoInexistenteException, LobbyAtivoInexistenteException, MaximoAfinacoesExceptions;
    String getTabelaClassificativa() throws LobbyAtivoInexistenteException;

    void addCircuito(String nomeCircuito, int distancia, List<GDU> retas, List<GDU> curvas, List<GDU> chicanes, int numeroVoltas) throws CircuitoJaExistenteException;
    List<Circuito> getCircuitos();

    void addCarro(Carro carro) throws CarroJaExisteException;
    List<Carro> getCarros();
    Set<String> getModosMotor();
    Set<String> getTiposPneus();
    void addPiloto(String piloto, int cts, int sva) throws PilotoInexistenteException;
    List<Piloto> getPilotos();

    void logout(String username) throws UtilizadorNaoExisteException;

    void registaJogador(String username, String password, boolean premium) throws UtilizadorJaExistenteException;
    Autenticavel autenticaUtilizador(String username, String password) throws UtilizadorNaoExisteException;

    void autenticaJogadorEmLobby(String username, String password, String nomePiloto) throws UtilizadorNaoExisteException, LobbyAtivoInexistenteException, PilotoInexistenteException;

    void atualizaPontuacoes(Map<String, Integer> classificacoesJogador);

    List<Circuito> getCircuitosCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException, CircuitoNaoExisteException;

    @Nullable Corrida getProxCorrida() throws LobbyAtivoInexistenteException;
}
