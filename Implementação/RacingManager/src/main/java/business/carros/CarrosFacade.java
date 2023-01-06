package business.carros;

import business.exceptions.CarroInexistenteException;
import business.exceptions.CarroJaExisteException;
import data.CarrosDAO;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CarrosFacade implements IGestCarros {

    /**
     * Instancia de Map<String,Carro> que mapeia um carro de Nome para carro
     */
    private final Map<String, Carro> carros;

    /**
     * Contrutor que constroi uma instância de CarrosFacade
     */
    public CarrosFacade() {
        this.carros = CarrosDAO.getInstance();
    }

    /**
     * Metodo que adiciona um carro ao Map<String,Carro> carros
     * @param carro carro a adicionar ao Map<String,Carro> carros
     * @throws CarroJaExisteException caso o carro ja exista no Map<String,Carro> carros
     */
    @Override
    public void addCarro(@NotNull Carro carro) throws CarroJaExisteException {
        if(this.carros.containsKey(carro.getModelo())) throw new CarroJaExisteException("Já existe um carro com o modelo de " + carro.getModelo());

        this.carros.put(carro.getModelo(), carro.clone());
    }

    /**
     * Metodo que devolve a lista de Carros
     * @return lista de cópias dos carros presentes no sistema
     */
    @Override
    public List<Carro> getCarros() {
        return this.carros.values().stream().map(Carro::clone).toList();
    }

    /**
     * Metodo que retorna lista de modos de Motor dispniveis
     * @return colecao de modos de motor disponiveis
     */
    @Override
    public Set<String> getModosMotor() {
        return Set.of("Conservador", "Agressivo", "Base");
    }

    /**
     * Metodo que devolve uma lista de categorias de pneu disponiveis
     * @return Lista com as categorias de pneu disponiveis
     */
    @Override
    public Set<String> getTipoPneus() {
        return Set.of("Duro", "Macio", "Chuva");
    }

    /**
     * Metodo que devolve uma instância de um carro dado o seu modelo
     * @param modelo modelo do carro a verificar
     * @return carro com o modelo dado
     * @throws CarroInexistenteException excecao lancada caso não exista nenhum carro com o modelo passado
     */
    @Override
    public Carro getCarro(String modelo) throws CarroInexistenteException {
        if(!this.carros.containsKey(modelo)) throw new CarroInexistenteException("Não existe carro com modelo de " + modelo);
        return this.carros.get(modelo).clone();
    }
}
