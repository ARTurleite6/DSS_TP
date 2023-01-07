package business.campeonatos;

import business.carros.Carro;
import business.carros.ModoMotor;
import business.carros.TipoPneu;
import business.exceptions.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface IGestCampeonatos {
    void addCampeonato(String campeonato, Set<String> circuitos) throws CampeonatoJaExisteException, CircuitoNaoExisteException;
    List<Campeonato> getCampeonatos();
    Lobby criaLobby(String campeonato, boolean premium) throws CampeonatoNaoExisteException, CircuitoNaoExisteException;
    void inscreveJogador(String username, Carro carro, String piloto) throws PilotoInexistenteException;
    String getTabelaClassificativa() throws LobbyAtivoInexistenteException;
    void terminaCampeonato();
    String startNextRace() throws LobbyAtivoInexistenteException, NaoExistemMaisCorridas;
    void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws LobbyAtivoInexistenteException, PilotoInexistenteException;
    void alteraAfinacao(String nomePiloto, float afinacao) throws LobbyAtivoInexistenteException, PilotoInexistenteException, MaximoAfinacoesExceptions, CarroNaoAfinavel;

    void addCircuito(String nomeCircuito, int distancia, List<GDU> chicanes, List<GDU> curvas, List<GDU> retas, int numeroVoltas) throws CircuitoJaExistenteException;
    List<Circuito> getCircuitos();
    Lobby getLobby() throws LobbyAtivoInexistenteException;

    List<Piloto> getPilotos();
    void addPiloto(String nome, int cts, int sva) throws PilotoInexistenteException;
    Campeonato getCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException;
    Circuito getCircuito(String nomeCircuito) throws CircuitoNaoExisteException;
    boolean existeLobbyAtivo();
    Piloto getPiloto(String nomePiloto) throws PilotoInexistenteException;
    boolean lobbyAberto();

    List<Circuito> getCircuitosCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException, CircuitoNaoExisteException;

    @Nullable Corrida getProxCorrida() throws LobbyAtivoInexistenteException;

    void autenticaJogadorEmLobby(String username, String nomePiloto) throws LobbyAtivoInexistenteException, PilotoInexistenteException;
}
