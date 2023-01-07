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
 * Classe que representa o facado dos campeonatos
 */
public class CampeonatosFacade implements IGestCampeonatos {

    /**
     * Campeonatos disponiveis na app
     */
    private final Map<String, Campeonato> campeonatos;

    /**
     * Circuitos disponiveis na app
     */
    private final Map<String, Circuito> circuitos;
    /**
     * Pilotos disponiveis na app
     */
    private final Map<String, Piloto> pilotos;
    /**
     * Lobby ativo no momento
     */
    private Lobby lobbyAtivo;
    /**
     * Lobbies realizados na app(Ainda nao esta a funcionar mas pronto)
     */
    private final Map<Integer, Lobby> lobbies;

    /**
     * Construtor da classe CampeonatosFacade
     */
    public CampeonatosFacade() {
        this.campeonatos = CampeonatoDAO.getInstance();
        this.circuitos = CircuitoDAO.getInstance();
        this.pilotos = PilotosDAO.getInstance();
        this.lobbies = new HashMap<>();
    }

    /**
     * Metodo que adiciona um campeonato
     * @param campeonato Campeonato a adicionar
     * @param circuitos Circuitos do campeonato
     * @throws CampeonatoJaExisteException Caso o campeonato ja exista
     * @throws CircuitoNaoExisteException Caso o circuito nao exista
     */
    @Override
    public void addCampeonato(String campeonato, Set<String> circuitos) throws CampeonatoJaExisteException, CircuitoNaoExisteException {
        if(this.campeonatos.containsKey(campeonato)) throw new CampeonatoJaExisteException("Já existe campeonato com o nome de " + campeonato);
        for(var nomeCircuito : circuitos) {
            if(!this.circuitos.containsKey(nomeCircuito)) throw new CircuitoNaoExisteException("Não existe circuito com o nome de " + nomeCircuito);
        }
        var c = new Campeonato(campeonato, circuitos);
        this.campeonatos.put(c.getNomeCampeonato(), c);
    }

    /**
     * Metodo que retorna lista com campeonatos dispniveis no sistema
     * @return
     */
    @Override
    public List<Campeonato> getCampeonatos() {
        return this.campeonatos.values().stream().toList();
    }

    /**
     * Metodo que cria um lobby
     * @param campeonato Campeonato do lobby
     * @param premium Se o lobby é premium ou nao
     * @return Lobby criado
     * @throws CampeonatoNaoExisteException Caso o campeonato nao exista
     * @throws CircuitoNaoExisteException Caso o circuito nao exista
     */
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
        return lobby.clone();
    }

    /**
     * Metodo que inscreve um user no lobby ativo de momento
     * @param username username do jogador
     * @param carro carro do jogador a utilizar na corrida
     * @param piloto piloto do jogador a utilizar na corrida
     * @throws PilotoInexistenteException Caso o piloto nao exista
     */
    @Override
    public void inscreveJogador(String username, Carro carro, String piloto) throws PilotoInexistenteException {
        var pil = this.getPiloto(piloto);
        this.lobbyAtivo.inscreveJogador(username, carro, pil);
    }

    /**
     * Metodo que string que representa tabela classificativa do lobby
     * @return tabela classificativa do campeonato ativo de momento
     * @throws LobbyAtivoInexistenteException caso não exista nenhum campeonato ativo no momento
     */
    @Override
    public String getTabelaClassificativa() throws LobbyAtivoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby no momento");
        return this.lobbyAtivo.printTabelaClassificativa();
    }

    /**
     * Metodo que termina o lobby ativo no momento
     */
    @Override
    public void terminaCampeonato() {
        this.lobbies.put(this.lobbyAtivo.getCodigo(), this.lobbyAtivo);
        this.lobbyAtivo = null;
    }

    /**
     * Metodo que comeca proxima corrida do lobby atual
     * @return ‘String’ com a representacão das posicões de cada jogador
     * @throws LobbyAtivoInexistenteException
     * @throws NaoExistemMaisCorridas
     */
    @Override
    public String startNextRace() throws LobbyAtivoInexistenteException, NaoExistemMaisCorridas {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe lobby ativo neste momento.");
        if(this.lobbyAtivo.isAberto()) this.lobbyAtivo.fechaLobby();
        return this.lobbyAtivo.startNextRace();
    }

    /**
     * Metodo que adiciona configuracao do jogador para o lobby e proximas corridas
     * @param nomePiloto nome do piloto do jogador a alterar
     * @param modoMotor modo do motor do jogador a usar
     * @param tipoPneu tipo de pneu do jogador a usar
     * @throws PilotoInexistenteException Caso o piloto nao exista
     */
    @Override
    public void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws PilotoInexistenteException {
        this.lobbyAtivo.addConfiguracao(nomePiloto, modoMotor, tipoPneu);
    }

    /**
     * Metodo que altera afinacao do carro de um determinado piloto
     * @param nomePiloto nome do piloto do carro escolhido no campeonato
     * @param afinacao novo valor para afinacao do carro
     * @throws PilotoInexistenteException caso nao exista nenhum piloto com o nome passado
     * @throws MaximoAfinacoesExceptions caso tenha excedido o numero de afinacoes que poderá realizar
     * @throws CarroNaoAfinavel Caso o piloto tenha um carro que não seja afinavel(C1/C1H ou C2/C2H)
     */
    @Override
    public void alteraAfinacao(String nomePiloto, float afinacao) throws PilotoInexistenteException, MaximoAfinacoesExceptions, CarroNaoAfinavel {
        this.lobbyAtivo.alteraAfinacao(nomePiloto, afinacao);
    }

    /**
     * Metodo que adiciona um novo circuito ao sistema
     * @param nomeCircuito nome do circuito a adicionar ao sistema
     * @param distancia distancia do circuito
     * @param chicanes lista de chicanes do circuito
     * @param curvas lista de curvas do circuito
     * @param retas lista de retas do circuito
     * @param numeroVoltas numero de voltas do circuito
     * @throws CircuitoJaExistenteException caso já exista um circuito com o nome passado
     */
    @Override
    public void addCircuito(String nomeCircuito, int distancia, List<GDU> chicanes, List<GDU> curvas, List<GDU> retas, int numeroVoltas) throws CircuitoJaExistenteException {
        if(this.circuitos.containsKey(nomeCircuito)) throw new CircuitoJaExistenteException("Já existe um circuito com o nome de " + nomeCircuito);
        var circuito = new Circuito(nomeCircuito, distancia, numeroVoltas, retas, curvas, chicanes);
        this.circuitos.put(nomeCircuito, circuito);
    }

    /**
     * Metodo que retorna os circuitos registados no sistema
     * @return lista de circuitos registados no sistema
     */
    @Override
    public List<Circuito> getCircuitos() {
        return new ArrayList<>(this.circuitos.values());
    }

    /**
     * Metodo que retorna o lobby ativo de momento
     * @return lobby ativo de momento
     * @throws LobbyAtivoInexistenteException caso não exista nenhum lobby ativo no momento
     */
    @Override
    public Lobby getLobby() throws LobbyAtivoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo");
        //TODO fazer o clone do lobby ativo
        return this.lobbyAtivo.clone();
    }

    /**
     * Metodo que retorna lista de pilotos no sistema
     * @return Lista de pilotos no sistema
     */
    @Override
    public List<Piloto> getPilotos() {
        return new ArrayList<>(this.pilotos.values());
    }

    /**
     * Metodo que adiciona um novo piloto ao sistema
     * @param nome nome do piloto a adicionar
     * @param cts cts do piloto a adicionar
     * @param sva sva do piloto a adicionar
     * @throws PilotoInexistenteException caso já exista um piloto com o nome passado
     */
    @Override
    public void addPiloto(String nome, int cts, int sva) throws PilotoInexistenteException {
        if(this.pilotos.containsKey(nome)) throw new PilotoInexistenteException("Não existe piloto com o nome de " + nome);
        this.pilotos.put(nome, new Piloto(nome, cts, sva));
    }

    /**
     * Metodo que retorna o campeonato ativo de momento
     * @param nomeCampeonato nome do campeonato a retornar
     * @return campeonato com o nome passado como argumento
     * @throws CampeonatoNaoExisteException caso não exista nenhum campeonato com o nome passado
     */
    @Override
    public Campeonato getCampeonato(String nomeCampeonato) throws CampeonatoNaoExisteException {
        var campeonato = this.campeonatos.get(nomeCampeonato);
        if(campeonato == null) throw new CampeonatoNaoExisteException("Não existe campeonato com nome de " + nomeCampeonato);
        return campeonato;
    }

    /**
     * Metodo que retorna um circuito com um determinado nome
     * @param nomeCircuito nome do circuito a retornar
     * @return circuito com o nome passado como argumento
     * @throws CircuitoNaoExisteException caso não exista nenhum circuito com o nome passado
     */
    @Override
    public Circuito getCircuito(String nomeCircuito) throws CircuitoNaoExisteException {
        var circuito = this.circuitos.get(nomeCircuito);
        if(circuito == null) throw new CircuitoNaoExisteException("Não existe circuito com nome " + nomeCircuito);
        return circuito;
    }

    /**
     * Metodo que verifica se existe algum lobby ativo no momento
     * @return true se existir um lobby ativo, false caso contrario
     */
    @Override
    public boolean existeLobbyAtivo() {
        return this.lobbyAtivo != null;
    }

    /**
     * Metodo que retorna o piloto com o nome passado como argumento
     * @param nomePiloto nome do piloto a retornar
     * @return piloto com o nome passado como argumento
     * @throws PilotoInexistenteException caso não exista nenhum piloto com o nome passado
     */
    @Override
    public Piloto getPiloto(String nomePiloto) throws PilotoInexistenteException {
        var piloto = this.pilotos.get(nomePiloto);
        if(piloto == null) throw new PilotoInexistenteException("Não existe nenhum piloto com o nome de " + nomePiloto);
        return piloto;
    }

    /**
     * Metodo que verifica se existe algum lobby ativo no sistema e se ele ainda se encontra aberto
     * @return true se existir um lobby ativo e este se encontrar aberto, false caso contrario
     */
    @Override
    public boolean lobbyAberto() {
        return this.lobbyAtivo != null && this.lobbyAtivo.isAberto();
    }

    /**
     * Metodo que retorna a lista de circuitos de um determinado campeonato
     * @param nomeCampeonato nome do campeonato a retornar a lista de circuitos
     * @return lista de circuitos do campeonato com o nome passado como argumento
     * @throws CampeonatoNaoExisteException caso não exista nenhum campeonato com o nome passado
     * @throws CircuitoNaoExisteException caso algum dos circuitos do campeonato não exista
     */
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

    /**
     * Metodo que retorna a proxima corrida do campeonato atual
     * @return proxima corrida do campeonato atual
     * @throws LobbyAtivoInexistenteException caso não exista nenhum lobby ativo no momento
     */
    @Override
    public @Nullable Corrida getProxCorrida() throws LobbyAtivoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby em andamento de momento");
        return this.lobbyAtivo.getProxCorrida();
    }

    /**
     * Metodo que autentica utilizador no lobby, substituindo o seu username pelo username registado no sistema
     * @param username username do utilizador a autenticar
     * @param nomePiloto nome do piloto do utilizador que deseja autenticar-se
     * @throws LobbyAtivoInexistenteException caso não exista nenhum lobby ativo no momento
     * @throws PilotoInexistenteException caso não exista nenhum piloto com o nome passado no lobby ativo
     */
    @Override
    public void autenticaJogadorEmLobby(String username, String nomePiloto) throws LobbyAtivoInexistenteException, PilotoInexistenteException {
        if(this.lobbyAtivo == null) throw new LobbyAtivoInexistenteException("Não existe nenhum lobby ativo de momento");
        this.lobbyAtivo.autenticaJogador(username, nomePiloto);
    }
}
