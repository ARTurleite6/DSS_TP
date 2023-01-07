package business;

import business.campeonatos.*;
import business.carros.*;
import business.exceptions.*;
import business.users.Autenticavel;
import business.users.IGestUsers;
import business.users.JogadorAutenticavel;
import business.users.UsersFacade;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RacingManagerLNFacade implements IRacingManagerLNFacade{
    private final IGestCarros carrosFacade;
    private final IGestCampeonatos campeonatosFacade;
    private final IGestUsers usersFacade;

    public RacingManagerLNFacade() {
        this.carrosFacade = new CarrosFacade();
        this.campeonatosFacade = new CampeonatosFacade();
        this.usersFacade = new UsersFacade();
    }

    @Override
    public Lobby criaLobby(String nomeCampeonato, String username) throws CampeonatoNaoExisteException, CircuitoNaoExisteException, UtilizadorNaoExisteException, CriarLobbySemAutenticacaoException {
        var jogador = this.usersFacade.getJogador(username);
        if(!jogador.estaAutenticado()) throw new CriarLobbySemAutenticacaoException("Tem de estar autenticado para criar lobby");
        var premium = jogador.isPremium();
        return this.campeonatosFacade.criaLobby(nomeCampeonato, premium);
    }

    @Override
    public void addCampeonato(String nomeCampeonato, Set<String> circuitos) throws CampeonatoJaExisteException, CircuitoNaoExisteException {
        this.campeonatosFacade.addCampeonato(nomeCampeonato, circuitos);
    }

    @Override
    public void inscreveJogador(String username, String modeloCarro, String nomePiloto) throws NaoExisteLobbyAbertoException, CarroInexistenteException, PilotoInexistenteException, LobbyAtivoInexistenteException {
        if(!this.campeonatosFacade.lobbyAberto()) throw new NaoExisteLobbyAbertoException("Não existe nenhum lobby aberto no momento");

        var carro = this.carrosFacade.getCarro(modeloCarro);
        this.campeonatosFacade.inscreveJogador(username, carro, nomePiloto);
    }

    @Override
    public List<Campeonato> getCampeonatos() {
        return this.campeonatosFacade.getCampeonatos();
    }

    @Override
    public String startNextRace() throws LobbyAtivoInexistenteException, NaoExistemMaisCorridas {
        if(!this.campeonatosFacade.existeLobbyAtivo()) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo no momento.");
        return this.campeonatosFacade.startNextRace();
    }

    @Override
    public String terminaCampeonato() throws LobbyAtivoInexistenteException, NonLobbyCurrentlyRunningException {
        if(!this.campeonatosFacade.existeLobbyAtivo()) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo no momento.");
        var lobby = this.campeonatosFacade.getLobby();
        if(lobby.isAberto()) throw new NonLobbyCurrentlyRunningException("O lobby ainda não tinha começado.");
        var terminou = lobby.existemMaisCorridas();
        if(terminou) {
           var classificacoes = lobby.getPontuacoesTotais();
           this.atualizaPontuacoes(classificacoes);
        }
        var res = lobby.printTabelaClassificativa();
        this.campeonatosFacade.terminaCampeonato();
        return res;
    }

    @Override
    public void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws LobbyAtivoInexistenteException, PilotoInexistenteException {
        if(!this.campeonatosFacade.existeLobbyAtivo()) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo no momento.");
        this.campeonatosFacade.addConfiguracao(nomePiloto, modoMotor, tipoPneu);
    }

    @Override
    public void alteraAfinacao(String nomePiloto, float afinacao) throws CarroNaoAfinavel, PilotoInexistenteException, LobbyAtivoInexistenteException, MaximoAfinacoesExceptions {
        if(!this.campeonatosFacade.existeLobbyAtivo()) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo no momento.");
        this.campeonatosFacade.alteraAfinacao(nomePiloto, afinacao);
    }

    @Override
    public String getTabelaClassificativa() throws LobbyAtivoInexistenteException {
        return this.campeonatosFacade.getTabelaClassificativa();
    }

    @Override
    public void addCircuito(String nomeCircuito, int distancia, List<GDU> retas, List<GDU> curvas, List<GDU> chicanes, int numeroVoltas) throws CircuitoJaExistenteException {
        this.campeonatosFacade.addCircuito(nomeCircuito, distancia, chicanes, curvas, retas, numeroVoltas);
    }

    @Override
    public List<Circuito> getCircuitos() {
        return this.campeonatosFacade.getCircuitos();
    }

    @Override
    public void addCarro(Carro carro) throws CarroJaExisteException {
        this.carrosFacade.addCarro(carro);
    }

    @Override
    public List<Carro> getCarros() {
        return this.carrosFacade.getCarros();
    }

    @Override
    public Set<String> getModosMotor() {
        return this.carrosFacade.getModosMotor();
    }

    @Override
    public Set<String> getTiposPneus() {
        return this.carrosFacade.getTipoPneus();
    }

    @Override
    public void addPiloto(String piloto, int cts, int sva) throws PilotoInexistenteException {
        this.campeonatosFacade.addPiloto(piloto, cts, sva);
    }

    @Override
    public List<Piloto> getPilotos() {
        return this.campeonatosFacade.getPilotos();
    }

    @Override
    public void logout(String username) throws UtilizadorNaoExisteException {
        this.usersFacade.logOut(username);
    }

    @Override
    public void registaJogador(String username, String password, boolean premium) throws UtilizadorJaExistenteException {
        this.usersFacade.registaJogador(new JogadorAutenticavel(username, password, premium));
    }

    @Override
    public Autenticavel autenticaUtilizador(String username, String password) throws UtilizadorNaoExisteException {
        return this.usersFacade.autenticaUtilizador(username, password);
    }

    @Override
    public @Nullable Corrida getProxCorrida() throws LobbyAtivoInexistenteException {
        return this.campeonatosFacade.getProxCorrida();
    }

    @Override
    public void autenticaJogadorEmLobby(String username, String password, String nomePiloto) throws UtilizadorNaoExisteException, LobbyAtivoInexistenteException, PilotoInexistenteException {
        if(!this.usersFacade.existeJogador(username)) throw new UtilizadorNaoExisteException("Não existe nenhum jogador com username " + username);
        var jogador = this.usersFacade.getJogador(username);
        if(jogador.getPassword().equals(password))
            this.campeonatosFacade.autenticaJogadorEmLobby(username, nomePiloto);
    }

    @Override
    public void atualizaPontuacoes(Map<String, Integer> classificacoesJogador) {
        this.usersFacade.atualizaPontuacoes(classificacoesJogador);
    }

    @Override
    public List<Circuito> getCircuitosCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException, CircuitoNaoExisteException {
        return this.campeonatosFacade.getCircuitosCampeonato(nomeCampeonato);
    }
}
