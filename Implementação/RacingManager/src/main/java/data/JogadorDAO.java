package data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import business.users.Autenticavel;
import business.users.JogadorAutenticavel;

public class JogadorDAO implements Map<String, JogadorAutenticavel> {

  private static JogadorDAO dao = null;

  private JogadorDAO() {}

  public static JogadorDAO getInstance() {
    if (dao == null)
      dao = new JogadorDAO();
    return dao;
  }

  @Override
  public int size() {
    try (var conn =
             DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
         var st = conn.createStatement()) {
      var query = "SELECT COUNT(*) FROM Jogador";
      try(var rs = st.executeQuery(query)) {
        if (rs.next()) {
          return rs.getInt(1);
        }

        return 0;
      }
    } catch (SQLException exception) {
      throw new RuntimeException();
    }
  }

  @Override
  public boolean isEmpty() {
    return this.size() == 0;
  }

  @Override
  public boolean containsKey(Object key) {
    return this.get((String)key) != null;
  }

  @Override
  public boolean containsValue(Object value) {
    Autenticavel auth = (Autenticavel) value;
    return this.get(auth.getUsername()).equals(value);
  }

  @Override
  public JogadorAutenticavel get(Object key) {
    try (
            var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var st1 = conn.prepareStatement("SELECT * FROM Utilizador WHERE username = ?");
            var st2 = conn.prepareStatement("SELECT * FROM Jogador WHERE username = ?");
    ){
      st1.setString(1, (String)key);
      try (var rs1 = st1.executeQuery()) {
        if (!rs1.next()) return null;
        var tipo = rs1.getString(4);
        if (tipo.equals("Admin")) return null;
        st2.setString(1, (String) key);
        var rs2 = st2.executeQuery();
        if (!rs2.next()) return null;

        var username = rs1.getString(1);
        var password = rs1.getString(2);
        var autenticado = rs1.getBoolean(3);
        var pontuacaoGlobal = rs2.getInt(2);
        var premium = rs2.getBoolean(3);
        return new JogadorAutenticavel(username, password, autenticado, pontuacaoGlobal, premium);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public JogadorAutenticavel put(String key, JogadorAutenticavel value) {
    var jogador = this.get(key);
    try (var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)) {
      try (

              var st1 = conn.prepareStatement("INSERT INTO Utilizador(username, password, autenticado, tipo) VALUES(?, ?, ?, ?)");
              var st2 = conn.prepareStatement("INSERT INTO Jogador(username, pontuacao_global, premium) VALUES(?, ?, ?)");
              var st3 = conn.prepareStatement("DELETE FROM Jogador WHERE username = ?");
              var st4 = conn.prepareStatement("DELETE FROM Utilizador WHERE username = ?")
      ) {
        conn.setAutoCommit(false);
        if (jogador != null) {
          st3.setString(1, key);
          st4.setString(1, key);
          st3.executeUpdate();
          st4.executeUpdate();
        }
        st1.setString(1, key);
        st1.setString(2, value.getPassword());
        st1.setBoolean(3, value.estaAutenticado());
        st1.setString(4, "Jogador");
        st2.setString(1, key);
        st2.setInt(2, value.getPontuacaoGlobal());
        st2.setBoolean(3, value.isPremium());
        st1.executeUpdate();
        st2.executeUpdate();
        conn.commit();
        return jogador;
      } catch (SQLException e) {
        conn.rollback();
        throw new RuntimeException(e);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public JogadorAutenticavel remove(Object key) {
    var user = this.get(key);
    if(user == null) return null;

    try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)) {
      try (

              var pt1 = conn.prepareStatement("DELETE FROM Utilizador WHERE Utilizador.username = ?");
              var pt2 = conn.prepareStatement("DELETE FROM Jogador WHERE username = ?");
              var pt4 = conn.prepareStatement("DELETE FROM JogadorLobby where jogador = ?")
      ) {
        conn.setAutoCommit(false);
        pt1.setString(1, (String) key);
        pt2.setString(1, (String) key);
        pt4.setString(1, (String) key);
        pt4.executeUpdate();
        pt2.executeUpdate();
        pt1.executeUpdate();
        conn.commit();
        return user;
      } catch (SQLException exception) {
        conn.rollback();
        System.out.println(exception.getMessage());
        throw new RuntimeException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void putAll(Map<? extends String, ? extends JogadorAutenticavel> m) {
    for(var entry : m.entrySet()) {
      this.put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)) {
      try (

              var st = conn.createStatement()
      ) {
        conn.setAutoCommit(false);
        st.executeUpdate("DELETE FROM Jogador");
        st.executeUpdate("DELETE FROM Utilizador");
        st.executeUpdate("DELETE FROM JogadorLobby");
        conn.commit();
      } catch (SQLException e) {
        conn.commit();
        throw new RuntimeException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Set<String> keySet() {
    try(
      var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
      var st = conn.createStatement()
    ){
      try(var rs = st.executeQuery("SELECT username FROM Jogador")) {
        var res = new HashSet<String>();
        while (rs.next()) {
          var nome = rs.getString(1);
          res.add(nome);
        }
        return res;
      }
    } catch (SQLException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public Collection<JogadorAutenticavel> values() {
    return this.keySet().stream().map(this::get).collect(Collectors.toList());
  }

  @Override
  public Set<Entry<String, JogadorAutenticavel>> entrySet() {
    return this.keySet().stream().map(nome -> new AbstractMap.SimpleEntry<>(nome, this.get(nome))).collect(Collectors.toSet());
  }
}
