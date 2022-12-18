package business.carros;

public class C2 extends Carro implements Afinavel {

    private float afinacao;

    @Override
    public float getAfinacao() {
        return this.afinacao;
    }

    @Override
    public void setAfinacao(float afinacao) {
        this.afinacao = afinacao;
    }

    @Override
    public Carro clone() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
