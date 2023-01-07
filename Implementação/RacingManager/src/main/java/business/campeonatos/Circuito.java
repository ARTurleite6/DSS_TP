package business.campeonatos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Circuito {
    private final String nomeCircuito;
    private final int distancia;
    private final int numeroVoltas;

    private final List<GDU> retas;
    private final List<GDU> curvas;
    private final List<GDU> chicanes;

    public Circuito(String nomeCircuito, int distancia, int numeroVoltas, List<GDU> retas, List<GDU> curvas, List<GDU> chicanes) {
        this.nomeCircuito = nomeCircuito;
        this.distancia = distancia;
        this.numeroVoltas = numeroVoltas;
        this.retas = new ArrayList<>(retas);
        this.curvas = new ArrayList<>(curvas);
        this.chicanes = new ArrayList<>(chicanes);
    }

    public Circuito(@NotNull Circuito c) {
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

    public List<GDU> getSeccoes() {
        var res = new ArrayList<>(this.retas);
        res.addAll(this.chicanes);
        res.addAll(this.curvas);
        return res;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
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
        sb.append("\nCircuito: Nome = ").append(this.nomeCircuito).append(", distancia = ").append(this.getDistancia()).append(", Numero de Voltas = ").append(this.getNumeroVoltas());
        var retas = this.retas;
        var chicanes = this.chicanes;
        var curvas = this.curvas;
        sb.append("\n    Retas do Circuito: ");
        for(int i = 0; i < retas.size(); ++i) {
            sb.append("\n        Reta ").append(i + 1).append(": ").append(retas.get(i));
        }

        sb.append("\n    Curvas do Circuito: ");
        for(int i = 0; i < curvas.size(); ++i) {
            sb.append("\n        Curva ").append(i + 1).append(": ").append(curvas.get(i));
        }

        sb.append("\n    Chicanes do Circuito: ");
        for(int i = 0; i < chicanes.size(); ++i) {
            sb.append("\n        Chicane ").append(i + 1).append(": ").append(chicanes.get(i));
        }
        sb.append("\n-----------------------------");
        return sb.toString();
    }

}
