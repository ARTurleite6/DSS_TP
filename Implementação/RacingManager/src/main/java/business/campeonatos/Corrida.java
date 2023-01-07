package business.campeonatos;

import business.carros.Carro;
import business.carros.Hibrido;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("MethodDoesntCallSuperMethod")
public class Corrida {
    private final Map<String, Integer> temposTotais;
    private final Map<String, Integer> dnf;
    private final List<Map<String, Integer>> temposVolta;
    private final boolean chuva;
    private final Circuito circuito;
    private final List<Carro> carros;

    public Corrida(boolean chuva, Circuito circuito) {
        this.temposTotais = new HashMap<>();
        this.dnf = new HashMap<>();
        this.temposVolta = new ArrayList<>();
        this.chuva = chuva;
        this.circuito = circuito;
        this.carros = new ArrayList<>();
    }

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

    public boolean estaChover() {
        return this.chuva;
    }

    public void addCorredores(@NotNull Map<String, Carro> carros) {
        for(Map.Entry<String, Carro> carro: carros.entrySet()) {
            var nomePiloto = carro.getKey();
            var car = carro.getValue().clone();
            this.carros.add(car);
            this.temposTotais.put(nomePiloto, 0);
        }
    }

    public Map<String, Integer> getTempos() {
        return new HashMap<>(this.temposTotais);
    }

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

    public String printResultadosFinais() {
        StringBuilder s = new StringBuilder(this.printResultados(this.temposTotais));
        for(var entry : this.dnf.entrySet()) {
            s.append("\nPiloto: ").append(entry.getKey()).append(", acidentou-se na volta = ").append(entry.getValue());
        }
        return s.toString();
    }

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

    public List<Carro> getCarros() {
        return this.carros.stream().map(Carro::clone).collect(Collectors.toList());
    }

    private void atualizaClassificacoes() {
        this.carros.forEach(carro -> {
            int tempo = carro.getTempo();
            String nomePiloto = carro.getPiloto().getNome();
            this.temposTotais.put(nomePiloto, tempo);
        });
        this.temposVolta.add(new HashMap<>(this.temposTotais));
    }

    public Circuito getCircuito() {
        return this.circuito;
    }

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
