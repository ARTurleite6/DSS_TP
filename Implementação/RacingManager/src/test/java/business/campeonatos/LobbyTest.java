package business.campeonatos;

import business.carros.Carro;
import business.carros.CarrosFacade;
import business.exceptions.*;
import org.junit.jupiter.api.Test;

class LobbyTest {

    @Test
    void startNextRace() throws CampeonatoNaoExisteException, CircuitoNaoExisteException, CarroInexistenteException, PilotoInexistenteException, NaoExistemMaisCorridas, LobbyAtivoInexistenteException {
        CampeonatosFacade facade = new CampeonatosFacade();
        CarrosFacade carroFacade = new CarrosFacade();

        Carro carro1 = carroFacade.getCarro("488 GTE");
        Carro carro2 = carroFacade.getCarro("Classe A 250e");
        Carro carro3 = carroFacade.getCarro("Especial de Corrida");
        var lobby = facade.criaLobby("Yuman", false);

        facade.inscreveJogador("Artur", carro1, "Artur");
        facade.inscreveJogador("jogador1", carro2, "Afonso");
        facade.inscreveJogador("jogador2", carro3, "Diana");
        System.out.println(lobby);

        var resultados = facade.startNextRace();
        System.out.println(resultados);

        System.out.println(facade.getTabelaClassificativa());

    }
}