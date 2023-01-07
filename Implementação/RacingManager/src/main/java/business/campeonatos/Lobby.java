package business.campeonatos;

import business.carros.*;
import business.exceptions.CarroNaoAfinavel;
import business.exceptions.MaximoAfinacoesExceptions;
import business.exceptions.NaoExistemMaisCorridas;
import business.exceptions.PilotoInexistenteException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa um lobby de corridas.
 */
public class Lobby {
    /**
     * Codigo do lobby
     */
    private final int codigo;
    /**
     * Classificacoes dos pilotos com carros de combustão do lobby
     */
    private final Map<String, Integer> classificacoes;
    /**
     * Classificacoes dos pilotos com carros híbridos do lobby
     */
    private final Map<String, Integer> classificacoesH;
    /**
     * Numero da corrida atual do lobby
     */
    private int numCorrida;
    /**
     * Nome do Campeonato onde o lobby se irá realizar
     */
    private final String nomeCampeonato;
    /**
     * Lobby aberto ou não a inscrições
     */
    private boolean aberto;
    /**
     * Jogador que cria lobby é premium ou não
     */
    private final boolean premium;
    /**
     * Afinacoes dos pilotos no lobby
     */
    private final Map<String, Integer> numAfinacoes;
    /**
     * Pilotos inscritos no lobby, juntamente com o seu username
     */
    private final Map<String, String> jogadores;
    /**
     * Lista de Corridas do lobby
     */
    private final List<Corrida> corridas;
    /**
     * Colecao de carros dos pilotos
     */
    private final Map<String, Carro> carros;

    /**
     * Construtor de um lobby
     * @param nomeCampeonato Nome do campeonato onde o lobby se irá realizar
     * @param circuitos circuitos do campeonato onde o lobby se irá realizar
     * @param premium Jogador que cria lobby é premium ou não (true se for premium)
     */
    public Lobby(String nomeCampeonato, @NotNull Set<Circuito> circuitos, boolean premium) {
        this.codigo = 0;
        this.classificacoes = new HashMap<>();
        this.classificacoesH = new HashMap<>();
        this.numCorrida = 0;
        this.nomeCampeonato = nomeCampeonato;
        this.aberto = true;
        this.premium = premium;
        this.numAfinacoes = new HashMap<>();
        this.jogadores = new HashMap<>();
        var rand = new Random();
        this.corridas = circuitos.stream().map(circuito -> new Corrida(rand.nextBoolean(), circuito)).toList();
        this.carros = new HashMap<>();
    }

    /**
     * Construtor de cópia de um lobby
     * @param l lobby a copiar
     */
    public Lobby(@NotNull Lobby l) {
        this.codigo = l.getCodigo();
        this.classificacoes = l.getClassificacoes();
        this.classificacoesH = l.getClassificacoesH();
        this.numCorrida = l.getNumCorrida();
        this.nomeCampeonato = l.getNomeCampeonato();
        this.aberto = l.isAberto();
        this.premium = l.isPremium();
        this.numAfinacoes = l.getNumAfinacoes();
        this.jogadores = new HashMap<>(l.jogadores);
        this.corridas = l.getCorridas();
        this.carros = l.getCarros();
    }

    /**
     * Devolve o codigo do lobby
     * @return codigo do lobby
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devolve as classificações dos pilotos com carros de combustão do lobby
     * @return classificações dos pilotos com carros de combustão do lobby
     */
    public Map<String, Integer> getClassificacoes() {
        return new HashMap<>(classificacoes);
    }

    /**
     * Devolve as classificações dos pilotos com carros híbridos do lobby
     * @return classificações dos pilotos com carros híbridos do lobby
     */
    public Map<String, Integer> getClassificacoesH() {
        return new HashMap<>(classificacoesH);
    }

    /**
     * Devolve o numero da corrida atual do lobby
     * @return numero da corrida atual do lobby
     */
    public int getNumCorrida() {
        return numCorrida;
    }

    /**
     * Devolve o nome do campeonato onde o lobby se irá realizar
     * @return nome do campeonato onde o lobby se irá realizar
     */
    public String getNomeCampeonato() {
        return nomeCampeonato;
    }

    /**
     * Devolve se o lobby está aberto ou não a inscrições
     * @return true se o lobby está aberto a inscrições, false caso contrário
     */
    public boolean isAberto() {
        return aberto;
    }

    /**
     * Devolve se o jogador que criou o lobby é premium ou não
     * @return true se o jogador que criou o lobby é premium, false caso contrário
     */
    public boolean isPremium() {
        return premium;
    }

    /**
     * Devolve as afinacoes dos pilotos no lobby
     * @return afinacoes dos pilotos no lobby
     */
    public Map<String, Integer> getNumAfinacoes() {
        return new HashMap<>(this.numAfinacoes);
    }

    /**
     * Devolve a lista de corridas do lobby
     * @return jogadores inscritos no lobby, com o seu username
     */
    public List<Corrida> getCorridas() {
        return this.corridas.stream().map(Corrida::clone).toList();
    }

    /**
     * Metodo que retorna a colecao de carros dos pilotos
     * @return colecao de carros dos pilotos
     */
    public Map<String, Carro> getCarros() {
        return this.carros.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lobby lobby = (Lobby) o;

        if (codigo != lobby.codigo) return false;
        if (numCorrida != lobby.numCorrida) return false;
        if (aberto != lobby.aberto) return false;
        if (premium != lobby.premium) return false;
        if (!classificacoes.equals(lobby.classificacoes)) return false;
        if (!classificacoesH.equals(lobby.classificacoesH)) return false;
        if (!nomeCampeonato.equals(lobby.nomeCampeonato)) return false;
        if (!numAfinacoes.equals(lobby.numAfinacoes)) return false;
        if (!jogadores.equals(lobby.jogadores)) return false;
        if (!corridas.equals(lobby.corridas)) return false;
        return carros.equals(lobby.carros);
    }

    @Override
    public int hashCode() {
        int result = codigo;
        result = 31 * result + classificacoes.hashCode();
        result = 31 * result + classificacoesH.hashCode();
        result = 31 * result + numCorrida;
        result = 31 * result + nomeCampeonato.hashCode();
        result = 31 * result + (aberto ? 1 : 0);
        result = 31 * result + (premium ? 1 : 0);
        result = 31 * result + numAfinacoes.hashCode();
        result = 31 * result + jogadores.hashCode();
        result = 31 * result + corridas.hashCode();
        result = 31 * result + carros.hashCode();
        return result;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Lobby clone() {
        return new Lobby(this);
    }

    @Override
    public String toString() {
        return "Lobby{" + "codigo=" + codigo +
                ", classificacoes=" + classificacoes +
                ", classificacoesH=" + classificacoesH +
                ", numCorrida=" + numCorrida +
                ", nomeCampeonato='" + nomeCampeonato + '\'' +
                ", aberto=" + aberto +
                ", premium=" + premium +
                ", numAfinacoes=" + numAfinacoes +
                ", jogadores=" + jogadores +
                ", corridas=" + corridas +
                ", carros=" + carros +
                '}';
    }

    public void inscreveJogador(String username, @NotNull Carro carro, @NotNull Piloto piloto) {
        var nomePiloto = piloto.getNome();
        this.jogadores.put(nomePiloto, username);
        this.numAfinacoes.put(nomePiloto, 0);
        carro.setPiloto(piloto);
        this.carros.put(nomePiloto, carro.clone());
        if(carro instanceof Hibrido) {
            this.classificacoesH.put(nomePiloto, 0);
        } else {
            this.classificacoes.put(nomePiloto, 0);
        }
    }

    /**
     * Metodo que fecha o lobby, não permitindo mais inscricões
     */
    public void fechaLobby() {
        this.aberto = false;
    }

    /**
     * Metodo que simula próxima corrida
     * @return Classificacões dos pilotos na corrida
     * @throws NaoExistemMaisCorridas caso não existam mais corridas
     */
    public String startNextRace() throws NaoExistemMaisCorridas {
        if(this.numCorrida >= this.corridas.size()) throw new NaoExistemMaisCorridas("Não existem mais corridas para jogar no campeonato");
        var corrida = this.corridas.get(this.numCorrida);
        corrida.addCorredores(this.carros);
        var resultados = corrida.simulaCorrida(this.premium);
        this.atualizaClassificacoes(resultados);
        this.numCorrida++;
        return corrida.printResultadosFinais();
    }

    /**
     * Metodo que atualiza as classificações dos pilotos
     * @param resultados resultados da corrida
     */
    private void atualizaClassificacoes(@NotNull Map<String, Integer> resultados) {
        for(var entry : resultados.entrySet()) {
            if(this.carros.get(entry.getKey()) instanceof Hibrido) {
                this.classificacoesH.put(entry.getKey(), entry.getValue());
            } else {
                this.classificacoes.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Metodo que verifica se existem mais corridas para se jogar
     * @return true se existirem mais corridas, false caso contrário
     */
    public boolean existemMaisCorridas() {
        return this.numCorrida == this.corridas.size();
    }

    /**
     * Metodo que retorna pontuacoes totais dos jogadores no lobby
     * @return pontuacoes totais dos jogadores no lobby
     */
    public Map<String, Integer> getPontuacoesTotais() {
        Map<String, Integer> classificacoes = new HashMap<>();

        var classH = this.classificacoesH.entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(this.jogadores.get(entry.getKey()), entry.getValue()))
                .toList();

        var classNH = this.classificacoes.entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(this.jogadores.get(entry.getKey()), entry.getValue()))
                .toList();

        classH.forEach(e -> classificacoes.put(e.getKey(), e.getValue()));
        classNH.forEach(e -> classificacoes.put(e.getKey(), e.getValue()));

        return classificacoes;
    }

    public String printTabelaClassificativa() {
        StringBuilder s = new StringBuilder("\n--------------------Classificacoes do momento-----------------------");
        s.append("\n--------------------Classificacoes de Não Hibridos-----------------------");
        var pontuacoes = this.classificacoes.entrySet()
                .stream()
                .sorted((c1, c2) -> c2.getValue() - c1.getValue())
                .map(c1 -> new AbstractMap.SimpleEntry<>(this.jogadores.get(c1.getKey()), c1.getValue()))
                .toList();
        int i = 1;
        for(var jogadorPontuacao : pontuacoes) {
            s.append("\n")
                    .append(i)
                    .append(": ")
                    .append(jogadorPontuacao.getKey())
                    .append(": ")
                    .append(jogadorPontuacao.getValue())
                    .append("pontos");
            ++i;
        }
        s.append("\n--------------------Classificacoes de Hibridos-----------------------");
        var pontuacoesH = this.classificacoesH.entrySet()
                .stream()
                .sorted((c1, c2) -> c2.getValue() - c1.getValue())
                .map(c1 -> new AbstractMap.SimpleEntry<>(this.jogadores.get(c1.getKey()), c1.getValue()))
                .toList();
        i = 1;
        for(var jogadorPontuacao : pontuacoesH) {
            s.append("\n")
                    .append(i)
                    .append(": ")
                    .append(jogadorPontuacao.getKey())
                    .append(": ")
                    .append(jogadorPontuacao.getValue())
                    .append(" pontos");
            ++i;
        }
        return s.toString();
    }

    /**
     * Metodo que adiciona uma configuração de corrida ao campeonato para um piloto
     * @param nomePiloto nome do piloto
     * @param modoMotor modo do motor
     * @param tipoPneu tipo de pneu
     * @throws PilotoInexistenteException caso o piloto não exista
     */
    public void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws PilotoInexistenteException {
        var carro = this.carros.get(nomePiloto);
        if(carro == null) throw new PilotoInexistenteException("Não existe nenhum piloto com o nome de " + nomePiloto + " no campeonato atual");
        carro.setModoMotor(modoMotor);
        carro.setTipoPneu(tipoPneu);
    }

    /**
     * Metodo que altera a afinação do carro do piloto
     * @param nomePiloto nome do piloto
     * @param afinacao afinacao do carro
     * @throws PilotoInexistenteException caso o piloto não exista
     * @throws MaximoAfinacoesExceptions caso o piloto já tenha atingido o máximo de afinacoes
     * @throws CarroNaoAfinavel caso o carro não seja afinavel
     */
    public void alteraAfinacao(String nomePiloto, float afinacao) throws PilotoInexistenteException, MaximoAfinacoesExceptions, CarroNaoAfinavel {
        var carro = this.carros.get(nomePiloto);
        if(carro == null) throw new PilotoInexistenteException("Não existe nenhum piloto com o nome de " + nomePiloto + " no campeonato atual");
        var num_afinacoes = this.numAfinacoes.get(nomePiloto);
        var totalAfinacoes = ((this.corridas.size() * 2) / 3);
        if(num_afinacoes == totalAfinacoes) throw new MaximoAfinacoesExceptions("O piloto com nome " + nomePiloto + " já atingiu o numero maximo de afinacoes");
        if(!(carro instanceof Afinavel afinavel)) throw new CarroNaoAfinavel("O piloto de nome " + nomePiloto + " não possui um carro afinavel");
        afinavel.setAfinacao(afinacao);
        this.numAfinacoes.put(nomePiloto, num_afinacoes + 1);
    }

    /**
     * Metodo que retorna a próxima corrida do campeonato
     * @return a próxima corrida do campeonato
     */
    public @Nullable Corrida getProxCorrida() {
        if(this.numCorrida >= this.corridas.size()) return null;
        return this.corridas.get(this.numCorrida).clone();
    }

    /**
     * Metodo que autentica jogador no lobby atual
     * @param username username do jogador
     * @param nomePiloto nome do piloto
     * @throws PilotoInexistenteException caso o piloto não exista
     */
    public void autenticaJogador(String username, String nomePiloto) throws PilotoInexistenteException {
        if(!this.jogadores.containsKey(nomePiloto)) throw new PilotoInexistenteException("Não existe nenhum jogador com o piloto " + nomePiloto);
        this.jogadores.put(nomePiloto, username);
    }
}
