package ui;

import business.IRacingManagerLNFacade;
import business.exceptions.UtilizadorJaExistenteException;
import business.exceptions.UtilizadorNaoExisteException;
import business.users.Autenticavel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final IRacingManagerLNFacade facade;

    private final TextMenu textMenuPrincipal;

    private final List<SubMenu> subMenus;
    private Autenticavel userAutenticado;
    private final Scanner scanner;

    public MenuPrincipal(Scanner scanner, IRacingManagerLNFacade facade) {
        this.scanner = scanner;
        this.facade = facade;
        this.textMenuPrincipal = new TextMenu(this.scanner);
        this.subMenus = new ArrayList<>();
        this.subMenus.add(new MenuPilotos(this.scanner, this.facade, this.userAutenticado));
        this.subMenus.add(new MenuCircuitos(this.scanner, this.facade, this.userAutenticado));
        this.subMenus.add(new MenuCampeonatos(this.scanner, this.facade, this.userAutenticado));
        this.subMenus.add(new MenuCarros(this.scanner, this.facade, this.userAutenticado));
        this.textMenuPrincipal.addOption("Registar-se como jogador", this::handleRegistoJogador, () -> this.userAutenticado == null);
        this.textMenuPrincipal.addOption("Login", this::handleLogin, () -> this.userAutenticado == null);
        this.textMenuPrincipal.addOption("Logout", this::handleLogout, () -> this.userAutenticado != null);
        this.textMenuPrincipal.addOption("Gestão Pilotos", this.subMenus.get(0)::run, null);
        this.textMenuPrincipal.addOption("Gestão Circuitos", this.subMenus.get(1)::run, null);
        this.textMenuPrincipal.addOption("Gestão Campeonatos", this.subMenus.get(2)::run, null);
        this.textMenuPrincipal.addOption("Gestão Carros", this.subMenus.get(3)::run, null);
    }

    private void handleRegistoJogador() {
        System.out.println("Insira o username desejado");
        var username = this.scanner.nextLine();
        System.out.println("Insira a password desejada");
        var password = this.scanner.nextLine();
        System.out.println("Deseja ser premium? S/N");
        var choice = this.scanner.nextLine();
        boolean premium;
        if(!choice.equals("S") && !choice.equals("N")) {
            System.out.println("Input inválido");
            return;
        }
        premium = choice.equals("S");
        try {
            this.facade.registaJogador(username, password, premium);
            System.out.println("User registado com sucesso");
        } catch (UtilizadorJaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleLogin() {
        System.out.println("Insira o seu username");
        var username = this.scanner.nextLine();
        System.out.println("Insira a sua password");
        var password = this.scanner.nextLine();
        try {
            var user = this.facade.autenticaUtilizador(username, password);
            if(user != null) {
                this.userAutenticado = user;
                this.subMenus.forEach(menu -> menu.setUserAutenticado(user));
                System.out.println("Login efetuado com sucesso");
                System.out.println(this.userAutenticado);
            }
            else System.out.println("Login efetuado com insucesso");
        } catch (UtilizadorNaoExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleLogout() {
        try {
            this.facade.logout(this.userAutenticado.getUsername());
            this.userAutenticado = null;
            this.subMenus.forEach(menu -> menu.setUserAutenticado(null));
            System.out.println("Logout efetuado com sucesso");
            System.out.println(this.userAutenticado);
        } catch (UtilizadorNaoExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() throws UtilizadorNaoExisteException {
        try {
            this.textMenuPrincipal.run();
        } finally {
            if(this.userAutenticado != null) this.facade.logout(this.userAutenticado.getUsername());
        }
    }
}
