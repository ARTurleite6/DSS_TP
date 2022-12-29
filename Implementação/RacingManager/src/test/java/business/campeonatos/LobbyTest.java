package business.campeonatos;

import business.carros.C2;
import business.carros.Carro;
import business.carros.CarrosFacade;
import business.carros.SC;
import business.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {

    @Test
    void startNextRace() throws CampeonatoNaoExisteException, CircuitoNaoExisteException, CarroInexistenteException, PilotoInexistenteException, NaoExistemMaisCorridas, LobbyAtivoInexistenteException {
        CampeonatosFacade facade = new CampeonatosFacade();
        CarrosFacade carroFacade = new CarrosFacade();

        Carro carro1 = carroFacade.getCarro("488 GTE");
        assertTrue(carro1 instanceof C2);
        Carro carro2 = carroFacade.getCarro("Benz 560");
        Carro carro3 = carroFacade.getCarro("GT20");
        var lobby = facade.criaLobby("CampUMnato", false);

        facade.inscreveJogador("Jorge", carro1, "Jorge");
        facade.inscreveJogador("jogador1", carro2, "Afonso");
        facade.inscreveJogador("jogador2", carro3, "Diana");
        System.out.println(lobby);

        var resultados = facade.startNextRace();
        System.out.println(resultados);

        System.out.println(facade.getTabelaClassificativa());

    }
}