package business.carros;

import business.exceptions.CarroInexistenteException;
import business.exceptions.CarroJaExisteException;

import java.util.List;

public interface IGestCarros {
    List<String> getCategorias();
    void addCarro(Carro carro) throws CarroJaExisteException;
    List<Carro> getCarros();
    List<String> getModosMotor();
    List<String> getTipoPneus();
    Carro getCarro(String modelo) throws CarroInexistenteException;
}
