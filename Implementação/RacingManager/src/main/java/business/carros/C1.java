package business.carros;

import business.campeonatos.Piloto;

import java.util.Random;

public class C1 extends Carro implements Afinavel {

    private static final int C1_CILINDRADA = 6000;

    private float afinacao;

    public C1() {
        super();
        this.afinacao = 0;
    }


    public C1(String modelo, String marca, int potenciaCombustao, float afinacao) {
        super(modelo, marca, C1.C1_CILINDRADA, potenciaCombustao);
        this.afinacao = afinacao;
    }

    public C1(String modelo, String marca, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacao) {
        super(modelo, marca, C1.C1_CILINDRADA, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
        this.afinacao = afinacao;
    }

    public C1(C1 c) {
        super(c);
        this.afinacao = c.getAfinacao();
    }

    @Override
    public int getFiabilidade() {
        return (int)(95 - this.getAfinacao());
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
        return new C1(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        C1 c1 = (C1) o;

        return Float.compare(c1.getAfinacao(), getAfinacao()) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getAfinacao() != 0.0f ? Float.floatToIntBits(getAfinacao()) : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("C1{");
        sb.append("afinacao=").append(afinacao);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean dnf(int volta, boolean chuva) {
        var motorH = 0;
        if(this instanceof Hibrido hibrido) {
            motorH = hibrido.getPotenciaEletrica() / 50;
        }
        System.out.println("Motor H " + motorH);
        Random random = new Random();
        int x = random.nextInt(95);
        float motorAvaria = (float)this.getModoMotor().getProbAvaria() / 10;
        System.out.println("Fator c1 = " + (95 - motorH - motorAvaria - (100 - this.getEstado())) );
        return x > 95 - motorH - motorAvaria - (100 - this.getEstado());
    }

    @Override
    protected int categoryCompare(String categoria) {
        if(categoria.equals("C1") || categoria.equals("C1H")) return 1000;
        else return 2000;
    }
}
