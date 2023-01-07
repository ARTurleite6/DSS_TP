package business.carros;

import java.util.Random;

public class SC extends Carro {

    private static final int SC_CILINDRADA = 2500;

    public SC(String modelo, String marca, int potenciaCombustao) {
        super(modelo, marca, SC.SC_CILINDRADA, potenciaCombustao);
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
        return "SC{" + "modelo='" + this.getModelo() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", cilindrada=" + this.getCilindrada() +
                ", potenciaCombustao=" + this.getPotenciaCombustao();
    }

    @Override
    public int getFiabilidade() {
        return (int)((this.getCilindrada() / 10) * 0.25);
    }

    @Override
    public boolean dnf(int volta, boolean chuva) {

        float motorAvaria = (float)this.getModoMotor().getProbAvaria() / 10;

        var random = new Random();
        int x = random.nextInt(80);

        int qualidadePiloto;
        var piloto = this.getPiloto();
        if(piloto == null) return true;
        if(chuva) {
            qualidadePiloto = piloto.getQualidadeChuva();
        } else {
            qualidadePiloto = piloto.getQualidadeTempoSeco();
        }
        int seguranca = piloto.getSeguranca();

        return x > (((qualidadePiloto + seguranca) * 0.75) + (((double) this.getCilindrada()) / 10) * 0.25) - motorAvaria - (100 - this.getEstado());
    }

    @Override
    protected int categoryCompare(String categoria) {
        return categoria.equals("SC") ? 1000 : 500;
    }
}
