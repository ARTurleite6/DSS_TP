package ui;

import business.IRacingManagerLNFacade;
import business.carros.*;
import business.exceptions.CarroJaExisteException;
import business.exceptions.CilindradaInvalidaException;
import business.users.Admin;
import business.users.Autenticavel;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Scanner;

public class MenuCarros implements SubMenu {
    private final TextMenu textMenuCarros;
    private final Scanner scanner;
    private final IRacingManagerLNFacade facade;
    private Autenticavel userAutenticado;

    public MenuCarros(Scanner scanner, IRacingManagerLNFacade facade, Autenticavel userAutenticado) {
        this.facade = facade;
        this.scanner = scanner;
        this.textMenuCarros = new TextMenu(this.scanner);
        this.userAutenticado = userAutenticado;
        this.textMenuCarros.addOption("Adiciona Carro", this::handleAddCarro, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
        this.textMenuCarros.addOption("Listar Carros", this::handleListaCarros, null);
    }


    private void handleListaCarros() {
        var carros = this.facade.getCarros();
        for(var carro : carros)
            System.out.println(carro);
    }

    private void handleAddC1(String marca, String modelo, int potenciaCombustao) {
        System.out.println("Deseja que o carro seja hibrido?S/N");
        String choice = this.scanner.nextLine();
        if(choice.equals("S")) {
            System.out.println("Indique a potencia do motor eletrico do carro.");
            int potenciaEletrica = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Insira o valor do PAC(Perfil AeroDinâmico) do carro.(Valor entre 0  e 1)");
            float afinacao = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new C1H(modelo, marca, potenciaCombustao, afinacao, potenciaEletrica));
                System.out.println("Carro adicionado com sucesso.");
            } catch (CarroJaExisteException e) {
                System.out.println(e.getMessage());
            }
        } else if(choice.equals("N")) {
            System.out.println("Insira o valor do PAC(Perfil AeroDinâmico) do carro.(Valor entre 0  e 1)");
            float afinacao = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new C1(modelo, marca, potenciaCombustao, afinacao));
                System.out.println("Carro adicionado com sucesso");
            } catch (CarroJaExisteException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void handleAddC2(String marca, String modelo, int cilindrada, int potenciaCombustao) {
        System.out.println("Deseja que o carro seja hibrido?S/N");
        String choice = this.scanner.nextLine();
        var potenciaEletrica = -1;
        if(choice.equals("S")) {
            System.out.println("Indique a potencia do motor eletrico do carro.");
            potenciaEletrica = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Insira o valor do PAC(Perfil AeroDinâmico) do carro.(Valor entre 0  e 1)");
            float afinacao = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new C2H(modelo, marca, cilindrada, potenciaCombustao, afinacao, potenciaEletrica));
                System.out.println("Carro adicionado com sucesso.");
            } catch (CarroJaExisteException | CilindradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } else if(choice.equals("N")) {
            System.out.println("Insira o valor do PAC(Perfil AeroDinâmico) do carro.(Valor entre 0  e 1)");
            float afinacao = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new C2(modelo, marca, cilindrada, potenciaCombustao, afinacao));
                System.out.println("Carro adicionado com sucesso");
            } catch (CarroJaExisteException e) {
                System.out.println(e.getMessage());
            } catch (CilindradaInvalidaException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void handleAddGT(String marca, String modelo, int cilindrada, int potenciaCombustao) {
        System.out.println("Deseja que o carro seja Híbrido? S/N");
        String choice = this.scanner.nextLine();
        if(choice.equals("S")) {
            System.out.println("Indique a potencia do motor eletrico do carro");
            var potenciaEletrica = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Insira o fator desgaste do carro. (Quanto o carro se irá desgastar no decorrer das voltas)(Valor entre 0 e 1)");
            float fatorDesgaste = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new GTH(marca, modelo, cilindrada, potenciaCombustao, fatorDesgaste, potenciaEletrica));
            } catch (CarroJaExisteException | CilindradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } else if(choice.equals("N")) {
            System.out.println("Insira o fator desgaste do carro. (Quanto o carro se irá desgastar no decorrer das voltas)(Valor entre 0 e 1)");
            float fatorDesgaste = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new GT(marca, modelo, cilindrada, potenciaCombustao, fatorDesgaste));
            } catch (CarroJaExisteException | CilindradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Opção inválida");
        }
    }

    private void handleAddSC(String marca, String modelo, int potenciaCombustao) {
        try {
            this.facade.addCarro(new SC(marca, modelo, potenciaCombustao));
        } catch (CarroJaExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleAddCarro() {
        try {
            System.out.println("Qual a categoria do carro que pretende adicionar");
            System.out.println("1- C1");
            System.out.println("2- C2");
            System.out.println("3- GT");
            System.out.println("4- SC");
            int choice = Integer.parseInt(this.scanner.nextLine());
            if(choice - 1 < 0 || choice - 1 >= 4) {
                System.out.println("Escolha inválida");
                return;
            }
            System.out.println("Indique a marca do carro.");
            var marca = this.scanner.nextLine();
            System.out.println("Indique a modelo do carro.");
            var modelo = this.scanner.nextLine();
            int cilindrada = -1;
            if(choice != 1 && choice != 4) {
                System.out.println("Indique a cilindrada do carro.");
                cilindrada = Integer.parseInt(this.scanner.nextLine());
            }
            System.out.println("Indique a potencia do motor de combustão do carro.");
            var potencia = Integer.parseInt(this.scanner.nextLine());
            int finalCilindrada = cilindrada;
            List<Handler> handlers = List.of(
                    () -> this.handleAddC1(marca, modelo, potencia),
                    () -> this.handleAddC2(marca, modelo, finalCilindrada, potencia),
                    () -> this.handleAddGT(marca, modelo, finalCilindrada, potencia),
                    () -> this.handleAddSC(marca, modelo, potencia)
            );
            handlers.get(choice - 1).execute();
        } catch(NumberFormatException e) {
            System.out.println("Formato Inválido");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setUserAutenticado(@Nullable Autenticavel user) {
        this.userAutenticado = user;
    }

    @Override
    public void run() {
        this.textMenuCarros.run();
    }
}
