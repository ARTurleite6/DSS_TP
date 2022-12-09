package carros;

import java.util.List;

public interface IGestCarros {
    List<String> getCategorias();
    void addCarro(Carro carro);
    List<Carro> getCarros();
    List<String> getModosMotor();
    List<String> getTipoPneus();
    Carro getCarro(String modelo);
}
