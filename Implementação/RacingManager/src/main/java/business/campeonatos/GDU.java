package business.campeonatos;

public enum GDU {;
    private final int dificuldade;

    GDU(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public int getDificuldade() {
        return this.dificuldade;
    }
}
