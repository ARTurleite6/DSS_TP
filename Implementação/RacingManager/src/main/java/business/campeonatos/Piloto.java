package business.campeonatos;

public class Piloto {
    private final String nome;
    private final int cts;
    private final int sva;

    public Piloto() {
        this.nome = "";
        this.cts = 0;
        this.sva = 0;
    }

    public Piloto(String nome, int cts, int sva) {
        this.nome = nome;
        this.cts = cts;
        this.sva = sva;
    }

    public Piloto(Piloto p) {
        this.nome = p.getNome();
        this.cts = p.getCts();
        this.sva = p.getSva();
    }

    public String getNome() {
        return nome;
    }

    public int getCts() {
        return cts;
    }

    public int getSva() {
        return sva;
    }

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
}
