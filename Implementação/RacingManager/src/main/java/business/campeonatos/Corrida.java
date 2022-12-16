package business.campeonatos;

import business.carros.Carro;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Corrida {
    private Map<String, Integer> temposTotais;
    private Map<String, Integer> dnf;
    private List<Map<String, Integer>> temposVolta;
    private boolean chuva;
    private Circuito circuito;
    private List<Carro> carros;

    public Corrida() {
        this.temposTotais = new HashMap<>();
        this.dnf = new HashMap<>();
        this.temposVolta = new ArrayList<>();
        this.chuva = false;
        this.circuito = new Circuito();
        this.carros = new ArrayList<>();
    }

    public Corrida(boolean chuva, Circuito circuito) {
        this.temposTotais = new HashMap<>();
        this.dnf = new HashMap<>();
        this.temposVolta = new ArrayList<>();
        this.chuva = chuva;
        this.circuito = circuito;
        this.carros = new ArrayList<>();
    }

    public Corrida(Map<String, Integer> temposTotais, Map<String, Integer> dnf, @NotNull List<Map<String, Integer>> tempos_volta, boolean chuva, Circuito circuito, List<Carro> carros) {
        this.temposTotais = new HashMap<>(temposTotais);
        this.dnf = new HashMap<>(dnf);
        this.temposVolta = new ArrayList<>();
        for(var tempo : tempos_volta) {
            this.temposVolta.add(new HashMap<>(tempo));
        }
        this.chuva = chuva;
        this.circuito = circuito;
        this.carros = carros.stream().map(Carro::clone).collect(Collectors.toList());
    }

    public Corrida(@NotNull Corrida c) {
        this.temposTotais = c.getTempos();
        this.dnf = new HashMap<>(c.dnf);
        this.temposVolta = new ArrayList<>();
        var temposPorVolta = c.temposVolta;
        for(var tempoVolta : temposPorVolta) {
            temposPorVolta.add(new HashMap<>(tempoVolta));
        }
        this.chuva = c.chuva;
        this.circuito = c.circuito;
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
        return null;
    }

    public String printResultadosFinais() {
        return null;
    }

    public String printResultados(int volta) {
        return null;
    }

    public List<Carro> getCarros() {
        return this.carros.stream().map(Carro::clone).collect(Collectors.toList());
    }

    public List<Piloto> getPilotos() {
        return this.carros.stream().map(Carro::getPiloto).collect(Collectors.toList());
    }

    private void atualizaClassificacoes() {

    }

    private void lidaUltrapassagens(int carro, int volta, GDU seccao, boolean premium) {
    }

    Map<String, Integer> getPontuacoes() {
        return null;
    }
}
