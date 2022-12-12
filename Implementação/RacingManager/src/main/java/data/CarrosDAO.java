package data;

import carros.Carro;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CarrosDAO implements Map<String, Carro> {

    private static CarrosDAO dao = null;
    private CarrosDAO() {

    }

    public static CarrosDAO getInstance() {
        if(dao == null) dao = new CarrosDAO();
        return dao;
    }

    @Override
    public int size() {
        try (Connection conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
             Statement s = conn.createStatement();
        ) {
            String query = "SELECT COUNT(*) FROM CARRO";
            var result = s.executeQuery(query);
            if(result.next()){
                return result.getInt(1);
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
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Carro get(Object key) {
        var modelo = (String)key;
        try(
                var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
                var ps = conn.prepareStatement("SELECT * FROM Carro WHERE modelo = ?");
                ){
            var rs = ps.executeQuery();
            if(rs.next()) {
                modelo = rs.getString(1);
                var marca = rs.getString(2);
                var cilindrada = rs.getInt(3);
                var potenciaCombustao = rs.getInt(4);
                var fiabilidade = rs.getFloat(5);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Carro put(String key, Carro value) {
        return null;
    }

    @Override
    public Carro remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Carro> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Carro> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Carro>> entrySet() {
        return null;
    }
}
