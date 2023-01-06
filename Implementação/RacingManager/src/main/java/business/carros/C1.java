package business.carros;

import business.campeonatos.Piloto;

import java.util.Random;

/**
 * Classe que representa um carro da categoria C1
 */
public class C1 extends Carro implements Afinavel {

    /**
     * Variavel padrao para afinacao dos Carros C1
     */
    private static final int C1_CILINDRADA = 6000;

    /**
     * Variavel para afinacao do carro
     */
    private float afinacao;

    /**
     * Construtor de um carro C1
     */
    public C1() {
        super();
        this.afinacao = 0;
    }


    /**
     * Construtor parametrizado de um carro C1
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param potenciaCombustao Potencia de combustao do carro
     * @param afinacao Afinacao do carro
     */
    public C1(String modelo, String marca, int potenciaCombustao, float afinacao) {
        super(modelo, marca, C1.C1_CILINDRADA, potenciaCombustao);
        this.afinacao = afinacao;
    }

    /**
     * Construtor parametrizado de um carro C1
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param potenciaCombustao Potencia de combustao do carro
     * @param estadoPneu Estado do pneu do carro
     * @param modoMotor Modo do motor do carro
     * @param tipoPneu Tipo do pneu do carro
     * @param piloto Piloto do carro
     * @param dnf Carro não consegui terminar corrida ou nao
     * @param tempo Tempo do carro numa corrida
     * @param despiste Carro despistou ou não
     * @param afinacao Afinacao do carro
     */
    public C1(String modelo, String marca, int potenciaCombustao, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu, Piloto piloto, boolean dnf, int tempo, boolean despiste, int afinacao) {
        super(modelo, marca, C1.C1_CILINDRADA, potenciaCombustao, estadoPneu, modoMotor, tipoPneu, piloto, dnf, tempo, despiste);
        this.afinacao = afinacao;
    }

    /**
     * Construtor cópia de um carro C1
     * @param c Carro C1 a ser copiado
     */
    public C1(C1 c) {
        super(c);
        this.afinacao = c.getAfinacao();
    }

    /**
     * Devolve a afinacao do carro
     * @return Afinacao do carro
     */
    @Override
    public int getFiabilidade() {
        return (int)(95 - this.getAfinacao());
    }

    /**
     * Devolve a afinacao do carro
     * @return Afinacao do carro
     */
    @Override
    public float getAfinacao() {
        return this.afinacao;
    }

    /**
     * Atualiza a afinacao do carro
     * @param afinacao Afinacao do carro
     */
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
        return "C1{" + "modelo='" + this.getModelo() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", cilindrada=" + this.getCilindrada() +
                ", potenciaCombustao=" + this.getPotenciaCombustao() +
                ", afinacao=" + this.afinacao +
                "}";
    }

    /**
     * Metodo que atualiza a afinacao do carro
     * @param volta Volta na corrida
     * @param chuva Se esta a chover ou nao
     * @return Afinacao do carro
     */
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

    /**
     * Metodo que atualiza a afinacao do carro
     * @param categoria Categoria do outro carro
     * @return Tempo necessário para poder ultrapassar o outro carro
     */
    @Override
    protected int categoryCompare(String categoria) {
        if(categoria.equals("C1") || categoria.equals("C1H")) return 1000;
        else return 2000;
    }
}
