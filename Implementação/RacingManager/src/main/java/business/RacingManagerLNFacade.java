package business;

import business.campeonatos.*;
import business.carros.Carro;
import business.carros.IGestCarros;
import business.carros.ModoMotor;
import business.carros.TipoPneu;
import business.exceptions.*;
import business.users.Autenticavel;
import business.users.IGestUsers;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RacingManagerLNFacade implements IRacingManagerLNFacade{
    private IGestCarros carrosFacade;
    private IGestCampeonatos campeonatosFacade;
    private IGestUsers usersFacade;

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
    public void inscreveJogador(String username, String modeloCarro, String nomePiloto) throws NaoExisteLobbyAbertoException, UsernameInvalidoException, CarroInexistenteException, PilotoInexistenteException, LobbyAtivoInexistenteException {
        if(!this.campeonatosFacade.lobbyAberto()) throw new NaoExisteLobbyAbertoException("Não existe nenhum lobby aberto no momento");
        if(this.usersFacade.existeJogador(username) && !this.usersFacade.jogadorAutenticado(username)) throw new UsernameInvalidoException("Não é permitido utilizar um username de um jogador registado a não ser que esteja autenticado");

        var carro = this.carrosFacade.getCarro(modeloCarro);
        this.campeonatosFacade.inscreveJogador(username, carro, nomePiloto);
    }

    @Override
    public List<Campeonato> getCampeonatos() {
        return this.campeonatosFacade.getCampeonatos();
    }

    @Override
    public void comecaCampeonato() throws LobbyAtivoInexistenteException, LobbyAlreadyStartedException {
        if(!this.campeonatosFacade.existeLobbyAtivo()) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo neste momento.");
        this.campeonatosFacade.comecaCampeonato();
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
        this.campeonatosFacade.addConfiguracao(nomePiloto, modoMotor, tipoPneu);
    }

    @Override
    public void alteraAfinacao(String nomePiloto, float afinacao) throws CarroNaoAfinavel, PilotoInexistenteException, LobbyAtivoInexistenteException, MaximoAfinacoesExceptions {
        this.campeonatosFacade.alteraAfinacao(nomePiloto, afinacao);
    }

    @Override
    public String getTabelaClassificativa() {
        return null;
    }

    @Override
    public List<Lobby> getHistoricoParticipacoes(String username) {
        return null;
    }

    @Override
    public void addCircuito(String nomeCircuito, float distancia, List<GDU> retas, List<GDU> curvas, List<GDU> chicanes) {

    }

    @Override
    public List<Circuito> getCircuitos() {
        return null;
    }

    @Override
    public List<String> getCategorias() {
        return null;
    }

    @Override
    public void addCarro(Carro carro) {

    }

    @Override
    public List<Carro> getCarros() {
        return null;
    }

    @Override
    public List<String> getModosMotor() {
        return null;
    }

    @Override
    public List<String> getTiposPneus() {
        return null;
    }

    @Override
    public void addPiloto(String piloto, float cts, float sva) {

    }

    @Override
    public List<Piloto> getPilotos() {
        return null;
    }

    @Override
    public String getRankingGlobal() {
        return null;
    }

    @Override
    public void atualizaPassword(String username, String password) {

    }

    @Override
    public void logout(String username) {

    }

    @Override
    public List<String> getTiposConta() {
        return null;
    }

    @Override
    public void registaJogador(String username, String password, boolean premium) {

    }

    @Override
    public Autenticavel autenticaUtilizador(String username, String password) {
        return null;
    }

    @Override
    public boolean existeUsername(String username) {
        return false;
    }

    @Override
    public void atualizaPontuacoes(Map<String, Integer> classificacoesJogador) {
        this.usersFacade.atualizaPontuacoes(classificacoesJogador);
    }
}
