package business.carros;

import business.campeonatos.Piloto;

public class SC extends Carro {

    private static int SC_CILINDRADA = 2500;

    public SC() {
        super();
    }

    public SC(String modelo, String marca, int potenciaCombustao) {
        super(modelo, marca, SC.SC_CILINDRADA, potenciaCombustao);
    }

    public SC(String modelo, String marca, int potenciaCombustao, float fiabilidade) {
        super(modelo, marca, SC.SC_CILINDRADA, potenciaCombustao, fiabilidade);
    }

    public SC(String modelo, String marca, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste) {
        super(modelo, marca, SC.SC_CILINDRADA, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
    }

    public SC(Carro c) {
        super(c);
    }

    @Override
    public SC clone() {
        return new SC(this);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SC{");
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int getFiabilidade() {
        return (int)((this.getCilindrada() / 10) * 0.25);
    }
}
