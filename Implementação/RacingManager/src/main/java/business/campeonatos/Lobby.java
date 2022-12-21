package business.campeonatos;

import business.carros.*;
import business.exceptions.CarroNaoAfinavel;
import business.exceptions.MaximoAfinacoesExceptions;
import business.exceptions.NaoExistemMaisCorridas;
import business.exceptions.PilotoInexistenteException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Lobby {
    private int codigo;
    private Map<String, Integer> classificacoes;
    private Map<String, Integer> classificacoesH;
    private int numCorrida;
    private String nomeCampeonato;
    private boolean aberto;
    private boolean premium;
    private Map<String, Integer> numAfinacoes;
    private Map<String, String> jogadores;
    private List<Corrida> corridas;
    private Map<String, Carro> carros;

    public Lobby() {
        this.codigo = 0;
        this.classificacoes = new HashMap<>();
        this.classificacoesH = new HashMap<>();
        this.numCorrida = 0;
        this.nomeCampeonato = "";
        this.aberto = true;
        this.premium = false;
        this.numAfinacoes = new HashMap<>();
        this.jogadores = new HashMap<>();
        this.corridas = new ArrayList<>();
        this.carros = new HashMap<>();
    }

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

    public int getCodigo() {
        return codigo;
    }

    public Map<String, Integer> getClassificacoes() {
        return new HashMap<>(classificacoes);
    }

    public Map<String, Integer> getClassificacoesH() {
        return new HashMap<>(classificacoesH);
    }

    public int getNumCorrida() {
        return numCorrida;
    }

    public String getNomeCampeonato() {
        return nomeCampeonato;
    }

    public boolean isAberto() {
        return aberto;
    }

    public boolean isPremium() {
        return premium;
    }

    public Map<String, Integer> getNumAfinacoes() {
        return new HashMap<>(this.numAfinacoes);
    }

    public Set<String> getJogadores() {
        return new HashSet<>(this.jogadores.keySet());
    }

    public List<Corrida> getCorridas() {
        return new ArrayList<>(corridas);
    }

    public Map<String, Carro> getCarros() {
        return new HashMap<>(carros);
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

    @Override
    public Lobby clone() {
        return new Lobby(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lobby{");
        sb.append("codigo=").append(codigo);
        sb.append(", classificacoes=").append(classificacoes);
        sb.append(", classificacoesH=").append(classificacoesH);
        sb.append(", numCorrida=").append(numCorrida);
        sb.append(", nomeCampeonato='").append(nomeCampeonato).append('\'');
        sb.append(", aberto=").append(aberto);
        sb.append(", premium=").append(premium);
        sb.append(", numAfinacoes=").append(numAfinacoes);
        sb.append(", jogadores=").append(jogadores);
        sb.append(", corridas=").append(corridas);
        sb.append(", carros=").append(carros);
        sb.append('}');
        return sb.toString();
    }

    public void inscreveJogador(String username, Carro carro, Piloto piloto) {
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

    public void fechaLobby() {
        this.aberto = false;
    }

    public String startNextRace() throws NaoExistemMaisCorridas {
        if(this.numCorrida >= this.corridas.size()) throw new NaoExistemMaisCorridas("Não existem mais corridas para jogar no campeonato");
        var corrida = this.corridas.get(this.numCorrida);
        corrida.addCorredores(this.carros);
        var resultados = corrida.simulaCorrida(this.premium);
        this.atualizaClassificacoes(resultados);
        this.numCorrida++;
        return corrida.printResultadosFinais();
    }

    private void atualizaClassificacoes(@NotNull Map<String, Integer> resultados) {
        for(var entry : resultados.entrySet()) {
            if(this.carros.get(entry.getKey()) instanceof Hibrido) {
                this.classificacoesH.put(entry.getKey(), entry.getValue());
            } else {
                this.classificacoes.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public boolean existemMaisCorridas() {
        return this.numCorrida == this.corridas.size();
    }

    public Map<String, Integer> getPontuacoesTotais() {
        return null;
    }

    public String printTabelaClassificativa() {
        return "";
    }

    public String printTabelaClassificativa(int corrida) {
        return "";
    }

    public void addConfiguracao(String nomePiloto, ModoMotor modoMotor, TipoPneu tipoPneu) throws PilotoInexistenteException {
        var carro = this.carros.get(nomePiloto);
        if(carro == null) throw new PilotoInexistenteException("Não existe nenhum piloto com o nome de " + nomePiloto + " no campeonato atual");
        carro.setModoMotor(modoMotor);
        carro.setTipoPneu(tipoPneu);
    }

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

    public boolean existeJogador(String username) {
        for(var jogador : this.jogadores.values()) {
            if(username.equals(jogador)) return true;
        }
        return false;
    }

    public void loginJogador(String username, String nomePiloto) throws PilotoInexistenteException {
        if(!this.jogadores.containsKey(nomePiloto)) throw new PilotoInexistenteException("Não existe nenhum piloto com nome " + nomePiloto + " no lobby atual");
        this.jogadores.put(nomePiloto, username);
    }

    public @Nullable Corrida getProxCorrida() {
        if(this.numCorrida >= this.corridas.size()) return null;
        return this.corridas.get(this.numCorrida).clone();
    }
}
