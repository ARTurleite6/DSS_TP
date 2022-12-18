package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<String> opcoes;
    private List<Handler> handlers;
    private List<PreCondition> preConditions;
    private Scanner scan;

    public Menu(Scanner scan) {
        this.opcoes = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.preConditions = new ArrayList<>();
        this.scan = scan;
    }

    public Menu(List<String> opcoes, List<Handler> handlers, List<PreCondition> preConditions, Scanner scan) {
        this.opcoes = new ArrayList<>(opcoes);
        this.handlers = new ArrayList<>(handlers);
        this.preConditions = new ArrayList<>(preConditions);
        this.scan = scan;
    }

    public Menu(Menu m) {
        this.opcoes = new ArrayList<>(m.opcoes);
        this.handlers = new ArrayList<>(m.handlers);
        this.preConditions = new ArrayList<>(m.preConditions);
        this.scan = m.scan;
    }

    public void addOption(String option, Handler handler, PreCondition preCondition) {
        this.opcoes.add(option);
        if(handler != null) this.handlers.add(handler);
        else this.handlers.add(() -> System.out.println("Não foi implementado ainda"));
        if(preCondition == null) this.preConditions.add(() -> true);
        else this.preConditions.add(preCondition);
    }

    public void setHandler(int i, Handler handler) {
        if(i < 0 || i >= this.opcoes.size()) throw new IndexOutOfBoundsException();
        this.handlers.set(i, handler);
    }

    public void setPreCondition(int i, PreCondition preCondition) {
        if(i < 0 || i >= this.opcoes.size()) throw new IndexOutOfBoundsException();
        this.preConditions.set(i, preCondition);
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
            }
        }
    }
}
