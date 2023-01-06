package business.carros;

import business.campeonatos.Piloto;
import business.exceptions.CilindradaInvalidaException;

/**
 * Classe que representa um carro do tipo C2H
 */
public class C2H extends C2 implements Hibrido {
    /**
     * Potencia eletrica do carro
     */
    private int potenciaEletrica;

    /**
     * Construtor da classe C2H
     */
    public C2H() {
        super();
        this.potenciaEletrica = 0;
    }

    /**
     * Construtor parametrizado da classe C2H
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param cilindrada Cilindrada do carro
     * @param afinacao Afinacao do carro
     * @param potenciaEletrica Potencia eletrica do carro
     */
    public C2H(String modelo, String marca, int cilindrada, int potenciaCombustao, float afinacao, int potenciaEletrica) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    /**
     * Construtor parametrizado da classe C2H
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param cilindrada Cilindrada do carro
     * @param estadoPneu Estado do pneu do carro
     * @param modoMotor Modo do motor do carro
     * @param tipoPneu Tipo do pneu do carro
     * @param piloto Piloto do carro
     * @param dnf verifica se carro nao terminou a corrida
     * @param tempo tempo do carro numa corrida decorrente
     * @param despiste Despiste do carro
     * @param afinacao Afinacao do carro
     * @param potenciaEletrica Potencia eletrica do carro
     */
    public C2H(String modelo, String marca, int cilindrada, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacao, int potenciaEletrica) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    /**
     * Construtor cópia da classe C2H
     * @param c carro a copiar
     */
    public C2H(C2H c) {
        super(c);
        this.potenciaEletrica = c.getPotenciaEletrica();
    }

    /**
     * Devolve a potencia eletrica do carro
     * @return potencia eletrica do carro
     */
    @Override
    public int getPotenciaEletrica() {
        return this.potenciaEletrica;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public C2H clone() {
        return new C2H(this);
    }

    @Override
    public String toString() {
        return "C2H{" + "modelo='" + this.getModelo() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", cilindrada=" + this.getCilindrada() +
                ", potenciaCombustao=" + this.getPotenciaCombustao() +
                ", afinacao=" + this.getAfinacao() +
                ", potenciaEletrica=" + potenciaEletrica +
                '}';
    }
}

