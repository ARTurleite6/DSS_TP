package carros;

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

    public CarrosFacade(CarrosFacade carros) {
        this.carros = carros.carros;
    }

    @Override
    public List<String> getCategorias() {
        return List.of("C1", "C2", "GT", "SC", "C1H", "C2H", "GTH");
    }

    @Override
    public void addCarro(Carro carro) {
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
    public Carro getCarro(String modelo) {
        return this.carros.get(modelo).clone();
    }
}
