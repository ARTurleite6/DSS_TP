package business.carros;

import business.campeonatos.GDU;
import business.campeonatos.Piloto;

import java.util.Random;

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
        System.out.println("DNF SC = " + ((((qualidadePiloto + seguranca) * 0.75) + (((double) this.getCilindrada()) / 10) * 0.25) - motorAvaria - (100 - this.getEstado())) );

        return x > (((qualidadePiloto + seguranca) * 0.75) + (((double) this.getCilindrada()) / 10) * 0.25) - motorAvaria - (100 - this.getEstado());
    }

    @Override
    protected int categoryCompare(String categoria) {
        return categoria.equals("SC") ? 1000 : 500;
    }
}
