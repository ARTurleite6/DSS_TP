package carros;

import campeonatos.Piloto;

public class SC extends Carro {

    public SC() {
        super();
    }

    public SC(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade) {
        super(modelo, marca, cilindrada, potenciaCombustao, fiabilidade);
    }

    public SC(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste) {
        super(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
    }

    public SC(Carro c) {
        super(c);
    }

    @Override
    public Carro clone() {
        return new SC(this);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SC{");
        sb.append('}');
        return sb.toString();
    }
}
