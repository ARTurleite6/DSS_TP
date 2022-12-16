package business.carros;

public class C2H extends C2 implements Hibrido {
    private int potenciaEletrica;

    @Override
    public int getPotenciaEletrica() {
        return this.potenciaEletrica;
    }
}
