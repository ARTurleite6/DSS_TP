package carros;

public class GTH extends GT implements Hibrido {

    private int potenciaEletrica;

    @Override
    public int getPotenciaEletrica() {
        return this.potenciaEletrica;
    }
}
