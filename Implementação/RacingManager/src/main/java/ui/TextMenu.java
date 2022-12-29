package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextMenu {
    private final List<String> opcoes;
    private final List<Handler> handlers;
    private final List<PreCondition> preConditions;
    private final Scanner scan;

    public TextMenu(Scanner scan) {
        this.opcoes = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.preConditions = new ArrayList<>();
        this.scan = scan;
    }

    public void addOption(String option, Handler handler, PreCondition preCondition) {
        this.opcoes.add(option);
        if(handler != null) this.handlers.add(handler);
        else this.handlers.add(() -> System.out.println("Não foi implementado ainda"));
        if(preCondition == null) this.preConditions.add(() -> true);
        else this.preConditions.add(preCondition);
    }

    public void imprimeOpcoes() {
        for(int i = 0; i < this.opcoes.size(); ++i) {
            System.out.println((i + 1) + "- " + this.opcoes.get(i));
        }
        System.out.println("0- Sair");
    }

    public void run() {
        int choice = -1;
        while(choice != 0) {
            this.imprimeOpcoes();
            try {
                String opcao = this.scan.nextLine();
                choice = Integer.parseInt(opcao);
                if(choice != 0 && choice - 1 < 0 || choice - 1 >= this.opcoes.size()) {
                    System.out.println("Opcao escolhida é invalida, escolha um número entre 1 " + "e " + this.opcoes.size());
                    choice = -1;
                }
                else {
                    if(choice != 0) {
                        if (this.preConditions.get(choice - 1).evaluate()) {
                            this.handlers.get(choice - 1).execute();
                        } else {
                            System.out.println("Não pode efetuar esta opcao de momento.");
                        }
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println("Formato inválido, insira um numero");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
