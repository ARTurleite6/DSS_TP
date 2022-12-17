package business.campeonatos;

public enum GDU {
    Impossivel(7), Possivel(2), Dificil(10);
    private final int dificuldade;
    private GDU(int dificuldade) {
        this.dificuldade = dificuldade;
    }
    public int getDificuldade() {
        return this.dificuldade;
    }
}
