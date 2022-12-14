package business.carros;

import business.campeonatos.Piloto;
import business.exceptions.CilindradaInvalidaException;

public class GTH extends GT implements Hibrido {

    private final int potenciaEletrica;

    public GTH() {
        super();
        this.potenciaEletrica = 0;
    }

    public GTH(String modelo, String marca, int cilindrada, int potenciaCombustao, float fatorDestaste, int potenciaEletrica) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, fatorDestaste);
        this.potenciaEletrica = potenciaEletrica;
    }

    public GTH(String modelo, String marca, int cilindrada, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, float fatorDesgaste, int potenciaEletrica) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste, fatorDesgaste);
        this.potenciaEletrica = potenciaEletrica;
    }

    public GTH(GTH c) {
        super(c);
        this.potenciaEletrica = c.getPotenciaEletrica();
    }

    @Override
    public int getPotenciaEletrica() {
        return this.potenciaEletrica;
    }


    @Override
    public String toString() {
        return "GTH{" + "modelo='" + this.getModelo() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", cilindrada=" + this.getCilindrada() +
                ", potenciaCombustao=" + this.getPotenciaCombustao() +
                ", fatorDesgaste=" + this.getFatorDesgaste() +
                ", potenciaEletrica=" + this.potenciaEletrica +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GTH gth = (GTH) o;

        return getPotenciaEletrica() == gth.getPotenciaEletrica();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getPotenciaEletrica();
        return result;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public GTH clone() {
        return new GTH(this);
    }

    @Override
    public int getFiabilidade() {
        return super.getFiabilidade() - this.potenciaEletrica;
    }
}
