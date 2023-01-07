package business.campeonatos;

import business.carros.Carro;
import business.carros.Hibrido;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa uma corrida de um campeonato.
 */
@SuppressWarnings("MethodDoesntCallSuperMethod")
public class Corrida {
    /**
     * Nome da corrida.
     */
    private final Map<String, Integer> temposTotais;
    /**
     * Colecao de pilotos que não terminaram a corrida, de nome de piloto para volta em que se despistaram
     */
    private final Map<String, Integer> dnf;
    /**
     * Lista com tempos dos pilotos em cada volta da corrida
     */
    private final List<Map<String, Integer>> temposVolta;
    /**
     * Se vai chuver ou não
     */
    private final boolean chuva;
    /**
     * Circuito onde a corrida se irá realizar
     */
    private final Circuito circuito;
    /**
     * Lista de carros da corrida
     */
    private final List<Carro> carros;

    /**
     * Construtor parametrizado de Corrida.
     * @param circuito Circuito onde a corrida se irá realizar.
     * @param chuva Se vai chover ou não.
     */
    public Corrida(boolean chuva, Circuito circuito) {
        this.temposTotais = new HashMap<>();
        this.dnf = new HashMap<>();
        this.temposVolta = new ArrayList<>();
        this.chuva = chuva;
        this.circuito = circuito;
        this.carros = new ArrayList<>();
    }

    /**
     * Construtor por cópia de Corrida.
     * @param c Corrida a copiar.
     */
    public Corrida(@NotNull Corrida c) {
        this.temposTotais = c.getTempos();
        this.dnf = new HashMap<>(c.dnf);
        this.temposVolta = new ArrayList<>();
        for(var tempo : c.temposVolta) {
            this.temposVolta.add(new HashMap<>(tempo));
        }
        this.chuva = c.estaChover();
        this.circuito = c.getCircuito();
        this.carros = c.getCarros();
    }

    /**
     * Metodo que verifica se vai chover na corrida
     * @return true se vai chover, false caso contrario
     */
    public boolean estaChover() {
        return this.chuva;
    }

    /**
     * Metodo que adiciona corredores à corrida, com o nome de piloto e o carro respetivo a utilizar
     * @param carros carros a utilizar mapeados por nome do piloto
     */
    public void addCorredores(@NotNull Map<String, Carro> carros) {
        for(Map.Entry<String, Carro> carro: carros.entrySet()) {
            var nomePiloto = carro.getKey();
            var car = carro.getValue().clone();
            this.carros.add(car);
            this.temposTotais.put(nomePiloto, 0);
        }
    }

    /**
     * Metodo que retorna os tempos do jogadores na corrida
     * @return Map com os tempos dos jogadores
     */
    public Map<String, Integer> getTempos() {
        return new HashMap<>(this.temposTotais);
    }

    /**
     * Metodo que simula a corrida atual
     * @param premium se o jogador que criou o lobby é premium ou não
     * @return Map com as pontuacoes dos jogadores após realizar simulação da corrida
     */
    public Map<String, Integer> simulaCorrida(boolean premium) {
        int numeroCarros = this.carros.size();
        int numeroVoltas = this.circuito.getNumeroVoltas();
        List<GDU> seccoes = this.circuito.getSeccoes();
        for(int volta = 1; volta <= numeroVoltas; ++volta) {
            for(var carro : this.carros) {
                var acidentou = carro.isDnf();
                if(!acidentou) {
                    boolean is_dnf = carro.dnf(volta, this.chuva);
                    if(is_dnf) {
                        carro.setDnf(true);
                        this.dnf.put(carro.getPiloto().getNome(), volta);
                    }
                }
            }
            for(GDU seccao : seccoes) {
                for(int i = numeroCarros - 1; i >= 0; --i) {
                    var carroAtual = this.carros.get(i);
                    var acidentou = carroAtual.isDnf();
                    carroAtual.setDespiste(false);
                    if(!acidentou) {
                        var despiste = carroAtual.despiste(volta, this.chuva);
                        if(despiste) carroAtual.setDespiste(true);
                        if(i == numeroCarros - 1) {
                            int anterior = carroAtual.getTempo();
                            int tempo = carroAtual.tempoProxSeccao(seccao, this.chuva, volta);
                            carroAtual.setTempo(anterior + tempo);
                        }
                        else {
                            this.lidaUltrapassagens(i, volta, seccao, premium);
                        }
                        //}
                    }
                }
            }
            this.atualizaClassificacoes();
        }
        return this.getPontuacoes();
    }

    /**
     * Metodo que retorna uma string com a representacao dos tempos dos jogadores na corrida
     * @return String com os tempos dos jogadores
     */
    public String printResultadosFinais() {
        StringBuilder s = new StringBuilder(this.printResultados(this.temposTotais));
        for(var entry : this.dnf.entrySet()) {
            s.append("\nPiloto: ").append(entry.getKey()).append(", acidentou-se na volta = ").append(entry.getValue());
        }
        return s.toString();
    }

    /**
     * Metodo que retorna uma string com a representacao dos tempos dos jogadores na corrida
     * @param resultados resultados que se deseja representar como uma string
     * @return String com os tempos dos jogadores
     */
    @Contract(pure = true)
    private @NotNull String printResultados(Map<String, Integer> resultados) {
        StringBuilder s = new StringBuilder("\n------------------Resultados-------------------");
        var list_aux = new ArrayList<>(this.carros);
        Comparator<Carro> comp = (c1, c2) -> {
            int r = Boolean.compare(c1.isDnf(), c2.isDnf());

            if(r == 0) r = resultados.get(c1.getPiloto().getNome()) - resultados.get(c2.getPiloto().getNome());

            return r;
        };
        list_aux.sort(comp);


        int lugar = 1;
        for(var carro : list_aux) {
            var piloto = carro.getPiloto();
            String nomePiloto = piloto.getNome();
            var tempo = resultados.get(nomePiloto);
            s.append("\nPiloto: ").append(nomePiloto).append(", lugar = ").append(lugar).append(", com tempo de ").append(tempo).append(" milisegundos");
            ++lugar;
        }
        s.append("\n----------Carros Acidentados-----------");
        return s.toString();
    }

    /**
     * Metodo que retorna a lista de carros presentes na corrida
     * @return lista de carros presentes na corrida
     */
    public List<Carro> getCarros() {
        return this.carros.stream().map(Carro::clone).collect(Collectors.toList());
    }

    /**
     * Metodo que atualiza os tempos de cada carro ao longo da corrida
     */
    private void atualizaClassificacoes() {
        this.carros.forEach(carro -> {
            int tempo = carro.getTempo();
            String nomePiloto = carro.getPiloto().getNome();
            this.temposTotais.put(nomePiloto, tempo);
        });
        this.temposVolta.add(new HashMap<>(this.temposTotais));
    }

    /**
     * Metodo que retorna o circuito da corrida
     * @return circuito da corrida
     */
    public Circuito getCircuito() {
        return this.circuito;
    }

    /**
     * Metodo que lida com as ultrapassagens entre carros na corrida
     * @param carro carro que se deseja verificar se ultrapassa outro
     * @param volta volta atual da corrida
     * @param seccao seccao atual da corrida
     * @param premium se o jogador que criou o lobby é premium ou não
     */
    private void lidaUltrapassagens(int carro, int volta, GDU seccao, boolean premium) {
        int numeroCarros = this.carros.size();
        var car1 = this.carros.get(carro);
        var piloto = car1.getPiloto().getNome();
        int tempoAnterior = this.temposTotais.get(piloto);
        ++carro;
        int minimo = -1;
        while(carro < numeroCarros - 1 && minimo == -1) {
            Carro carFrente = this.carros.get(carro);
            String pilotoFrente = carFrente.getPiloto().getNome();
            int tempoAnteriorFrente = this.temposTotais.get(pilotoFrente);
            boolean ultrapassa;
            if(premium) {
                ultrapassa = car1.podeUltrapassar(seccao, volta, this.chuva, carFrente, tempoAnteriorFrente);
            } else {
                ultrapassa = car1.podeUltrapassar(seccao, volta, this.chuva, carFrente);
            }
            if(!ultrapassa) {
                minimo = carFrente.getTempo();
            }
            ++carro;
        }

        if(minimo != -1) {
            int delta = car1.tempoProxSeccao(seccao, this.chuva, volta);
            int tempoProx = tempoAnterior + delta;
            if(tempoProx <= minimo)
                tempoProx = minimo + 500;
            car1.setTempo(tempoProx);
        } else {
            int tempoProx = car1.tempoProxSeccao(seccao, this.chuva, volta);
            car1.setTempo(tempoAnterior + tempoProx);
        }

        this.carros.sort((c1, c2) -> c2.getTempo() - c1.getTempo());
    }

    /**
     * Metodo que retorna a pontuacao de cada piloto na corrida
     * @return pontuacao de cada piloto na corrida
     */
    private @NotNull Map<String, Integer> getPontuacoes() {

        Map<Integer, Integer> pontuacaoPosicao = Map.of(
                1, 12,
                2, 10,
                3, 8,
                4, 7,
                5, 6,
                6, 5,
                7, 4,
                8, 3,
                9, 2,
                10, 1
        );

        Map<String, Integer> pontuacoes = new HashMap<>();
        int posicaoHibrido = 1;
        int posicaoCombustao = 1;
        for(var carro : this.carros) {
            if(carro instanceof Hibrido) {
                Integer pontuacao = pontuacaoPosicao.get(posicaoHibrido);
                if(pontuacao == null) pontuacao = 0;
                pontuacoes.put(carro.getPiloto().getNome(), pontuacao);
                ++posicaoHibrido;
            } else {
                Integer pontuacao = pontuacaoPosicao.get(posicaoCombustao);
                if(pontuacao == null) pontuacao = 0;
                pontuacoes.put(carro.getPiloto().getNome(), pontuacao);
                ++posicaoCombustao;
            }
        }
        return pontuacoes;
    }

    public Corrida clone() {
        return new Corrida(this);
    }
}
