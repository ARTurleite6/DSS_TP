package business.carros;

import business.campeonatos.Piloto;
import business.exceptions.CilindradaInvalidaException;

public class C2H extends C2 implements Hibrido {
    private int potenciaEletrica;

    public C2H() {
        super();
        this.potenciaEletrica = 0;
    }

    public C2H(String modelo, String marca, int cilindrada, int potenciaCombustao, float afinacao, int potenciaEletrica) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    public C2H(String modelo, String marca, int cilindrada, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacao, int potenciaEletrica) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    public C2H(C2H c) {
        super(c);
        this.potenciaEletrica = c.getPotenciaEletrica();
    }

    @Override
    public int getPotenciaEletrica() {
        return this.potenciaEletrica;
    }

    public C2H clone() {
        return new C2H(this);
    }
}
