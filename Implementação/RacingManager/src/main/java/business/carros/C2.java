package business.carros;

import business.campeonatos.Piloto;
import business.exceptions.CilindradaInvalidaException;

import java.util.Random;

/**
 * Classe que representa um carro do tipo C2
 */
public class C2 extends Carro implements Afinavel {

    /**
     * Cilindrada do carro
     */
    private float afinacao;

    /**
     * Construtor da classe C2
     */
    public C2() {
        super();
    }

    /**
     * Construtor parametrizado da classe C2
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param cilindrada Cilindrada do carro
     * @param afinacao Afinacao do carro
     */
    public C2(String modelo, String marca, int cilindrada, int potenciaCombustao, float afinacao) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao);
        if(cilindrada < 3000 || cilindrada > 5000) throw new CilindradaInvalidaException("C2s apenas podem possuir uma ciindrada no intervalo de 3000 e 5000");
        this.afinacao = afinacao;
    }

    /**
     * Construtor parametrizado da classe C2
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
     */
    public C2(String modelo, String marca, int cilindrada, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacao) throws CilindradaInvalidaException {
        super(modelo, marca, cilindrada, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
        if(cilindrada < 3000 || cilindrada > 5000) throw new CilindradaInvalidaException("C2s apenas podem possuir uma ciindrada no intervalo de 3000 e 5000");
        this.afinacao = afinacao;
    }

    /**
     * Construtor de copia da classe C2
     * @param c carro a copiar
     */
    public C2(C2 c) {
        super(c);
        this.afinacao = c.getAfinacao();
    }

    /**
     * Devolve a fiabilidade do carro
     * @return fiabilidade do carro
     */
    @Override
    public int getFiabilidade() {
        return (int)(80 + (this.getCilindrada() / 1200) + (this.getAfinacao() / 10) + this.getModoMotor().getProbAvaria());
    }

    /**
     * Devolve a afinacao do carro
     * @return afinacao do carro
     */
    @Override
    public float getAfinacao() {
        return this.afinacao;
    }

    /**
     * Atualiza a afinacao do carro
     * @param afinacao nova afinacao do carro
     */
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
        String sb = "Carro{" + "modelo='" + this.getModelo() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", cilindrada=" + this.getCilindrada() +
                ", potenciaCombustao=" + this.getPotenciaCombustao() +
                "afinacao=" + afinacao +
                "}";
        return sb;
    }

    /**
     * Verifica se carro não conseguiu terminar a corrida
     * @param volta Volta em que o carro se encontra
     * @return true se carro não terminou a corrida, false caso contrário
     */
    @Override
    public boolean dnf(int volta, boolean chuva) {
        int motorH = 0;
        if(this instanceof Hibrido h)
            motorH = h.getPotenciaEletrica() / 50;

        Random random = new Random();
        int x = random.nextInt(80);
        System.out.println("Valor random = " + x);
        float motorAvaria = (float)this.getModoMotor().getProbAvaria() / 10;
        System.out.println("Motor Hibrido C2 = " + motorH);
        System.out.println("Motor Avaria C2 = " + motorAvaria);
        System.out.println("Fator cilindrada C2 = " + this.getEstado() / 1200.0);

        System.out.println("Fator C2 = " + (80 - motorH - ((float)this.getCilindrada() / 1200.0) - (this.getAfinacao() / 10) - motorAvaria - (100 - this.getEstado())));

        return x > 80 - motorH - ((float)this.getCilindrada() / 1200.0) + (this.getAfinacao() / 10) - motorAvaria - (100 - this.getEstado());
    }

    /**
     * Metodo que retorna o tempo necessário para ultrapassar outro carro de acordo com a categoria
     * @param categoria Categoria do carro a ultrapassar
     * @return tempo necessário para ultrapassar o carro
     */
    @Override
    protected int categoryCompare(String categoria) {
        if(categoria.equals("C2") || categoria.equals("C2H")) return 1000;
        else if(categoria.equals("C1") || categoria.equals("C1H")) return 500;
        else return 2000;
    }
}
