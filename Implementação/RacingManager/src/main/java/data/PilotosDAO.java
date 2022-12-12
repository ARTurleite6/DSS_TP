package data;

import campeonatos.Piloto;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PilotosDAO implements Map<String, Piloto> {
  private static PilotosDAO dao = null;

  private PilotosDAO() {}

  public static PilotosDAO getInstance() {
    if (dao == null)
      dao = new PilotosDAO();
    return dao;
  }

  @Override
  public int size() {
    try (Connection con =
             DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
         var st = con.createStatement();) {
      var query = "SELECT COUNT(*) FROM Piloto";
      var rs = st.executeQuery(query);
      if (rs.next()) {
        return rs.getInt(1);
      }
      return 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isEmpty() {
    return this.size() == 0;
  }

  @Override
  public boolean containsKey(Object key) {
    return this.get(key) != null;
  }

  @Override
  public boolean containsValue(Object value) {
    var piloto = (Piloto)value;
    var piloto2 = this.get(piloto.getNome());
    if(piloto2 == null) return false;
    return piloto2.equals(value);
  }

  @Override
  public Piloto get(Object key) {
    try (var conn =
             DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
         var ps = conn.prepareStatement(
             "SELECT * FROM Piloto WHERE Piloto.nome = ?");) {
      ps.setString(1, (String)key);
      var rs = ps.executeQuery();
      if (rs.next()) {
        var nome = rs.getString(1);
        var cts = rs.getInt(2);
        var sva = rs.getInt(3);
        return new Piloto(nome, cts, sva);
      }
      return null;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Piloto put(String key, Piloto value) {
    var piloto = this.get(key);
    if (piloto == null) {
      try (Connection conn = DriverManager.getConnection(
               ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
           var pt = conn.prepareStatement(
               "INSERT INTO Piloto(nome, cts, sva) VALUES(?, ?, ?)");) {
        pt.setString(1, value.getNome());
        pt.setInt(2, value.getCts());
        pt.setInt(3, value.getSva());
        if (pt.executeUpdate() == 0) {
          throw new SQLException();
        }
        return null;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else {
      try (Connection conn = DriverManager.getConnection(
               ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
           var pt = conn.prepareStatement(
               "UPDATE Piloto SET cts = ?, sva = ? WHERE Piloto.nome = ?");) {
        pt.setInt(1, value.getCts());
        pt.setInt(2, value.getSva());
        pt.setString(3, key);

        pt.executeUpdate();
        return piloto;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public Piloto remove(Object key) {
    var piloto = this.get(key);
    if (piloto == null)
      return null;
    try (var conn =
             DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
         var pt = conn.prepareStatement("DELETE FROM Piloto WHERE Nome = ?");) {
      pt.setString(1, (String)key);
      if (pt.executeUpdate() != 0)
        return piloto;
      else
        return null;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void putAll(Map<? extends String, ? extends Piloto> m) {
    for (var entry : m.entrySet()) {
      this.put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
        var st = conn.createStatement();
    ) {
      st.executeUpdate("DELETE FROM Piloto");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Set<String> keySet() {
    try (var conn =
             DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
         var st = conn.createStatement();) {
      var query = "SELECT Piloto.Nome FROM Piloto";
      var rs = st.executeQuery(query);
      var res = new TreeSet<String>();
      while (rs.next()) {
        res.add(rs.getString(1));
      }
      return res;
    } catch (SQLException exception) {
      throw new RuntimeException();
    }
  }

  @Override
  public Collection<Piloto> values() {
    ArrayList<Piloto> pilotos = new ArrayList<>();
    for (var key : this.keySet()) {
      var piloto = this.get(key);
      pilotos.add(piloto);
    }
    return pilotos;
  }

  @Override
  public Set<Entry<String, Piloto>> entrySet() {
    Set<Entry<String, Piloto>> res = new TreeSet<>();

    for (var piloto : this.values()) {
      res.add(new AbstractMap.SimpleEntry<String, Piloto>(piloto.getNome(),
                                                          piloto));
    }
    return res;
  }
}
