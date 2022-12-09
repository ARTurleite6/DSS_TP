package carros;

public class C1H extends C1 implements Hibrido {
    private int potenciaEletrica;

    @Override
    public int getPotenciaEletrica() {
        return this.potenciaEletrica;
    }
}
