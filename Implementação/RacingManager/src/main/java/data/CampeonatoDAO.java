package data;

import business.campeonatos.Campeonato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class CampeonatoDAO implements Map<String, Campeonato> {
    private static CampeonatoDAO dao = null;

    private CampeonatoDAO() {}

    public static CampeonatoDAO getInstance() {
        if (dao == null)
            dao = new CampeonatoDAO();
        return dao;
    }

    @Override
    public int size() {
        try (Connection con =
                     DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
             var st = con.createStatement();) {
            var query = "SELECT COUNT(*) FROM Campeonato";
            try(var rs = st.executeQuery(query)) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
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
        var campeonato = (Campeonato)value;
        var campeonato2 = this.get(campeonato.getNomeCampeonato());
        return campeonato2 != null && campeonato2.equals(campeonato);
    }

    @Override
    public Campeonato get(Object key) {
        try (var conn =
                     DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
             var ps = conn.prepareStatement(
                     "SELECT nome FROM Campeonato WHERE nome = ?");
             var ps2 = conn.prepareStatement("SELECT campeonato, circuito FROM CircuitoCampeonato WHERE campeonato = ?");
             ) {
            ps.setString(1, (String)key);
            try(var rs = ps.executeQuery()) {
                if (rs.next()) {
                    var nome = rs.getString(1);
                    Set<String> circuitos = new HashSet<>();
                    ps2.setString(1, nome);
                    try(var rs2 = ps2.executeQuery()) {
                        while(rs2.next()) {
                            circuitos.add(rs2.getString(2));
                        }
                        return new Campeonato(nome, circuitos);
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Campeonato put(String key, Campeonato value) {
        var campeonato = this.get(key);
        if (campeonato == null) {
            try (Connection conn = DriverManager.getConnection(
                    ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
                 var pt = conn.prepareStatement(
                         "INSERT INTO Campeonato(nome) VALUES(?)");
                 var pt2 = conn.prepareStatement("INSERT INTO CircuitoCampeonato(campeonato, circuito) VALUES(?, ?)")
                 ) {
                pt.setString(1, key);
                pt.executeUpdate();
                var circuitos = value.getCircuitos();
                for(var circuito : circuitos) {
                    pt2.setString(1, key);
                    pt2.setString(2, circuito);
                    pt2.addBatch();
                }

                pt2.executeBatch();
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (Connection conn = DriverManager.getConnection(
                    ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
                 var pt = conn.prepareStatement(
                         "DELETE FROM CircuitoCampeonato WHERE campeonato = ?");
                 var pt2 = conn.prepareStatement("INSERT INTO CircuitoCampeonato(campeonato, circuito) VALUES(?, ?)");
                 ) {
                pt.setString(1, key);
                pt.executeUpdate();
                var circuitos = value.getCircuitos();
                for(var circuito : circuitos) {
                    pt2.setString(1, key);
                    pt2.setString(2, circuito);
                    pt2.addBatch();
                }
                pt2.executeBatch();
                return campeonato;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Campeonato remove(Object key) {
        var campeonato = this.get(key);
        if (campeonato == null)
            return null;
        try (var conn =
                     DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
             var pt = conn.prepareStatement("DELETE FROM Campeonato WHERE nome = ?");
             var pt2 = conn.prepareStatement("DELETE FROM CircuitoCampeonato WHERE campeonato = ?");
             ) {
            pt2.setString(1, (String)key);
            pt2.executeUpdate();
            pt.setString(1, (String)key);
            pt.executeUpdate();
            return campeonato;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Campeonato> m) {
        for (var entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for(var key : this.keySet()) {
            this.remove(key);
        }
    }

    @Override
    public Set<String> keySet() {
        try (var conn =
                     DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
             var st = conn.createStatement()) {
            var query = "SELECT nome FROM Campeonato";
            try(var rs = st.executeQuery(query)) {
                var res = new TreeSet<String>();
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
                return res;
            }
        } catch (SQLException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public Collection<Campeonato> values() {
        List<Campeonato> campeonatos = new ArrayList<>();
        for (var key : this.keySet()) {
            var campeonato = this.get(key);
            campeonatos.add(campeonato);
        }
        return campeonatos;
    }

    @Override
    public Set<Entry<String, Campeonato>> entrySet() {
        Set<Entry<String, Campeonato>> res = new TreeSet<>();

        for (var campeonato : this.values()) {
            res.add(new AbstractMap.SimpleEntry<>(campeonato.getNomeCampeonato(),
                    campeonato));
        }
        return res;
    }
}
