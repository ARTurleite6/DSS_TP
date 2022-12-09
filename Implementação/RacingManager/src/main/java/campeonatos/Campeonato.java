package campeonatos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Campeonato {
    private final String nomeCampeonato;
    private Set<String> circuitos;

    public Campeonato() {
        this.nomeCampeonato = "";
        this.circuitos = new HashSet<>();
    }

    public Campeonato(String nomeCampeonato) {
        this.nomeCampeonato = nomeCampeonato;
        this.circuitos = new HashSet<>();
    }
    public Campeonato(String nomeCampeonato, Set<String> circuitos) {
        this.nomeCampeonato = nomeCampeonato;
        this.circuitos = new HashSet<>(circuitos);
    }

    public Campeonato(Campeonato c) {
        this.nomeCampeonato = c.getNomeCampeonato();
        this.circuitos = c.getCircuitos();
    }

    public String getNomeCampeonato() {
        return this.nomeCampeonato;
    }

    public Set<String> getCircuitos() {
        return new HashSet<>(this.circuitos);
    }

    public Campeonato clone() {
        return new Campeonato(this);
    }

    @Override
    public String toString() {
        return "Campeonato{" +
                "nomeCampeonato='" + nomeCampeonato + '\'' +
                ", circuitos=" + circuitos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campeonato that = (Campeonato) o;

        if (!getNomeCampeonato().equals(that.getNomeCampeonato())) return false;
        return getCircuitos().equals(that.getCircuitos());
    }

    @Override
    public int hashCode() {
        int result = getNomeCampeonato().hashCode();
        result = 31 * result + getCircuitos().hashCode();
        return result;
    }
}
