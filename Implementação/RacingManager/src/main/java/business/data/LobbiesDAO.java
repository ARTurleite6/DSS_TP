package business.data;

import business.campeonatos.Lobby;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class LobbiesDAO implements Map<Integer, Lobby> {
    private static LobbiesDAO dao = null;

    private LobbiesDAO() {

    }

    public static LobbiesDAO getInstance() {
        if(dao == null) dao = new LobbiesDAO();
        return dao;
    }

    @Override
    public int size() {
        try (var connection = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
             var st = connection.createStatement();
        ) {
            try(var rs = st.executeQuery("SELECT COUNT(*) FROM Lobby")) {
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
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Lobby get(Object key) {
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var pt = conn.prepareStatement("SELECT * FROM Lobby WHERE numero = ?");
            var pt2 = conn.prepareStatement("SELECT * FROM JogadorLobby where lobby = ?");
        ) {
            pt.setInt(1, (Integer)key);
            try(var lRs = pt.executeQuery()) {
                if(!lRs.next()) return null;
                var codigo = lRs.getInt(1);
                var campeonato = lRs.getBoolean(2);
                pt2.setInt(1, (Integer)key);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //TODO
        return null;
    }

    @Nullable
    @Override
    public Lobby put(Integer key, Lobby value) {
        return null;
    }

    @Override
    public Lobby remove(Object key) {
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends Integer, ? extends Lobby> m) {

    }

    @Override
    public void clear() {

    }

    @NotNull
    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @NotNull
    @Override
    public Collection<Lobby> values() {
        return null;
    }

    @NotNull
    @Override
    public Set<Entry<Integer, Lobby>> entrySet() {
        return null;
    }
}
