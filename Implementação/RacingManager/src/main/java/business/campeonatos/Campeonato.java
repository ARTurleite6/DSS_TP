package business.campeonatos;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class Campeonato {

    /**
     * Nome do Campeonato
     */
    private final String nomeCampeonato;

    /**
     * Lista com os nomes dos circuitos do campeonato
     */
    private final Set<String> circuitos;

    /**
     * @param nomeCampeonato nome a colocar no campeonato
     * @param circuitos circuitos escolhidos para campeonato
     */
    public Campeonato(String nomeCampeonato, Set<String> circuitos) {
        this.nomeCampeonato = nomeCampeonato;
        this.circuitos = new HashSet<>(circuitos);
    }

    /**
     * @param c campeonato que deseja copiar
     */
    public Campeonato(@NotNull Campeonato c) {
        this.nomeCampeonato = c.getNomeCampeonato();
        this.circuitos = c.getCircuitos();
    }

    /**
     * @return nome do campeonato
     */
    public String getNomeCampeonato() {
        return this.nomeCampeonato;
    }

    /**
     * @return colecao de nomes dos circuitos
     */
    public Set<String> getCircuitos() {
        return new HashSet<>(this.circuitos);
    }

    /**
     * @return copia do campeonato
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Campeonato clone() {
        return new Campeonato(this);
    }

    /**
     * @return representacao do campeonato em string
     */
    @Override
    public String toString() {
        return "Campeonato{" +
                "nomeCampeonato='" + nomeCampeonato + '\'' +
                ", circuitos=" + circuitos +
                '}';
    }

    /**
     * @param o objecto a comparar com a instancia campeonato
     * @return comparacao do objeto, true se forem iguais, falso caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campeonato that = (Campeonato) o;

        if (!getNomeCampeonato().equals(that.getNomeCampeonato())) return false;
        return getCircuitos().equals(that.getCircuitos());
    }

    /**
     * @return codigo hash do campeonato
     */
    @Override
    public int hashCode() {
        int result = getNomeCampeonato().hashCode();
        result = 31 * result + getCircuitos().hashCode();
        return result;
    }

    /**
     * @return Representacao do campeonato para ser apresentado no menu
     */
    public String imprimeCampeonato() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------");
        sb.append("\nCampeonato: Nome = ").append(this.nomeCampeonato);
        var circuitos = this.circuitos;
        sb.append("\n    Lista de circuitos do campeonato:");
        for(var circuito : circuitos) {
            sb.append("\n     ").append(circuito);
        }
        sb.append("\n-----------------------------");
        return sb.toString();
    }
}
