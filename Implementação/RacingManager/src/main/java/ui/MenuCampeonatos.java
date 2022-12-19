package ui;

import business.IRacingManagerLNFacade;
import business.campeonatos.*;
import business.carros.ModoMotor;
import business.carros.TipoPneu;
import business.exceptions.*;
import business.users.Admin;
import business.users.Autenticavel;
import business.users.JogadorAutenticavel;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MenuCampeonatos implements SubMenu {
    private final TextMenu textMenuCampeonato;
    private final Scanner scanner;
    private final IRacingManagerLNFacade facade;
    private Autenticavel userAutenticado;

    public MenuCampeonatos(Scanner scanner, IRacingManagerLNFacade facade, Autenticavel userAutenticado) {
        this.facade = facade;
        this.scanner = scanner;
        this.textMenuCampeonato = new TextMenu(this.scanner);
        this.userAutenticado = userAutenticado;
        this.textMenuCampeonato.addOption("Criar Campeonato", this::handleAddCampeonato, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
        this.textMenuCampeonato.addOption("Listar Campeonatos", this::handleListaCampeonato, null);
        this.textMenuCampeonato.addOption("Jogar Campeonato", this::handleJogarCampeonato, () -> this.userAutenticado != null && this.userAutenticado instanceof JogadorAutenticavel);
    }

    public void setUserAutenticado(@Nullable Autenticavel userAutenticado) {
        this.userAutenticado = userAutenticado;
    }

    public void run() {
        this.textMenuCampeonato.run();
    }

    private void handleAddCampeonato() {
        try {
            System.out.println("Insira o nome desejado para o campeonato");
            var nomeCampeonato = this.scanner.nextLine();

            var circuitos = this.facade.getCircuitos();
            circuitos.forEach(circuito -> System.out.println(circuito.imprimeCircuito()));
            var circuitosNomes = circuitos.stream().map(Circuito::getNomeCircuito).collect(Collectors.toSet());

            Set<String> circuitosCampeonato = new TreeSet<>();

            String choice = "";
            System.out.println("Insira os nomes dos circuitos que deseja adicionar, Prima Enter se desejar terminar a escolha");
            do {
                choice = this.scanner.nextLine();
                if(!choice.equals("")) {
                    if(circuitosNomes.contains(choice)) {
                        circuitosCampeonato.add(choice);
                        System.out.println("Circuito " + choice + ", adicionado ao campeonato");
                    } else {
                        System.out.println("Nome de circuito inválido, tente novamente ou Prima Enter para terminar");
                    }
                }
            } while(!choice.equals(""));
            this.facade.addCampeonato(nomeCampeonato, circuitosCampeonato);
        } catch (CircuitoNaoExisteException | CampeonatoJaExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleListaCampeonato() {
        var campeonatos = this.facade.getCampeonatos();
        for(var campeonato : campeonatos) {
            System.out.println(campeonato.imprimeCampeonato());
        }
    }

    private int inscreveJogadoresCampeonato(Lobby lobby) throws NumberFormatException {
        System.out.println("Quantos jogadores irão jogar, incluindo o criador do lobby?");
        int numeroJogadores = Integer.parseInt(this.scanner.nextLine());
        var carrosDisponiveis = this.facade.getCarros();
        var pilotos = this.facade.getPilotos();
        int i = 0;
        while(i < numeroJogadores) {
            try {
                var username = "jogador" + i;
                System.out.println("\n-----------------------------");
                if (i == 0) username = this.userAutenticado.getUsername();
                System.out.println("Jogador, username = " + username);
                carrosDisponiveis.forEach(System.out::println);
                System.out.println("Que carro deseja utilizar na corrida");
                var modelo = this.scanner.nextLine();
                pilotos.forEach(Piloto::imprimePiloto);
                System.out.println("Que piloto deseja utilizar na corrida");
                var pilotoEscolhido = this.scanner.nextLine();
                this.facade.inscreveJogador(username, modelo, pilotoEscolhido);
                ++i;
            } catch (NaoExisteLobbyAbertoException | PilotoInexistenteException | CarroInexistenteException |
                     LobbyAtivoInexistenteException | UsernameInvalidoException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("\n-----------------------------");
        }
        System.out.println(lobby);
        System.out.println("\n-----------------------------");
        return numeroJogadores;
    }

    private void handleAfinacoesVeiculos(int numeroJogadores) throws LobbyAtivoInexistenteException {
        Set<String> pilotosAfinacaoAlterada = new HashSet<>();
        int i = 0;
        while (i < numeroJogadores) {
            try {
                System.out.println("\n------------------------------------");
                var username = "jogador" + i;
                if (i == 0) username = this.userAutenticado.getUsername();
                System.out.println("Jogador: " + username);
                System.out.println("Deseja alterar a afinacao do seu carro?S/N");
                String choice = this.scanner.nextLine();
                if (choice.equals("S")) {
                    System.out.println("Indique o valor(Entre 0 e 1) para a afinacao do seu veiculo.");
                    float afinacao = Float.parseFloat(this.scanner.nextLine());
                    System.out.println("Indique o nome do piloto pelo qual se encontra a jogar");
                    String piloto = this.scanner.nextLine();
                    if (pilotosAfinacaoAlterada.contains(piloto))
                        throw new NumberFormatException("Piloto que escolheste já teve a afinacao alterada");
                    this.facade.alteraAfinacao(piloto, afinacao);
                    pilotosAfinacaoAlterada.add(piloto);
                }
                ++i;
            } catch (NumberFormatException | CarroNaoAfinavel | PilotoInexistenteException |
                     MaximoAfinacoesExceptions e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("\n------------------------------------");
    }

    private void handleJogarCampeonato() {
        var campeonatos = this.facade.getCampeonatos();
        campeonatos.forEach(Campeonato::imprimeCampeonato);
        System.out.println("Escolhe o campeonato que deseja jogar");
        String campeonatoEscolhido = this.scanner.nextLine();
        try {
            Lobby lobby = this.facade.criaLobby(campeonatoEscolhido, this.userAutenticado.getUsername());
            System.out.println(lobby);
            System.out.println("Lobby criado com sucesso");
            var circuitosCampeonato = this.facade.getCircuitosCampeonato(campeonatoEscolhido);
            circuitosCampeonato.forEach(Circuito::imprimeCircuito);
            int numeroJogadores = this.inscreveJogadoresCampeonato(lobby);
            Corrida corrida = this.facade.getProxCorrida();
            System.out.println("A corrida terá situacao meteorológica de " + (corrida.estaChover() ? "chuva" : "tempo seco"));
            System.out.println(corrida.getCircuito().imprimeCircuito());
            this.handleAfinacoesVeiculos(numeroJogadores);
            this.handleConfiguracoesVeiculo(numeroJogadores);
        } catch (CampeonatoNaoExisteException | CircuitoNaoExisteException | UtilizadorNaoExisteException |
                 CriarLobbySemAutenticacaoException | NumberFormatException | LobbyAtivoInexistenteException |
                 NaoExistemMaisCorridas e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleConfiguracoesVeiculo(int numeroJogadores) {
        int i = 0;
        Set<String> pilotosEscolhidos = new TreeSet<>();
        while(i < numeroJogadores) {
            System.out.println("\n------------------------------------");
            String username = "jogador" + i;
            if(i == 0) username = this.userAutenticado.getUsername();
            System.out.println("Jogador: " + username);
            System.out.println("Escolha um modo de motor para o seu veículo:");
            System.out.println("    " + this.facade.getModosMotor());
            ModoMotor modoMotor = ModoMotor.fromString(this.scanner.nextLine());
            if(modoMotor == null) {
                System.out.println("Valor para modo de motor inválido");
                continue;
            }
            System.out.println("Escolha um tipo de pneus para o seu veículo:");
            System.out.println("    " + this.facade.getTiposPneus());
            TipoPneu tipoPneu = TipoPneu.fromString(this.scanner.nextLine());
            if(tipoPneu == null) {
                System.out.println("Valor para tipo de pneu inválido.");
                continue;
            }

            System.out.println("Indique o nome do seu piloto no lobby");
            String nomePiloto = this.scanner.nextLine();
            if(pilotosEscolhidos.contains(nomePiloto)) {
                System.out.println("Piloto já foi escolhido");
                continue;
            }
            try {
                this.facade.addConfiguracao(nomePiloto, modoMotor, tipoPneu);
                pilotosEscolhidos.add(nomePiloto);
                ++i;
            } catch (LobbyAtivoInexistenteException | PilotoInexistenteException e) {
                System.out.println(e.getMessage());
            }

        }
        System.out.println("\n------------------------------------");
    }


}
