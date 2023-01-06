package business.carros;

import business.exceptions.CarroInexistenteException;
import business.exceptions.CarroJaExisteException;

import java.util.List;
import java.util.Set;

public interface IGestCarros {
    void addCarro(Carro carro) throws CarroJaExisteException;
    List<Carro> getCarros();
    Set<String> getModosMotor();
    Set<String> getTipoPneus();
    Carro getCarro(String modelo) throws CarroInexistenteException;
}
