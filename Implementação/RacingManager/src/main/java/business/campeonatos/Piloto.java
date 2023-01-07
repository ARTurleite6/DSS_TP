package business.campeonatos;

import org.jetbrains.annotations.NotNull;

/**
 * Classe que representa um piloto de um campeonato.
 */
public class Piloto {
    /**
     * Nome do piloto.
     */
    private final String nome;
    /**
     * cts(chuva vs tempo seco) do piloto.
     */
    private final int cts;
    /**
     * sva(seguranca vs agressividade) do piloto.
     */
    private final int sva;

    /**
     * Construtor default do Piloto
     */
    public Piloto() {
        this.nome = "";
        this.cts = 0;
        this.sva = 0;
    }

    /**
     * Construtor parametrizado de Piloto.
     * @param nome Nome do piloto.
     * @param cts Cts do piloto.
     * @param sva Sva do piloto.
     */
    public Piloto(String nome, int cts, int sva) {
        this.nome = nome;
        this.cts = cts;
        this.sva = sva;
    }

    /**
     * Construtor por cópia de Piloto.
     * @param p Piloto a copiar.
     */
    public Piloto(@NotNull Piloto p) {
        this.nome = p.getNome();
        this.cts = p.getCts();
        this.sva = p.getSva();
    }

    /**
     * Devolve o nome do piloto.
     * @return Nome do piloto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve o cts do piloto.
     * @return Cts do piloto.
     */
    public int getCts() {
        return cts;
    }

    /**
     * Devolve o sva do piloto.
     * @return Sva do piloto.
     */
    public int getSva() {
        return sva;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Piloto clone() {
        return new Piloto(this);
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "nome='" + nome + '\'' +
                ", cts=" + cts +
                ", sva=" + sva +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += hash * 7 + this.nome.hashCode();
        hash += hash * 7 + Integer.hashCode(this.cts);
        hash += hash * 7 + Integer.hashCode(this.sva);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piloto piloto = (Piloto) o;

        if (getCts() != piloto.getCts()) return false;
        if (getSva() != piloto.getSva()) return false;
        return getNome().equals(piloto.getNome());
    }

    public String imprimePiloto() {
        return "Piloto: Nome = " + this.getNome() + ", sva = " + this.getSva() + ", cts = " + this.getCts();
    }

    /**
     * Metodo que calcula a qualidade em tempo seco do utilizador numa escala de 0 a 10
     * @return Qualidade em tempo seco do utilizador numa escala de 0 a 10
     */
    public int getQualidadeTempoSeco() {
        return this.cts;
    }

    /**
     * Metodo que calcula a qualidade em tempo de chuva do utilizador numa escala de 0 a 10
     * @return Qualidade em tempo de chuva do utilizador numa escala de 0 a 10
     */
    public int getQualidadeChuva() {
        return 10 - this.cts;
    }

    /**
     * Metodo que calcula a qualidade em agressividade do utilizador numa escala de 0 a 10
     * @return Qualidade em agressividade do utilizador numa escala de 0 a 10
     */
    public int getAgressividade() {
        return this.sva;
    }

    /**
     * Metodo que calcula a qualidade em segurança do utilizador numa escala de 0 a 10
     * @return Qualidade em segurança do utilizador numa escala de 0 a 10
     */
    public int getSeguranca() {
        return 10 - this.sva;
    }
}
