package carros;

public class C1 extends Carro implements Afinavel {

    private float afinacao;

    @Override
    public float getAfinacao() {
        return this.afinacao;
    }

    @Override
    public void setAfinacao(int afinacao) {
        this.afinacao = afinacao;
    }
}
