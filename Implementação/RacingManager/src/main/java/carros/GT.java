package carros;

import campeonatos.Piloto;

public class GT extends Carro {
    private float fatorDesgaste;

    public GT() {
        super();
        this.fatorDesgaste = 0;
    }

    public GT(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade, float fatorDesgaste) {
        super(modelo, marca, cilindrada, potenciaCombustao, fiabilidade);
        this.fatorDesgaste = fatorDesgaste;
    }

    public GT(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, float fatorDesgaste) {
        super(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
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
        result = 31 * result + (getFatorDesgaste() != +0.0f ? Float.floatToIntBits(getFatorDesgaste()) : 0);
        return result;
    }
}
