package business.campeonatos;

public enum GDU {
    Impossivel(10), Possivel(2), Dificil(7);
    private final int dificuldade;
    private GDU(int dificuldade) {
        this.dificuldade = dificuldade;
    }
    public int getDificuldade() {
        return this.dificuldade;
    }

    public int getTempoMedio() {
        int fatorTipoSeccao = 1000;
        if(this == GDU.Impossivel) fatorTipoSeccao = 3000;
        if(this == GDU.Dificil) fatorTipoSeccao = 2000;

        return fatorTipoSeccao;
    }
}
