import business.IRacingManagerLNFacade;
import business.RacingManagerLNFacade;
import business.campeonatos.Circuito;
import business.campeonatos.GDU;
import business.carros.C1;
import business.carros.C1H;
import business.exceptions.*;
import business.users.Admin;
import business.users.Autenticavel;
import ui.Handler;
import ui.Menu;

import java.util.*;
import java.util.stream.Collectors;

public class RacingManager {

    private final IRacingManagerLNFacade facade;
    private final Menu menuPrincipal;
    private final Menu menuPilotos;
    private final Menu menuCircuitos;
    private final Menu menuCampeonatos;
    private final Menu menuCarros;
    private Autenticavel userAutenticado;
    private final Scanner scanner;

    public RacingManager() {
        this.scanner = new Scanner(System.in);
        this.menuPrincipal = new Menu(this.scanner);
        this.menuPilotos = new Menu(this.scanner);
        this.menuCircuitos = new Menu(this.scanner);
        this.menuCampeonatos = new Menu(this.scanner);
        this.menuCarros = new Menu(this.scanner);
        this.userAutenticado = null;
        this.facade = new RacingManagerLNFacade();
        this.initializeMenus();
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
            System.out.println("Logout efetuado com sucesso");
            System.out.println(this.userAutenticado);
        } catch (UtilizadorNaoExisteException e) {
            System.out.println(e.getMessage());
        }
    }


    private void handleRegistoJogador() {
        System.out.println("Insira o username desejado");
        var username = this.scanner.nextLine();
        System.out.println("Insira a password desejada");
        var password = this.scanner.nextLine();
        System.out.println("Deseja ser premium? S/N");
        var choice = this.scanner.nextLine();
        boolean premium = false;
        if(!choice.equals("S") && !choice.equals("N")) {
            System.out.println("Input inválido");
            return;
        }
        if(choice.equals("S")) {
            premium = true;
        } else {
            premium = false;
        }
        try {
            this.facade.registaJogador(username, password, premium);
            System.out.println("User registado com sucesso");
        } catch (UtilizadorJaExistenteException e) {
            System.out.println(e.getMessage());
        }
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

    private void handleListaCircuitos() {
        var circuitos = this.facade.getCircuitos();
        for(var circuito : circuitos) {
            System.out.println(circuito.imprimeCircuito());
        }
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

    private void handleListaCarros() {
        var carros = this.facade.getCarros();
        for(var carro : carros)
            System.out.println(carro);
    }

    private void handleAddC1(String marca, String modelo, int cilindrada, int potenciaCombustao) {
        System.out.println("Deseja que o carro seja hibrido?S/N");
        String choice = this.scanner.nextLine();
        var potenciaEletrica = -1;
        if(choice.equals("S")) {
            System.out.println("Indique a potencia do motor eletrico do carro.");
            potenciaEletrica = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Insira o valor do PAC(Perfil AeroDinâmico) do carro.(Valor entre 0  e 1)");
            float afinacao = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new C1H(modelo, marca, cilindrada, potenciaCombustao, afinacao, potenciaEletrica));
                System.out.println("Carro adicionado com sucesso.");
            } catch (CarroJaExisteException e) {
                System.out.println(e.getMessage());
            }
        } else if(choice.equals("N")) {
            System.out.println("Insira o valor do PAC(Perfil AeroDinâmico) do carro.(Valor entre 0  e 1)");
            float afinacao = Float.parseFloat(this.scanner.nextLine());
            try {
                this.facade.addCarro(new C1(modelo, marca, cilindrada, potenciaCombustao, afinacao));
                System.out.println("Carro adicionado com sucesso");
            } catch (CarroJaExisteException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void handleAddC2(String marca, String modelo, int cilindrada, int potenciaCombustao) {

    }

    private void handleAddGT(String marca, String modelo, int cilindrada, int potenciaCombustao) {

    }

    private void handleAddSC(String marca, String modelo, int cilindrada, int potenciaCombustao) {

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
            System.out.println("Indique a cilindrada do carro.");
            var cilindrada = Integer.parseInt(this.scanner.nextLine());
            System.out.println("Indique a potencia do motor de combustão do carro.");
            var potencia = Integer.parseInt(this.scanner.nextLine());
            List<Handler> handlers = List.of(
                    () -> this.handleAddC1(marca, modelo, cilindrada, potencia),
                    () -> this.handleAddC2(marca, modelo, cilindrada, potencia),
                    () -> this.handleAddGT(marca, modelo, cilindrada, potencia),
                    () -> this.handleAddSC(marca, modelo, cilindrada, potencia)
            );
            handlers.get(choice - 1).execute();
        } catch(NumberFormatException e) {
            System.out.println("Formato Inválido");
        }
    }

    private void inicializaMenuPilotos() {
        this.menuPilotos.addOption("Lista Pilotos", this::handleListagemPilotos, null);
        this.menuPilotos.addOption("Adicionar Piloto", this::handleAddPiloto, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
    }

    private void inicializaMenuPrincipal() {
        this.menuPrincipal.addOption("Registar-se como jogador", this::handleRegistoJogador, () -> this.userAutenticado == null);
        this.menuPrincipal.addOption("Login", this::handleLogin, () -> this.userAutenticado == null);
        this.menuPrincipal.addOption("Logout", this::handleLogout, () -> this.userAutenticado != null);
        this.menuPrincipal.addOption("Gestão Pilotos", this.menuPilotos::run, null);
        this.menuPrincipal.addOption("Gestão Circuitos", this.menuCircuitos::run, null);
        this.menuPrincipal.addOption("Gestão Campeonatos", this.menuCampeonatos::run, null);
        this.menuPrincipal.addOption("Gestão Carros", this.menuCarros::run, null);
    }

    private void inicializaMenuCircuitos() {
        this.menuCircuitos.addOption("Criar Circuito", this::handleAddCircuito, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
        this.menuCircuitos.addOption("Listar Circuitos", this::handleListaCircuitos, null);
    }

    private void inicializaMenuCampeonatos() {
        this.menuCampeonatos.addOption("Criar Campeonato", this::handleAddCampeonato, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
        this.menuCampeonatos.addOption("Listar Campeonatos", this::handleListaCampeonato, null);
    }

    private void inicializaMenuCarros() {
        this.menuCarros.addOption("Criar Carro", this::handleAddCarro, () -> this.userAutenticado != null && this.userAutenticado instanceof Admin);
        this.menuCarros.addOption("Listar Carros", this::handleListaCarros, null);
    }

    private void initializeMenus() {
        this.inicializaMenuPrincipal();
        this.inicializaMenuPilotos();
        this.inicializaMenuCircuitos();
        this.inicializaMenuCampeonatos();
        this.inicializaMenuCarros();
    }

    public void run() throws UtilizadorNaoExisteException {
        this.menuPrincipal.run();
        if(this.userAutenticado != null) this.facade.logout(this.userAutenticado.getUsername());
    }
}
