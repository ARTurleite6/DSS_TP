package business.users;

import data.AdminDAO;
import data.JogadorDAO;
import business.exceptions.UtilizadorJaExistenteException;
import business.exceptions.UtilizadorNaoExisteException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class UsersFacade implements IGestUsers {

    private final JogadorDAO jogadores;
    private final AdminDAO admins;

    public UsersFacade() {
        this.jogadores = JogadorDAO.getInstance();
        this.admins = AdminDAO.getInstance();
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
        if(jogador != null && jogador.estaAutenticado()) {
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
    public void registaJogador(@NotNull JogadorAutenticavel jogador) throws UtilizadorJaExistenteException {
        if(!this.jogadores.containsKey(jogador.getUsername()) && !this.admins.containsKey(jogador.getUsername()))
            this.jogadores.put(jogador.getUsername(), jogador);
        else throw new UtilizadorJaExistenteException("Jogador já existente");
    }

    @Override
    public Autenticavel autenticaUtilizador(String username, String password) throws UtilizadorNaoExisteException {
        var jogador = this.jogadores.get(username);
        var admin = this.admins.get(username);
        System.out.println("Jogador = " + jogador);
        if(jogador == null && admin == null) throw new UtilizadorNaoExisteException("Não existe utilizador com username " + username);
        if(jogador != null) {
            if(jogador.login(username, password)) {
                System.out.println(jogador);
                this.jogadores.put(username, jogador);
                return jogador.clone();
            }
        } else {
            System.out.println("Admin = " + admin);
            if(admin.login(username, password)) {
                System.out.println(admin);
                this.admins.put(username, admin);
                return admin.clone();
            }
        }
        return null;
    }

    @Override
    public void atualizaPontuacoes(@NotNull Map<String, Integer> pontuacoesJogador) {
        for(var entries : pontuacoesJogador.entrySet()) {
            var jogador = this.jogadores.get(entries.getKey());
            if(jogador != null) {
                jogador.addPontuacao(entries.getValue());
                this.jogadores.put(jogador.getUsername(), jogador);
            }
        }
    }

    @Override
    public boolean existeUtilizador(String username) {
        return this.jogadores.containsKey(username) && this.admins.containsKey(username);
    }

    @Override
    public boolean existeJogador(String username) {
        return this.jogadores.containsKey(username);
    }

    public JogadorAutenticavel getJogador(String username) throws UtilizadorNaoExisteException {
        var jogador = this.jogadores.get(username);
        if(jogador == null) throw new UtilizadorNaoExisteException("Jogador não existe com username " + username);
        return jogador.clone();
    }
}
