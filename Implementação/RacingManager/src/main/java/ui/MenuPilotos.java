package ui;

import business.IRacingManagerLNFacade;
import business.exceptions.PilotoInexistenteException;
import business.users.Admin;
import business.users.Autenticavel;
import org.jetbrains.annotations.Nullable;

import java.util.Scanner;

public class MenuPilotos implements SubMenu {

    private final TextMenu textMenuPilotos;
    private final Scanner scanner;
    private final IRacingManagerLNFacade facade;
    private Autenticavel userAutenticado;

    public MenuPilotos(Scanner scanner, IRacingManagerLNFacade facade, Autenticavel userAutenticado) {
        this.facade = facade;
        this.scanner = scanner;
        this.textMenuPilotos = new TextMenu(this.scanner);
        this.userAutenticado = userAutenticado;
        this.textMenuPilotos.addOption("Lista Pilotos", this::handleListagemPilotos, null);
        this.textMenuPilotos.addOption("Adicionar Piloto", this::handleAddPiloto, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
    }

    private void handleAddPiloto() {
        try {
            System.out.println("Insira o nome do piloto desejado");
            var nomePiloto = this.scanner.nextLine();
            System.out.println("Insira o CTS(Chuva vs. Tempo Seco) do piloto");
            var cts = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Insira o SVA(Segurança vs. Agressividade) do piloto");
            var sva = Integer.parseInt(this.scanner.nextLine());
            this.facade.addPiloto(nomePiloto, cts, sva);
            System.out.println("Piloto inserido com sucesso.");
        } catch(NumberFormatException e) {
            System.out.println("Valor inválido, terá de ser um valor decimal ou inteiro");
        } catch (PilotoInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleListagemPilotos() {
        var pilotos = this.facade.getPilotos();
        for(var piloto : pilotos) {
            System.out.println("Piloto: Nome = " + piloto.getNome() + ", sva = " + piloto.getSva() + ", cts = " + piloto.getCts());
        }
        System.out.println("Prima qualquer tecla para avançar");
        this.scanner.nextLine();
    }
    @Override
    public void setUserAutenticado(@Nullable Autenticavel user) {
        this.userAutenticado = user;
    }

    @Override
    public void run() {
        this.textMenuPilotos.run();
    }
}
