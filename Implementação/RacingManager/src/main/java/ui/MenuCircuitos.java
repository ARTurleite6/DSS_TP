package ui;

import business.IRacingManagerLNFacade;
import business.campeonatos.GDU;
import business.exceptions.CircuitoJaExistenteException;
import business.users.Admin;
import business.users.Autenticavel;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCircuitos implements SubMenu {
    private final TextMenu textMenuCircuitos;
    private final Scanner scanner;
    private final IRacingManagerLNFacade facade;
    private Autenticavel userAutenticado;

    public MenuCircuitos(Scanner scanner, IRacingManagerLNFacade facade, Autenticavel userAutenticado) {
        this.facade = facade;
        this.scanner = scanner;
        this.textMenuCircuitos = new TextMenu(this.scanner);
        this.userAutenticado = userAutenticado;
        this.textMenuCircuitos.addOption("Criar Circuito", this::handleAddCircuito, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
        this.textMenuCircuitos.addOption("Listar Circuitos", this::handleListaCircuitos, null);
    }

    private void handleAddCircuito() {
        try {
            System.out.println("Insira o nome desejado para o circuito");
            var nomeCircuito = this.scanner.nextLine();
            System.out.println("Insira a distancia do circuito");
            var distancia = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Insira o numero de curvas que o circuito terá");
            var numeroCurvas = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Insira o numero de chicanes que o circuito terá");
            var numeroChicanes = Integer.parseInt(this.scanner.nextLine());
            var numeroRetas = numeroCurvas + numeroChicanes;
            List<GDU> chicanes = new ArrayList<>(numeroChicanes);
            List<GDU> curvas = new ArrayList<>(numeroCurvas);
            List<GDU> retas = new ArrayList<>(numeroRetas);
            for(int i = 0; i < numeroChicanes; ++i) {
                chicanes.add(GDU.Dificil);
            }
            System.out.println("Escolha de dificuldades das retas");
            this.populaArraySeccao(retas, numeroRetas);
            System.out.println("Escolha de dificuldades das curvas");
            this.populaArraySeccao(curvas, numeroCurvas);
            System.out.println("Insira o numero de voltas que o circuito terá");
            var numeroVoltas = Integer.parseInt(this.scanner.nextLine());
            this.facade.addCircuito(nomeCircuito, distancia, retas, curvas, chicanes, numeroVoltas);
        } catch(NumberFormatException e) {
            System.out.println("Formato inválido");
        } catch (CircuitoJaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void populaArraySeccao(List<GDU> seccoes, int numeroSeccoes) {
        for (int i = 0; i < numeroSeccoes; i++) {
            System.out.println("Insira a dificuldade a seccao numero " + (i + 1) + ", 1(Possivel)/2(Impossivel)3/(Dificil)");
            boolean valida = true;
            do {
                var escolha = Integer.parseInt(this.scanner.nextLine());
                switch (escolha) {
                    case 1 -> {
                        seccoes.add(GDU.Possivel);
                        valida = true;
                    }
                    case 2 -> {
                        seccoes.add(GDU.Impossivel);
                        valida = true;
                    }
                    case 3 -> {
                        seccoes.add(GDU.Dificil);
                        valida = true;
                    }
                    default -> {
                        System.out.println("Opcão inválida, tente novamente");
                        valida = false;
                    }
                }
            } while(!valida);
        }
    }

    private void handleListaCircuitos() {
        var circuitos = this.facade.getCircuitos();
        for(var circuito : circuitos) {
            System.out.println(circuito.imprimeCircuito());
        }
    }

    @Override
    public void setUserAutenticado(@Nullable Autenticavel user) {
        this.userAutenticado = user;
    }

    @Override
    public void run() {
        this.textMenuCircuitos.run();
    }
}
