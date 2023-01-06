package data;

import business.campeonatos.Circuito;
import business.campeonatos.GDU;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CircuitoDAO implements Map<String, Circuito> {

    private static CircuitoDAO dao = null;

    private CircuitoDAO() {

    }

    public static CircuitoDAO getInstance() {
        if(dao == null) dao = new CircuitoDAO();
        return dao;
    }

    @Override
    public int size() {
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var st = conn.createStatement()
        ) {
            try(var rs = st.executeQuery("SELECT COUNT(*) FROM Circuito")) {
                if(rs.next()) {
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
        var c = (Circuito)value;
        var circuito = this.get(c.getNomeCircuito());
        return circuito != null && circuito.equals(c);
    }

    @Override
    public Circuito get(Object key) {
        try (var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
             var pt1 = conn.prepareStatement("SELECT nome, distancia, voltas FROM Circuito WHERE nome = ?");
             var pt2 = conn.prepareStatement("SELECT numero, circuito, dificuldade, tipo FROM Seccao WHERE circuito = ? ORDER BY numero ASC")) {
            pt1.setString(1, (String) key);
            try (var rs = pt1.executeQuery()) {
                if (!rs.next()) return null;
                var nome = rs.getString(1);
                var distancia = rs.getInt(2);
                var numeroVoltas = rs.getInt(3);
                pt2.setString(1, (String) key);
                try (var rs2 = pt2.executeQuery()) {
                    List<GDU> retas = new ArrayList<>();
                    List<GDU> curvas = new ArrayList<>();
                    List<GDU> chicanes = new ArrayList<>();
                    while (rs2.next()) {
                        var dificuldade = rs2.getInt(3);
                        var tipo = rs2.getString(4);
                        if (dificuldade == GDU.Impossivel.getDificuldade()) {
                            if (tipo.equals("Curva")) curvas.add(GDU.Impossivel);
                            else if (tipo.equals("Reta")) retas.add(GDU.Impossivel);
                        } else if (dificuldade == GDU.Dificil.getDificuldade()) {
                            switch (tipo) {
                                case "Curva" -> curvas.add(GDU.Dificil);
                                case "Reta" -> retas.add(GDU.Dificil);
                                case "Chicane" -> chicanes.add(GDU.Dificil);
                            }
                        } else if (dificuldade == GDU.Possivel.getDificuldade()) {
                            if (tipo.equals("Curva")) curvas.add(GDU.Possivel);
                            else if (tipo.equals("Reta")) retas.add(GDU.Possivel);
                        }
                    }
                    if (retas.size() == 0 && curvas.size() == 0 && chicanes.size() == 0){
                        return null;
                    }
                    return new Circuito(nome, distancia, numeroVoltas, retas, curvas, chicanes);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Circuito put(String key, Circuito value) {
        var circuito = this.get(key);
        if(circuito == null) {
            try (var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)
            ) {
                conn.setAutoCommit(false);
                try(
                        var pt1 = conn.prepareStatement("INSERT INTO Circuito(nome, distancia, voltas) VALUES(?, ?, ?)");
                        var pt2 = conn.prepareStatement("INSERT INTO Seccao(numero, circuito, dificuldade, tipo) VALUE(?, ?, ?, ?)")
                ) {
                    pt1.setString(1, key);
                    pt1.setInt(2, value.getDistancia());
                    pt1.setInt(3, value.getNumeroVoltas());
                    pt1.executeUpdate();
                    var retas = value.getRetas();
                    int numeroSeccao = 0;
                    for (GDU reta : retas) {
                        pt2.setInt(1, numeroSeccao++);
                        pt2.setString(2, key);
                        pt2.setInt(3, reta.getDificuldade());
                        pt2.setString(4, "Reta");
                        pt2.addBatch();
                    }
                    var curvas = value.getCurvas();
                    for (GDU curva : curvas) {
                        pt2.setInt(1, numeroSeccao++);
                        pt2.setString(2, key);
                        pt2.setInt(3, curva.getDificuldade());
                        pt2.setString(4, "Curva");
                        pt2.addBatch();
                    }
                    var chicanes = value.getChicanes();
                    for (GDU chicane : chicanes) {
                        pt2.setInt(1, numeroSeccao++);
                        pt2.setString(2, key);
                        pt2.setInt(3, chicane.getDificuldade());
                        pt2.setString(4, "Chicane");
                        pt2.addBatch();
                    }
                    pt2.executeBatch();
                    conn.commit();
                    return null;
                } catch(SQLException e) {
                    conn.rollback();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try (var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)
            ) {
                conn.setAutoCommit(false);
                try(
                        var pt1 = conn.prepareStatement("UPDATE FROM Circuito SET distancia = ?, voltas = ? WHERE nome = ?");
                        var pt2 = conn.prepareStatement("INSERT INTO Seccao(numero, circuito, dificuldade, tipo) VALUE(?, ?, ?, ?)");
                        var pt3 = conn.prepareStatement("DELETE FROM Seccao WHERE circuito = ?")
                ) {
                    pt3.setString(1, key);
                    pt3.executeUpdate();
                    pt1.setInt(1, value.getDistancia());
                    pt1.setInt(2, value.getNumeroVoltas());
                    pt1.setString(3, key);
                    pt1.executeUpdate();
                    var retas = value.getRetas();
                    for(int i = 0; i < retas.size(); ++i) {
                        pt2.setInt(1, i);
                        pt2.setString(2, key);
                        pt2.setInt(3, retas.get(i).getDificuldade());
                        pt2.setString(4, "Reta");
                        pt2.addBatch();
                    }
                    var curvas = value.getCurvas();
                    for(int i = 0; i < curvas.size(); ++i) {
                        pt2.setInt(1, i);
                        pt2.setString(2, key);
                        pt2.setInt(3, curvas.get(i).getDificuldade());
                        pt2.setString(4, "Curva");
                        pt2.addBatch();
                    }
                    var chicanes = value.getChicanes();
                    for(int i = 0; i < chicanes.size(); ++i) {
                        pt2.setInt(1, i);
                        pt2.setString(2, key);
                        pt2.setInt(3, chicanes.get(i).getDificuldade());
                        pt2.setString(4, "Chicane");
                        pt2.addBatch();
                    }
                    pt2.executeBatch();
                    conn.commit();
                    return circuito;
                } catch(SQLException e) {
                    conn.rollback();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Circuito remove(Object key) {
        var circuito = this.get(key);
        if(circuito == null) return null;
        try (var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)
        ) {
            conn.setAutoCommit(false);
            try(var pt = conn.prepareStatement("DELETE FROM Seccao WHERE circuito = ?");
                var pt2 = conn.prepareStatement("DELETE FROM Circuito WHERE nome = ?")
            ) {
                pt.setString(1, (String)key);
                pt.executeUpdate();
                pt2.setString(1, (String)key);
                pt2.executeUpdate();
                conn.commit();
                return circuito;
            } catch(SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends Circuito> m) {
        for(var circuito : m.entrySet()) {
            this.put(circuito.getKey(), circuito.getValue());
        }
    }

    @Override
    public void clear() {
        for(var circuitoNome : this.keySet()) {
            this.remove(circuitoNome);
        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        try(
                var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
                var st = conn.createStatement()
        ) {
            try(var rs = st.executeQuery("SELECT nome FROM Circuito")) {
                Set<String> res = new HashSet<>();
                while(rs.next()) {
                    res.add(rs.getString(1));
                }
                return res;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Collection<Circuito> values() {
        Collection<Circuito> coll = new ArrayList<>();
        for(var nomeCircuito : this.keySet()) {
            coll.add(this.get(nomeCircuito));
        }
        return coll;
    }

    @NotNull
    @Override
    public Set<Entry<String, Circuito>> entrySet() {
        return this.values().stream().map(circuito -> new AbstractMap.SimpleEntry<>(circuito.getNomeCircuito(), circuito)).collect(Collectors.toSet());
    }
}
