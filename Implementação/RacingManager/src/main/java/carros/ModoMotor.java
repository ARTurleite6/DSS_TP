package carros;

public enum ModoMotor {
    //TODO por definir valores associados à probabilidade de avaria
    Conservador(100), Agressivo(100), Base(100);

    private int probAvaria;

    private ModoMotor(int probAvaria) {
        this.probAvaria = probAvaria;
    }

    public int getProbAvaria() {
        return this.probAvaria;
    }
}
