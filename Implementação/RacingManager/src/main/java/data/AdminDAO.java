package data;

import business.users.Admin;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AdminDAO implements Map<String, Admin> {

    private static AdminDAO dao = null;

    private AdminDAO() {

    }

    public static AdminDAO getInstance() {
        if(dao == null) dao = new AdminDAO();
        return dao;
    }

    @Override
    public int size() {
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var st = conn.createStatement();
        ) {
            try(var rs = st.executeQuery("SELECT COUNT(*) FROM Administrador")) {
                if(rs.next()) {
                    var size = rs.getInt(1);
                    return size;
                }
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
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var st = conn.prepareStatement("SELECT username FROM utilizador WHERE username = ?")
        ) {
            st.setString(1, (String)key);
            try(var rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        Admin ad = (Admin)value;
        var admin = this.get(ad.getUsername());
        return admin != null && ad.equals(admin);
    }

    @Override
    public Admin get(Object key) {
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var st = conn.prepareStatement("SELECT * FROM Utilizador WHERE username = ?");
            var st1 = conn.prepareStatement("SELECT * FROM Administrador WHERE username = ?");
        ) {
            st.setString(1, (String)key);

            try(var rs1 = st.executeQuery()) {
                if(!rs1.next()) return null;
                var username = rs1.getString(1);
                var password = rs1.getString(2);
                var autenticado = rs1.getBoolean(3);
                var tipo = rs1.getString(4);
                if(!tipo.equals("Admin")) return null;
                st1.setString(1, (String)key);
                try(var rs2 = st1.executeQuery()) {
                    if(!rs2.next()) return null;
                    return new Admin(username, password, autenticado);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Admin put(String key, Admin value) {
        var admin = this.get(key);
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)) {
            if(admin != null) {
                try(var pt1 = conn.prepareStatement("UPDATE Utilizador SET password = ?, autenticado = ? WHERE username = ?")) {
                    conn.setAutoCommit(false);
                    pt1.setString(1, value.getPassword());
                    pt1.setBoolean(2, value.estaAutenticado());
                    pt1.setString(3, value.getUsername());
                    pt1.executeUpdate();
                    conn.commit();
                    return admin;
                } catch(SQLException e) {
                    conn.rollback();
                    throw new RuntimeException(e);
                }
            }
            else {
                try(var pt1 = conn.prepareStatement("INSERT INTO Utilizador(username, password, autenticado, tipo) VALUES(?, ?, ?, ?)");
                    var pt2 = conn.prepareStatement("INSERT INTO Administrador(username) VALUES(?)");
                ) {
                    conn.setAutoCommit(false);
                    pt1.setString(1, value.getUsername());
                    pt1.setString(2, value.getPassword());
                    pt1.setBoolean(3, value.estaAutenticado());
                    pt1.setString(4, "Admin");
                    pt2.setString(1, value.getUsername());
                    pt1.executeUpdate();
                    pt2.executeUpdate();
                    conn.commit();
                    return null;
                } catch (SQLException e) {
                    conn.rollback();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Admin remove(Object key) {
        var admin = this.get(key);
        if(admin != null) {
            try (
                    var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            ) {
                try(var pt1 = conn.prepareStatement("DELETE FROM Administrador WHERE username = ?");
                    var pt2 = conn.prepareStatement("DELETE FROM Utilizador WHERE username = ?");
                ) {
                    conn.setAutoCommit(false);
                    pt1.setString(1, (String)key);
                    pt2.setString(1, (String)key);
                    pt1.executeUpdate();
                    pt2.executeUpdate();
                    conn.commit();
                } catch(SQLException e) {
                    conn.rollback();
                    throw new RuntimeException(e);
                }
                return admin;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Admin> m) {
        for(var entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)) {
            try(var st1 = conn.createStatement();
                var st2 = conn.createStatement();
            ) {
                conn.setAutoCommit(false);
                st1.executeUpdate("DELETE FROM Administrador");
                st2.executeUpdate("DELETE FROM Utilizador");
                conn.commit();
            } catch(SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> keySet() {
        try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
            var st1 = conn.createStatement();
        ) {
            Set<String> res = new TreeSet<>();
            try(var rs = st1.executeQuery("SELECT username FROM Administrador")) {
                while(rs.next()) {
                    res.add(rs.getString(1));
                }
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Admin> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    @Override
    public Set<Entry<String, Admin>> entrySet() {
        return this.keySet().stream().map(username -> new AbstractMap.SimpleEntry<>(username, this.get(username))).collect(Collectors.toSet());
    }
}
