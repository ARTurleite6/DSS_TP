package business.campeonatos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um circuito de um campeonato.
 */
public class Circuito {
    /**
     * Nome do circuito.
     */
    private final String nomeCircuito;
    /**
     * distancia do circuito
     */
    private final int distancia;
    /**
     * Numero de voltas do circuito
     */
    private final int numeroVoltas;

    /**
     * Lista de retas do circuito.
     */
    private final List<GDU> retas;
    /**
     * Lista de curvas do circuito.
     */
    private final List<GDU> curvas;
    /**
     * Lista de rectas do circuito.
     */
    private final List<GDU> chicanes;

    /**
     * Construtor parametrizado de Circuito.
     * @param nomeCircuito Nome do circuito.
     * @param distancia Distancia do circuito.
     * @param numeroVoltas Numero de voltas do circuito.
     * @param retas Lista de retas do circuito.
     * @param curvas Lista de curvas do circuito.
     * @param chicanes Lista de chicanes do circuito.
     */
    public Circuito(String nomeCircuito, int distancia, int numeroVoltas, List<GDU> retas, List<GDU> curvas, List<GDU> chicanes) {
        this.nomeCircuito = nomeCircuito;
        this.distancia = distancia;
        this.numeroVoltas = numeroVoltas;
        this.retas = new ArrayList<>(retas);
        this.curvas = new ArrayList<>(curvas);
        this.chicanes = new ArrayList<>(chicanes);
    }

    /**
     * Construtor por cópia de Circuito.
     * @param c Circuito a copiar.
     */
    public Circuito(@NotNull Circuito c) {
        this.nomeCircuito = c.getNomeCircuito();
        this.distancia = c.getDistancia();
        this.numeroVoltas = c.getNumeroVoltas();
        this.retas = c.getRetas();
        this.curvas = c.getCurvas();
        this.chicanes = c.getChicanes();
    }

    /**
     * Devolve o nome do circuito.
     * @return Nome do circuito.
     */
    public String getNomeCircuito() {
        return nomeCircuito;
    }

    /**
     * Devolve a distancia do circuito.
     * @return Distancia do circuito.
     */
    public int getDistancia() {
        return distancia;
    }

    /**
     * Devolve o numero de voltas do circuito.
     * @return Numero de voltas do circuito.
     */
    public int getNumeroVoltas() {
        return numeroVoltas;
    }

    /**
     * Devolve a lista de retas do circuito.
     * @return Lista de retas do circuito.
     */
    public List<GDU> getRetas() {
        return new ArrayList<>(this.retas);
    }

    /**
     * Devolve a lista de chicanes do circuito.
     * @return Lista de chicanes do circuito.
     */
    public List<GDU> getChicanes() {
        return new ArrayList<>(this.chicanes);
    }

    /**
     * Devolve a lista de curvas do circuito.
     * @return Lista de curvas do circuito.
     */
    public List<GDU> getCurvas() {
        return new ArrayList<>(this.curvas);
    }

    /**
     * Metodo que retorna lista de todas as seccoes do circuito(retas, curvas e chicanes)
     * @return Lista de todas as seccoes do circuito.
     */
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
