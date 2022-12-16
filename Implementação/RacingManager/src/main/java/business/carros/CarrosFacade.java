package business.carros;

import business.exceptions.CarroInexistenteException;
import business.exceptions.CarroJaExisteException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarrosFacade implements IGestCarros {

    private Map<String, Carro> carros;

    public CarrosFacade() {
        this.carros = new HashMap<>();
    }

    public CarrosFacade(Map<String, Carro> carros) {
        this.carros = carros;
    }

    @Contract(pure = true)
    public CarrosFacade(@NotNull CarrosFacade carros) {
        this.carros = carros.carros;
    }

    @Override
    public List<String> getCategorias() {
        return List.of("C1", "C2", "GT", "SC", "C1H", "C2H", "GTH");
    }

    @Override
    public void addCarro(@NotNull Carro carro) throws CarroJaExisteException {
        if(this.carros.containsKey(carro.getModelo())) throw new CarroJaExisteException("Já existe um carro com o modelo de " + carro.getModelo());

        this.carros.put(carro.getModelo(), carro.clone());
    }

    @Override
    public List<Carro> getCarros() {
        return this.carros.values().stream().map(Carro::clone).toList();
    }

    @Override
    public List<String> getModosMotor() {
        return List.of("Conservador", "Agressivo", "Base");
    }

    @Override
    public List<String> getTipoPneus() {
        return List.of("Duro", "Macio", "Chuva");
    }

    @Override
    public Carro getCarro(String modelo) throws CarroInexistenteException {
        if(!this.carros.containsKey(modelo)) throw new CarroInexistenteException("Não existe carro com modelo de " + modelo);
        return this.carros.get(modelo).clone();
    }
}
