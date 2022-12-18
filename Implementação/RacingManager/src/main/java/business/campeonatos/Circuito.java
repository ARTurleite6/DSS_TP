package business.campeonatos;

import java.util.ArrayList;
import java.util.List;

public class Circuito {
    private String nomeCircuito;
    private int distancia;
    private int numeroVoltas;

    private List<GDU> retas;
    private List<GDU> curvas;
    private List<GDU> chicanes;

    public Circuito() {
        this.nomeCircuito = "";
        this.distancia = 0;
        this.numeroVoltas = 0;
        this.retas = new ArrayList<>();
        this.curvas = new ArrayList<>();
        this.chicanes = new ArrayList<>();
    }

    public Circuito(String nomeCircuito, int distancia, int numeroVoltas, List<GDU> retas, List<GDU> curvas, List<GDU> chicanes) {
        this.nomeCircuito = nomeCircuito;
        this.distancia = distancia;
        this.numeroVoltas = numeroVoltas;
        this.retas = new ArrayList<>(retas);
        this.curvas = new ArrayList<>(curvas);
        this.chicanes = new ArrayList<>(chicanes);
    }

    public Circuito(Circuito c) {
        this.nomeCircuito = c.getNomeCircuito();
        this.distancia = c.getDistancia();
        this.numeroVoltas = c.getNumeroVoltas();
        this.retas = c.getRetas();
        this.curvas = c.getCurvas();
        this.chicanes = c.getChicanes();
    }

    public String getNomeCircuito() {
        return nomeCircuito;
    }

    public int getDistancia() {
        return distancia;
    }

    public int getNumeroVoltas() {
        return numeroVoltas;
    }

    public List<GDU> getRetas() {
        return new ArrayList<>(this.retas);
    }

    public List<GDU> getChicanes() {
        return new ArrayList<>(this.chicanes);
    }

    public List<GDU> getCurvas() {
        return new ArrayList<>(this.curvas);
    }

    public Circuito clone() {
        return new Circuito(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Circuito circuito = (Circuito) o;

        if (getDistancia() != circuito.getDistancia()) return false;
        if (getNumeroVoltas() != circuito.getNumeroVoltas()) return false;
        if (!getNomeCircuito().equals(circuito.getNomeCircuito())) return false;
        if (!getRetas().equals(circuito.getRetas())) return false;
        if (!getCurvas().equals(circuito.getCurvas())) return false;
        return getChicanes().equals(circuito.getChicanes());
    }

    @Override
    public int hashCode() {
        int result = getNomeCircuito().hashCode();
        result = 31 * result + getDistancia();
        result = 31 * result + getNumeroVoltas();
        result = 31 * result + getRetas().hashCode();
        result = 31 * result + getCurvas().hashCode();
        result = 31 * result + getChicanes().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Circuito{" +
                "nomeCircuito='" + nomeCircuito + '\'' +
                ", distancia=" + distancia +
                ", numeroVoltas=" + numeroVoltas +
                ", retas=" + retas +
                ", curvas=" + curvas +
                ", chicanes=" + chicanes +
                '}';
    }

    public String imprimeCircuito() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------");
        sb.append("\nCircuito: Nome = " + this.nomeCircuito + ", distancia = " + this.getDistancia() + ", Numero de Voltas = " + this.getNumeroVoltas());
        var retas = this.retas;
        var chicanes = this.chicanes;
        var curvas = this.curvas;
        sb.append("\n    Retas do Circuito: ");
        for(int i = 0; i < retas.size(); ++i) {
            sb.append("\n        Reta " + (i + 1) + ": " + retas.get(i));
        }

        sb.append("\n    Curvas do Circuito: ");
        for(int i = 0; i < curvas.size(); ++i) {
            sb.append("\n        Curva " + (i + 1) + ": " + curvas.get(i));
        }

        sb.append("\n    Chicanes do Circuito: ");
        for(int i = 0; i < chicanes.size(); ++i) {
            sb.append("\n        Chicane " + (i + 1) + ": " + chicanes.get(i));
        }
        sb.append("\n-----------------------------");
        return sb.toString();
    }
}
