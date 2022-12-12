package data;

import campeonatos.Piloto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PilotosDAO implements Map<String, Piloto> {
    private static PilotosDAO dao = null;

    private PilotosDAO() {

    }

    public static PilotosDAO getInstance() {
        if(dao == null) dao = new PilotosDAO();
        return dao;
    }

    @Override
    public int size() {
        try(Connection con = DriverManager.getConnection(ConnectionData.getConnectionString());
            var st = con.createStatement();
        ) {
            var query = "SELECT COUNT(*) FROM Piloto";
            var rs = st.executeQuery(query);
            if(rs.next()) {
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
        return this.get(piloto.getNome()).equals(value);
    }

    @Override
    public Piloto get(Object key) {
        try(
                var conn = DriverManager.getConnection(ConnectionData.getConnectionString());
                var ps = conn.prepareStatement("SELECT * FROM Piloto WHERE Piloto.nome = ?");
                ) {
            ps.setString(1, (String)key);
            var rs = ps.executeQuery();
            if(rs.next()) {
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
        if(piloto == null) {
            try(Connection conn = DriverManager.getConnection(ConnectionData.getConnectionString());
                var pt = conn.prepareStatement("INSERT INTO Piloto(nome, cts, sva) VALUES(?, ?, ?)");
            ) {
                pt.setString(1, value.getNome());
                pt.setInt(2, value.getCts());
                pt.setInt(3, value.getSva());
                if(pt.executeUpdate() == 1) {
                    return value;
                }
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try(Connection conn = DriverManager.getConnection(ConnectionData.getConnectionString());
                var pt = conn.prepareStatement("UPDATE Piloto SET cts = ?, sva = ? WHERE Piloto.nome = ?");
            ) {
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
        if(piloto == null) return null;
        try(
                var conn = DriverManager.getConnection(ConnectionData.getConnectionString());
                var pt = conn.prepareStatement("DELETE FROM Piloto WHERE Nome = ?");
                ) {
            pt.setString(1, (String)key);
            if(pt.executeUpdate() != 0)
                    return piloto;
            else return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends Piloto> m) {
        for(var entry: m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Piloto> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Piloto>> entrySet() {
        return null;
    }
}
