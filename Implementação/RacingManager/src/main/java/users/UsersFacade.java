package users;

import data.AdminDAO;
import data.JogadorDAO;
import exceptions.UtilizadorJaExistenteException;
import exceptions.UtilizadorNaoExisteException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsersFacade implements IGestUsers {

    //TODO criar admins DAO
    private final JogadorDAO jogadores;
    private final AdminDAO admins;

    public UsersFacade() {
        this.jogadores = JogadorDAO.getInstance();
        this.admins = AdminDAO.getInstance();
    }

    public UsersFacade(Map<String, JogadorAutenticavel> jogadores, Map<String, Admin> administradores) {
        this.jogadores = JogadorDAO.getInstance();
        this.admins = AdminDAO.getInstance();
        var jogadores_set = jogadores
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.jogadores.putAll(jogadores_set);
        var admins_set = administradores
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.admins.putAll(admins_set);
    }

    public UsersFacade(UsersFacade usersFacade) {
        this.jogadores = usersFacade.jogadores;
        this.admins = usersFacade.admins;
    }

    @Override
    public String getRankingGlobal() {
        return null;
    }

    @Override
    public void atualizaPassword(String username, String password) throws UtilizadorNaoExisteException {
        //TODO verificar se password é válida ou não
        var jogador = this.jogadores.get(username);
        var admin = this.admins.get(username);
        if(jogador == null && admin == null) throw new UtilizadorNaoExisteException("Não existe utilizador com username= " + username);
        if(jogador != null) {
            jogador.setPassword(password);
            this.jogadores.put(jogador.getUsername(), jogador);
        } else {
            admin.setPassword(password);
            this.admins.put(admin.getUsername(), admin);
        }
    }

    @Override
    public void logOut(String username) throws UtilizadorNaoExisteException {
        var jogador = this.jogadores.get(username);
        var admin = this.admins.get(username);
        if(jogador == null && admin == null) throw new UtilizadorNaoExisteException("Não existe utilizador com username " + username);

        if(jogador != null) {
            jogador.logOut();
            this.jogadores.put(username, jogador);
        } else {
            admin.logOut();
            this.admins.put(username, admin);
        }
    }

    @Override
    public List<String> getTiposConta() {
        return List.of("Normal", "Premium");
    }

    @Override
    public void registaJogador(JogadorAutenticavel jogador) throws UtilizadorJaExistenteException {
        if(!this.jogadores.containsKey(jogador.getUsername()))
            this.jogadores.put(jogador.getUsername(), jogador);
        else throw new UtilizadorJaExistenteException("Jogador já existente");
    }

    @Override
    public Autenticavel autenticaUtilizador(String username, String password) throws UtilizadorNaoExisteException {
        var jogador = this.jogadores.get(username);
        var admin = this.admins.get(username);
        if(jogador == null && admin == null) throw new UtilizadorNaoExisteException("Não existe utilizador com username " + username);
        if(jogador != null) {
            if(jogador.login(username, password)) {
                this.jogadores.put(username, jogador);
                return jogador;
            }
        } else {
            if(admin.login(username, password)) {
                this.admins.put(username, admin);
                return admin;
            }
        }
        return null;
    }

    @Override
    public void atualizaPontuacao(Map<String, Integer> pontuacoesJogador, int lobby) {
        for(var entries : pontuacoesJogador.entrySet()) {
            var jogador = this.jogadores.get(entries.getKey());
            if(jogador != null && jogador.estaAutenticado()) {
                jogador.addPontuacao(entries.getValue(), lobby);
            }
        }
    }

    @Override
    public boolean existeUtilizador(String username) {
        return this.jogadores.containsKey(username) && this.admins.containsKey(username);
    }

    @Override
    public boolean isJogadorPremium(String username) {
        var user = this.jogadores.get(username);
        if(user == null) return false;
        return user.isPremium();
    }

    @Override
    public boolean existeJogador(String username) {
        return this.jogadores.containsKey(username);
    }

    @Override
    public boolean jogadorAutenticado(String username) {
        var jogador = this.jogadores.get(username);
        if(jogador == null) return false;
        return jogador.estaAutenticado();
    }
}
