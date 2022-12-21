package business.carros;

import business.campeonatos.GDU;
import business.campeonatos.Piloto;
import business.exceptions.CilindradaInvalidaException;

import java.util.Random;

public class GT extends Carro {
    private float fatorDesgaste;

    public GT() {
        super();
        this.fatorDesgaste = 0;
    }

    public GT(String modelo, String marca, int cilindrada, int potenciaCombustao, float fatorDesgaste) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao);
        if(cilindrada < 2000 || cilindrada > 4000) throw new CilindradaInvalidaException("GTs apenas podem ter cilindradas num intervalo de 3000 e 5000");
        this.fatorDesgaste = fatorDesgaste;
    }

    public GT(String modelo, String marca, int cilindrada, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, float fatorDesgaste) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
        if(cilindrada < 2000 || cilindrada > 4000) throw new CilindradaInvalidaException("GTs apenas podem ter cilindradas num intervalo de 3000 e 5000");
        this.fatorDesgaste = fatorDesgaste;
    }

    public GT(GT c) {
        super(c);
        this.fatorDesgaste = c.getFatorDesgaste();
    }

    public float getFatorDesgaste() {
        return this.fatorDesgaste;
    }

    @Override
    public Carro clone() {
        return new GT(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GT{");
        sb.append("fatorDesgaste=").append(fatorDesgaste);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GT gt = (GT) o;

        return Float.compare(gt.getFatorDesgaste(), getFatorDesgaste()) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getFatorDesgaste() != 0.0f ? Float.floatToIntBits(getFatorDesgaste()) : 0);
        return result;
    }

    @Override
    public int getFiabilidade() {
        return (int)(((100000 / this.getCilindrada()) * 1.7) - this.getModoMotor().getProbAvaria());
    }

    @Override
    public boolean dnf(int volta, boolean chuva) {
        int motorH = 0;
        if(this instanceof Hibrido h)
            motorH = h.getPotenciaEletrica() / 20;

        Random random = new Random();
        int x = random.nextInt(100);
        int motorAvaria = this.getModoMotor().getProbAvaria();
        return x > (((100000) / this.getCilindrada()) * 1.7) - (volta * this.fatorDesgaste) - motorH - motorAvaria - (100 - this.getEstado());
    }


    @Override
    protected int categoryCompare(String categoria) {
        return 0;
    }
}
