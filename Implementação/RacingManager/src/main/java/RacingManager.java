import business.IRacingManagerLNFacade;
import business.RacingManagerLNFacade;
import business.exceptions.*;
import ui.MenuPrincipal;

import java.util.*;

public class RacingManager {

    private final IRacingManagerLNFacade facade;
    private final Scanner scanner;
    private final MenuPrincipal menuPrincipal;

    public RacingManager() {
        this.scanner = new Scanner(System.in);
        this.facade = new RacingManagerLNFacade();
        this.menuPrincipal = new MenuPrincipal(this.scanner, facade);
    }

    public void run() throws UtilizadorNaoExisteException {
        this.menuPrincipal.run();
    }
}
