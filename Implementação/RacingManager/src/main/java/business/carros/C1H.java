package business.carros;

import business.campeonatos.Piloto;

/**
 * Classe que representa um carro do tipo C1H
 */
@SuppressWarnings("MethodDoesntCallSuperMethod")
public class C1H extends C1 implements Hibrido {
    /**
     * Potencia eletrica do carro
     */
    private final int potenciaEletrica;

    /**
     * Construtor da classe C1H
     */
    public C1H() {
        super();
        this.potenciaEletrica = 0;
    }

    /**
     * Construtor parametrizado da classe C1H
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param potenciaCombustao Potencia de combustao do carro
     * @param afinacao Afinacao do carro
     * @param potenciaEletrica Potencia eletrica do carro
     */
    public C1H(String modelo, String marca, int potenciaCombustao, float afinacao, int potenciaEletrica) {
        super(modelo, marca, potenciaCombustao, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    /**
     * Construtor parametrizado da classe C1H
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param potenciaCombustao Potencia de combustao do carro
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
    public C1H(String modelo, String marca, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacao, int potenciaEletrica) {
        super(modelo, marca, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste, afinacao);
        this.potenciaEletrica = potenciaEletrica;
    }

    /**
     * Construtor cópia da classe C1H
     * @param c carro a copiar
     */
    public C1H(C1H c) {
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
        return "C1H{" + "modelo='" + this.getModelo() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", cilindrada=" + this.getCilindrada() +
                ", potenciaCombustao=" + this.getPotenciaCombustao() +
                ", afinacao=" + this.getAfinacao() +
                ", potenciaEletrica=" + potenciaEletrica +
                '}';
    }

    public C1H clone() {
        return new C1H(this);
    }

    /**
     * Devolve a fiabilidade do carro
     * @return fiabilidade do carro
     */
    @Override
    public int getFiabilidade() {
        return super.getFiabilidade() - this.getPotenciaEletrica() - this.getModoMotor().getProbAvaria();
    }
}
