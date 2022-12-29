package business.campeonatos;

import business.carros.Carro;
import business.carros.ModoMotor;
import business.carros.TipoPneu;
import business.exceptions.CampeonatoJaExisteException;
import business.exceptions.CampeonatoNaoExisteException;
import business.exceptions.CarroNaoAfinavel;
import business.exceptions.CircuitoJaExistenteException;
import business.exceptions.CircuitoNaoExisteException;
import business.exceptions.LobbyAtivoInexistenteException;
import business.exceptions.MaximoAfinacoesExceptions;
import business.exceptions.NaoExistemMaisCorridas;
import business.exceptions.PilotoInexistenteException;
import data.CampeonatoDAO;
import data.CircuitoDAO;
import data.PilotosDAO;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 *
 */
public class CampeonatosFacade implements IGestCampeonatos {

    /**
     *
     */
    private final Map<String, Campeonato> campeonatos;
    private final Map<String, Circuito> circuitos;
    private final Map<String, Piloto> pilotos;
    private Lobby lobbyAtivo;
    private final Map<Integer, Lobby> lobbies;

    public CampeonatosFacade() {
        this.campeonatos = CampeonatoDAO.getInstance();
        this.circuitos = CircuitoDAO.getInstance();
        this.pilotos = PilotosDAO.getInstance();
        this.lobbies = new HashMap<>();
    }

    @Override
    public void addCampeonato(String campeonato, Set<String> circuitos) throws CampeonatoJaExisteException, CircuitoNaoExisteException {
        if(this.campeonatos.containsKey(campeonato)) throw new CampeonatoJaExisteException("Já existe campeonato com o nome de " + campeonato);
        for(var nomeCircuito : circuitos) {
            if(!this.circuitos.containsKey(nomeCircuito)) throw new CircuitoNaoExisteException("Não existe circuito com o nome de " + nomeCircuito);
        }
        var c = new Campeonato(campeonato, circuitos);
        this.campeonatos.put(c.getNomeCampeonato(), c);
    }

    @Override
    public List<Campeonato> getCampeonatos() {
        return this.campeonatos.values().stream().toList();
    }

    @Override
    public Lobby criaLobby(String campeonato, boolean premium) throws CampeonatoNaoExisteException, CircuitoNaoExisteException {
        var camp = this.campeonatos.get(campeonato);
        if(camp == null) throw new CampeonatoNaoExisteException("Não existe campeonato com nome de " + campeonato);
        var circuitos = camp.getCircuitos();
        Set<Circuito> listaCircuitos = new HashSet<>();
        for(var nomeCircuito : circuitos) {
            listaCircuitos.add(this.getCircuito(nomeCircuito));
        }
        var lobby = new Lobby(campeonato, listaCircuitos, premium);
        this.lobbyAtivo = lobby;
        return lobby;
    }

    @Override
    public void inscreveJogador(String username, Carro carro, String piloto) throws PilotoInexistenteException {
        var pil = this.getPiloto(piloto);
        this.lobbyAtivo.inscreveJogador(username, carro, pil);
    }

    @Override
    public void comecaCampeonato() {
        this.lobbyAtivo.fechaLobby();
    }

    @Override
    public String getTabelaClassificativa() throws LobbyAtivoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby no momento");
        return this.lobbyAtivo.printTabelaClassificativa();
    }

    @Override
    public void terminaCampeonato() {
        this.lobbies.put(this.lobbyAtivo.getCodigo(), this.lobbyAtivo);
        this.lobbyAtivo = null;
    }

    @Override
    public String startNextRace() throws LobbyAtivoInexistenteException, NaoExistemMaisCorridas {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe lobby ativo neste momento.");
        if(this.lobbyAtivo.isAberto()) this.lobbyAtivo.fechaLobby();
        return this.lobbyAtivo.startNextRace();
    }

    @Override
    public void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws LobbyAtivoInexistenteException, PilotoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo no momento");
        this.lobbyAtivo.addConfiguracao(nomePiloto, modoMotor, tipoPneu);
    }

    @Override
    public void alteraAfinacao(String nomePiloto, float afinacao) throws LobbyAtivoInexistenteException, PilotoInexistenteException, MaximoAfinacoesExceptions, CarroNaoAfinavel {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo no momento.");
        this.lobbyAtivo.alteraAfinacao(nomePiloto, afinacao);
    }

    @Override
    public List<Lobby> getHistoricoParticipacoes(String username) {
        List<Lobby> lobbies = new ArrayList<>();
        for(var lobby : this.lobbies.values()) {
            if(lobby.existeJogador(username)) lobbies.add(lobby.clone());
        }
        return lobbies;
    }

    @Override
    public void addCircuito(String nomeCircuito, int distancia, List<GDU> chicanes, List<GDU> curvas, List<GDU> retas, int numeroVoltas) throws CircuitoJaExistenteException {
        if(this.circuitos.containsKey(nomeCircuito)) throw new CircuitoJaExistenteException("Já existe um circuito com o nome de " + nomeCircuito);
        var circuito = new Circuito(nomeCircuito, distancia, numeroVoltas, retas, curvas, chicanes);
        this.circuitos.put(nomeCircuito, circuito);
    }

    @Override
    public List<Circuito> getCircuitos() {
        return new ArrayList<>(this.circuitos.values());
    }

    @Override
    public Lobby getLobby() throws LobbyAtivoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo");
        //TODO fazer o clone do lobby ativo
        return this.lobbyAtivo.clone();
    }

    @Override
    public List<Piloto> getPilotos() {
        return new ArrayList<>(this.pilotos.values());
    }

    @Override
    public void addPiloto(String nome, int cts, int sva) throws PilotoInexistenteException {
        if(this.pilotos.containsKey(nome)) throw new PilotoInexistenteException("Não existe piloto com o nome de " + nome);
        this.pilotos.put(nome, new Piloto(nome, cts, sva));
    }

    @Override
    public Campeonato getCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException {
        var campeonato = this.campeonatos.get(nomeCampeonato);
        if(campeonato == null) throw new CampeonatoNaoExisteException("Não existe campeonato com nome de " + nomeCampeonato);
        return campeonato;
    }

    @Override
    public Circuito getCircuito(String nomeCircuito) throws CircuitoNaoExisteException {
        var circuito = this.circuitos.get(nomeCircuito);
        if(circuito == null) throw new CircuitoNaoExisteException("Não existe circuito com nome " + nomeCircuito);
        return circuito;
    }

    @Override
    public boolean existeLobbyAtivo() {
        return this.lobbyAtivo != null;
    }

    @Override
    public Piloto getPiloto(String nomePiloto) throws PilotoInexistenteException {
        var piloto = this.pilotos.get(nomePiloto);
        if(piloto == null) throw new PilotoInexistenteException("Não existe nenhum piloto com o nome de " + nomePiloto);
        return piloto;
    }

    @Override
    public boolean lobbyAberto() {
        return this.lobbyAtivo != null && this.lobbyAtivo.isAberto();
    }

    @Override
    public List<Circuito> getCircuitosCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException, CircuitoNaoExisteException {
        var campeonato = this.getCampeonato(nomeCampeonato);
        var circuitosCampeonato = campeonato.getCircuitos();
        List<Circuito> res = new ArrayList<>(circuitos.size());
        for(var circuitoNome : circuitosCampeonato) {
            res.add(this.getCircuito(circuitoNome));
        }
        return res;
    }

    @Override
    public void loginJogador(String username, String nomePiloto) throws LobbyAtivoInexistenteException, PilotoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby em andamento de momento.");
        this.lobbyAtivo.loginJogador(username, nomePiloto);
    }

    @Override
    public @Nullable Corrida getProxCorrida() throws LobbyAtivoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby em andamento de momento");
        return this.lobbyAtivo.getProxCorrida();
    }

    @Override
    public void autenticaJogadorEmLobby(String username, String nomePiloto) throws LobbyAtivoInexistenteException, PilotoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo de momento");
        this.lobbyAtivo.autenticaJogador(username, nomePiloto);
    }
}
