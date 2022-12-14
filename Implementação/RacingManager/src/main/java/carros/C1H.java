package carros;

import campeonatos.Piloto;

public class C1H extends C1 implements Hibrido {
    private int potenciaEletrica;

    public C1H() {
        super();
        this.potenciaEletrica = 0;
    }

    public C1H(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade, float afinacao, int potenciaEletrica) {
        super(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    public C1H(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacao, int potenciaEletrica) {
        super(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    public C1H(C1H c) {
        super(c);
        this.potenciaEletrica = c.getPotenciaEletrica();
    }

    @Override
    public int getPotenciaEletrica() {
        return this.potenciaEletrica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        C1H c1H = (C1H) o;

        return getPotenciaEletrica() == c1H.getPotenciaEletrica();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getPotenciaEletrica();
        return result;
    }

    @Override
    public String toString() {
        return "C1H{" +
                "potenciaEletrica=" + potenciaEletrica +
                '}';
    }

    public C1H clone() {
        return new C1H(this);
    }
}
