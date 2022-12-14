package users;

import data.JogadorDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsersFacade implements IGestUsers {

    //TODO criar admins DAO
    private JogadorDAO jogadores;

    public UsersFacade() {
        this.jogadores = JogadorDAO.getInstance();
    }

    public UsersFacade(Map<String, JogadorAutenticavel> jogadores) {
        this.jogadores = JogadorDAO.getInstance();
        var jogadores_set = jogadores.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.jogadores.putAll(jogadores_set);
    }

    public UsersFacade(UsersFacade usersFacade) {
        this.jogadores = usersFacade.jogadores;
    }

    @Override
    public String getRankingGlobal() {
        return null;
    }

    @Override
    public boolean atualizaPassword(String username, String password) {
        //TODO verificar se utilizador existe ou não
        //TODO verificar se password é válida ou não
        var user = this.jogadores.get(password);
        if(user == null) return false;
        user.setPassword(password);
        this.jogadores.put(user.getUsername(), user);
        return true;
    }

    @Override
    public void logOut(String username) {
        var user = this.jogadores.get(username);
        if(user != null) {
            user.logOut();
            this.jogadores.put(user.getUsername(), user);
        }
    }

    @Override
    public List<String> getTiposConta() {
        return List.of("Normal", "Premium");
    }

    @Override
    public void registaJogador(JogadorAutenticavel jogador) {
        if(!this.jogadores.containsKey(jogador.getUsername()))
            this.jogadores.put(jogador.getUsername(), jogador);
    }

    @Override
    public Autenticavel autenticaUtilizador(String username, String password) {
        var user = this.jogadores.get(username);
        if(user == null) return null;
        else if(user.login(username, password)) {
                this.jogadores.put(username, user);
                return user;
            }
        else return null;
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
        return this.jogadores.containsValue(username);
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
