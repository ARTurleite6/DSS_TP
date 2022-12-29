package business.campeonatos;

public enum GDU {
    Impossivel(10), Possivel(2), Dificil(7);
    private final int dificuldade;
    GDU(int dificuldade) {
        this.dificuldade = dificuldade;
    }
    public int getDificuldade() {
        return this.dificuldade;
    }

    public int getTempoMedio() {
        int fatorTipoSeccao = 20000;
        if(this == GDU.Impossivel) fatorTipoSeccao = 40000;
        else if(this == GDU.Dificil) fatorTipoSeccao = 30000;

        return fatorTipoSeccao;
    }
}
