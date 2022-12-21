package business.campeonatos;

import business.carros.Carro;
import business.carros.ModoMotor;
import business.carros.TipoPneu;
import business.exceptions.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface IGestCampeonatos {
    boolean existeCircuito(String nomeCircuito);
    void addCampeonato(String campeonato, Set<String> circuitos) throws CampeonatoJaExisteException, CircuitoNaoExisteException;
    List<Campeonato> getCampeonatos();
    Lobby criaLobby(String campeonato, boolean premium) throws CampeonatoNaoExisteException, CircuitoNaoExisteException;
    void inscreveJogador(String username, Carro carro, String piloto) throws LobbyAtivoInexistenteException, PilotoInexistenteException;
    void comecaCampeonato() throws LobbyAtivoInexistenteException, LobbyAlreadyStartedException;
    String getTabelaClassificativa();
    void terminaCampeonato();
    String startNextRace() throws LobbyAtivoInexistenteException, NaoExistemMaisCorridas;
    void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws LobbyAtivoInexistenteException, PilotoInexistenteException;
    void alteraAfinacao(String nomePiloto, float afinacao) throws LobbyAtivoInexistenteException, PilotoInexistenteException, MaximoAfinacoesExceptions, CarroNaoAfinavel;
    List<Lobby> getHistoricoParticipacoes(String username);
    void addCircuito(String nomeCircuito, int distancia, List<GDU> chicanes, List<GDU> curvas, List<GDU> retas, int numeroVoltas) throws CircuitoJaExistenteException;
    List<Circuito> getCircuitos();
    Lobby getLobby() throws LobbyAtivoInexistenteException;
    Lobby getLobby(int numLobby);
    List<Piloto> getPilotos();
    void addPiloto(String nome, int cts, int sva) throws PilotoInexistenteException;
    Campeonato getCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException;
    Circuito getCircuito(String nomeCircuito) throws CircuitoNaoExisteException;
    boolean existeLobbyAtivo();
    Piloto getPiloto(String nomePiloto) throws PilotoInexistenteException;
    boolean lobbyAberto();

    public List<Circuito> getCircuitosCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException, CircuitoNaoExisteException;

    void loginJogador(String username, String nomePiloto) throws LobbyAtivoInexistenteException, PilotoInexistenteException;

    @Nullable Corrida getProxCorrida() throws LobbyAtivoInexistenteException;
}
