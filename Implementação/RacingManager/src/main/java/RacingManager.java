import business.IRacingManagerLNFacade;
import business.RacingManagerLNFacade;
import business.exceptions.*;
import ui.MenuPrincipal;

import java.util.*;

/**
 * Classe que executa o jogo
 */
public class RacingManager {

    /**
     * Facade com a logica de negocio do programa
     */
    private final IRacingManagerLNFacade facade;

    /**
     * Scanner para receber input do utilizador
     */
    private final Scanner scanner;

    /**
     * Instancia que apresenta e lida com o menu principal do programa
     */
    private final MenuPrincipal menuPrincipal;

    /**
     * Construtor da classe do jogo onde inicializa os objetos necessarios
     */
    public RacingManager() {
        this.scanner = new Scanner(System.in);
        this.facade = new RacingManagerLNFacade();
        this.menuPrincipal = new MenuPrincipal(this.scanner, facade);
    }

    /**
     * Funcao que executa o programa RacingManager
     */
    public void run() throws UtilizadorNaoExisteException {
        this.menuPrincipal.run();
    }
}
