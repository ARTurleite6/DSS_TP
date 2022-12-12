package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import users.Admin;
import users.Autenticavel;
import users.JogadorAutenticavel;

public class UserDAO implements Map<String, Autenticavel> {

  private static UserDAO dao = null;

  private UserDAO() {}

  public static UserDAO getInstance() {
    if (dao == null)
      dao = new UserDAO();
    return dao;
  }

  @Override
  public int size() {
    try (var conn =
             DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
         var st = conn.createStatement();) {
      var query = "SELECT COUNT(*) FROM Utilizador";
      var rs = st.executeQuery(query);
      if (rs.next()) {
        return rs.getInt(1);
      }
      return 0;
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
  public Autenticavel get(Object key) {
    try (var conn =
             DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
         var pt1 = conn.prepareStatement(
             "SELECT * FROM Utilizador WHERE Utilizador.username = ?");
         var pt2 = conn.prepareStatement(
             "SELECT * FROM Jogador WHERE Jogador.username = ?");
         var pt3 = conn.prepareStatement(
             "SELECT lobby FROM JogadorLobby WHERE Jogador.username = ?");) {
      pt1.setString(1, (String)key);
      var rs = pt1.executeQuery();
      if (!rs.next())
        return null;

      var username = rs.getString(1);
      var password = rs.getString(2);
      var autenticado = rs.getBoolean(3);
      var tipo = rs.getString(4);
      if (tipo.equals("Admin")) {
        return new Admin(username, password, autenticado);
      } else {
        pt2.setString(1, (String)key);
        var rs1 = pt2.executeQuery();
        if (!rs1.next())
          return null;
        var pontuacao_global = rs.getInt(2);
        var premium = rs.getBoolean(3);
        Set<Integer> lobbiesParticipados = new TreeSet<>();
        pt3.setString(1, (String)key);
        var rs2 = pt3.executeQuery();
        while (rs2.next()) {
          lobbiesParticipados.add(rs2.getInt(1));
        }
        return new JogadorAutenticavel(username, password, autenticado,
                                       pontuacao_global, premium,
                                       lobbiesParticipados);
      }
    } catch (SQLException exc) {
      throw new RuntimeException();
    }
  }

  @Override
  public Autenticavel put(String key, Autenticavel value) {
    var user = this.get(key);
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    if (user == null) {
      try (
        var pt1 = conn.prepareStatement(
            "INSERT INTO Utilizador(username, password, autenticado, tipo) VALUES(?, ?, ?, ?)");
        var pt2 =
            conn.prepareStatement("INSERT INTO ADMINISTRADOR(nome) VALUES(?)");
        var pt3 = conn.prepareStatement(
            "INSERT INTO JOGADOR(username, pontuacao_global, premium) VALUES(?, ?, ?)");) {
        var tipo = value.getClass().getSimpleName();
        conn.setAutoCommit(false);
        pt1.setString(1, key);
        pt1.setString(2, value.getPassword());
        pt1.setBoolean(3, value.estaAutenticado());
        pt1.setString(4, tipo);
        pt1.executeUpdate();
        if (tipo.equals("Admin")) {
          pt2.setString(1, key);
          pt2.executeUpdate();
        } else {
          var jogador = (JogadorAutenticavel)value;
          pt3.setString(1, key);
          pt3.setInt(2, jogador.getPontuacaoGlobal());
          pt3.setBoolean(3, jogador.isPremium());
          pt3.executeUpdate();
        }
        return null;
      } catch (SQLException e) {
        if (conn != null) {
          try {
            conn.rollback();
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }
        }
        throw new RuntimeException();
      } finally {
        if (conn != null) {
          try {
            conn.close();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
      }
    } else {
      try (
        var pt1 = conn.prepareStatement(
            "UPDATE FROM Utilizador SET password = ?, autenticado = ?, tipo = ? WHERE username = ?");
        var pt2 =
            conn.prepareStatement("DELETE FROM Jogador WHERE username = ?");
        var pt3 = conn.prepareStatement(
            "DELETE FROM Administrador WHERE username = ?");
        var pt4 = conn.prepareStatement(
            "INSERT INTO Jogador(username, pontuacao_global, premium) VALUES(?, ?, ?)");
        var pt5 = conn.prepareStatement(
            "INSERT INTO Administrador(username) VALUES(?)");
      ) {

        var tipo = value.getClass().getSimpleName();
        conn.setAutoCommit(false);
        pt1.setString(1, value.getPassword());
        pt1.setBoolean(2, value.estaAutenticado());
        pt1.setString(3, value.getClass().getSimpleName());
        pt1.setString(4, key);
        pt1.executeUpdate();

        pt2.setString(1, key);
        pt2.executeUpdate();
        pt3.setString(1, key);
        pt3.executeUpdate();

        if(tipo.equals("Admin")) {
          pt5.setString(1, key);
          pt5.executeUpdate();
        } else {
          var jogador = (JogadorAutenticavel)value;
          pt4.setString(1, jogador.getUsername());
          pt4.setInt(2, jogador.getPontuacaoGlobal());
          pt4.setBoolean(3, jogador.isPremium());
          pt4.executeUpdate();
        }

        conn.commit();
        return user;
      } catch (SQLException e) {
        try {
          if (conn != null)
            conn.rollback();
        } catch (SQLException e2) {
          throw new RuntimeException();
        }
      } finally {
        try {
          if (conn != null)
            conn.close();
        } catch (SQLException e3) {
          throw new RuntimeException();
        }
      }
    }
    return null;
  }

  @Override
  public Autenticavel remove(Object key) {
    var user = this.get(key);
    if(user == null) return null;

    try(
            var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var pt1 = conn.prepareStatement("DELETE FROM Utilizador WHERE Utilizador.username = ?");
            var pt2 = conn.prepareStatement("DELETE FROM Jogador WHERE Utilizador.username = ?");
            var pt3 = conn.prepareStatement("DELETE FROM Administrador WHERE Utilizador.username = ?");
            ) {
      pt1.setString(1, (String)key);
      pt2.setString(1, (String)key);
      pt3.setString(1, (String)key);
      pt1.executeUpdate();
      pt2.executeUpdate();
      pt3.executeUpdate();
      return user;
    } catch(SQLException exception) {
      throw new RuntimeException();
    }
  }

  @Override
  public void putAll(Map<? extends String, ? extends Autenticavel> m) {
    for(var entry : m.entrySet()) {
      this.put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    try(
            var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var st = conn.createStatement();
            ) {
      st.executeQuery("DELETE FROM Utilizador");
      st.executeQuery("DELETE FROM Administrador");
      st.executeQuery("DELETE FROM Jogador");
    }  catch (SQLException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public Set<String> keySet() {
    try(
      var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
      var st = conn.createStatement();
            ){
      var rs = st.executeQuery("SELECT nome FROM Piloto");
      var res = new HashSet<String>();
      while(rs.next()) {
        var nome = rs.getString(1);
        res.add(nome);
      }
      return res;
    } catch (SQLException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public Collection<Autenticavel> values() {
    return this.keySet().stream().map(this::get).collect(Collectors.toList());
  }

  @Override
  public Set<Entry<String, Autenticavel>> entrySet() {
    return this.keySet().stream().map(nome -> new AbstractMap.SimpleEntry<>(nome, this.get(nome))).collect(Collectors.toSet());
  }
}
