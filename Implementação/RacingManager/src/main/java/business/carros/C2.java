package business.carros;

import business.campeonatos.GDU;
import business.campeonatos.Piloto;
import business.exceptions.CilindradaInvalidaException;

import java.util.Random;

public class C2 extends Carro implements Afinavel {

    private float afinacao;

    public C2() {
        super();
    }

    public C2(String modelo, String marca, int cilindrada, int potenciaCombustao, float afinacao) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao);
        if(cilindrada < 3000 || cilindrada > 5000) throw new CilindradaInvalidaException("C2s apenas podem possuir uma ciindrada no intervalo de 3000 e 5000");
        this.afinacao = afinacao;
    }

    public C2(String modelo, String marca, int cilindrada, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacacao) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
        if(cilindrada < 3000 || cilindrada > 5000) throw new CilindradaInvalidaException("C2s apenas podem possuir uma ciindrada no intervalo de 3000 e 5000");
        this.afinacao = afinacacao;
    }

    public C2(C2 c) {
        super(c);
        this.afinacao = c.getAfinacao();
    }

    @Override
    public int getFiabilidade() {
        return (int)(80 + (this.getCilindrada() / 1200) + (this.getAfinacao() / 10) + this.getModoMotor().getProbAvaria());
    }


    @Override
    public float getAfinacao() {
        return this.afinacao;
    }

    @Override
    public void setAfinacao(float afinacao) {
        this.afinacao = afinacao;
    }

    @Override
    public Carro clone() {
        return new C2(this);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean dnf(int volta, boolean chuva) {
        int motorH = 0;
        if(this instanceof Hibrido h)
            motorH = h.getPotenciaEletrica() / 20;

        Random random = new Random();
        int x = random.nextInt(100);
        int motorAvaria = this.getModoMotor().getProbAvaria();

        return x > 80 - motorH + (this.getCilindrada() / 1200) + (this.getAfinacao() / 10) - motorAvaria - (100 - this.getEstado());
    }

    @Override
    protected int categoryCompare(String categoria) {
        return 0;
    }
}
