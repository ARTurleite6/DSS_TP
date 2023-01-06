package business.carros;

import business.campeonatos.Piloto;
import business.exceptions.CilindradaInvalidaException;

import java.util.Random;

/**
 * Classe que representa um carro do tipo GT
 */
public class GT extends Carro {
    /**
     * Fator de desgaste do carro
     */
    private float fatorDesgaste;

    /**
     * Construtor da classe GT
     */
    public GT() {
        super();
        this.fatorDesgaste = 0;
    }

    /**
     * Construtor parametrizado da classe GT
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param cilindrada Cilindrada do carro
     * @param fatorDesgaste Fator de desgaste do carro
     */
    public GT(String modelo, String marca, int cilindrada, int potenciaCombustao, float fatorDesgaste) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao);
        if(cilindrada < 2000 || cilindrada > 4000) throw new CilindradaInvalidaException("GTs apenas podem ter cilindradas num intervalo de 3000 e 5000");
        this.fatorDesgaste = fatorDesgaste;
    }

    /**
     * Construtor parametrizado da classe GT
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
     * @param fatorDesgaste Fator de desgaste do carro
     */
    public GT(String modelo, String marca, int cilindrada, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, float fatorDesgaste) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
        if(cilindrada < 2000 || cilindrada > 4000) throw new CilindradaInvalidaException("GTs apenas podem ter cilindradas num intervalo de 3000 e 5000");
        this.fatorDesgaste = fatorDesgaste;
    }

    /**
     * Construtor de copia da classe GT
     * @param c GT a copiar
     */
    public GT(GT c) {
        super(c);
        this.fatorDesgaste = c.getFatorDesgaste();
    }

    /**
     * Devolve o fator de desgaste do carro
     * @return Fator de desgaste do carro
     */
    public float getFatorDesgaste() {
        return this.fatorDesgaste;
    }

    @Override
    public Carro clone() {
        return new GT(this);
    }

    @Override
    public String toString() {
        return "GT{" + "modelo='" + this.getModelo() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", cilindrada=" + this.getCilindrada() +
                ", potenciaCombustao=" + this.getPotenciaCombustao() +
                ", fatorDesgaste=" + this.fatorDesgaste +
                '}';
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

    /**
     * Metodo que calcula o tempo de um carro numa corrida
     * @return Fiabilidade do carro
     */
    @Override
    public int getFiabilidade() {
        return (int)(((100000 / this.getCilindrada()) * 1.7) - this.getModoMotor().getProbAvaria());
    }

    @Override
    public boolean dnf(int volta, boolean chuva) {
        int motorH = 0;
        if(this instanceof Hibrido h)
            motorH = h.getPotenciaEletrica() / 50;

        Random random = new Random();
        int x = random.nextInt(85);
        float motorAvaria = (float)this.getModoMotor().getProbAvaria() / 10;
        System.out.println("DNF GT = " + ((((100000) / this.getCilindrada()) * 1.7) - (volta * this.fatorDesgaste) - motorH - motorAvaria - (100 - this.getEstado())));
        return x > (((100000) / this.getCilindrada()) * 1.7) - (volta * this.fatorDesgaste) - motorH - motorAvaria - (100 - this.getEstado());
    }


    /**
     * Metodo que calcula o tempo de um carro numa corrida
     * @return Tempo do carro numa corrida
     */
    @Override
    protected int categoryCompare(String categoria) {
        if(categoria.equals("GT") || categoria.equals("GTH")) return 1000;
        else if(categoria.equals("SC")) return 2000;
        else return 500;
    }
}
