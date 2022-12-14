package data;

import carros.*;

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
        return this.get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        var cValue = (Carro)value;
        var carro = this.get(cValue.getModelo());
        return carro != null && carro.equals(value);
    }

    private Carro getC1OrC2(PreparedStatement pt1, PreparedStatement pt1h, String modelo,
                          String marca, int cilindrada, int potenciaCombustao, float fiabilidade) {
        try {
            Carro carro = null;
            pt1.setString(1, modelo);
            var rs2 = pt1.executeQuery();
            if (rs2.next()) {
                var afinacao = rs2.getFloat(2);
                var hibrido = rs2.getBoolean(3);
                if (hibrido) {
                    pt1h.setString(1, modelo);
                    var rs3 = pt1h.executeQuery();
                    if (rs3.next()) {
                        var potencia_eletrica = rs3.getInt(2);
                        carro = new C1H(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, afinacao, potencia_eletrica);
                    } else carro = null;
                } else {
                    carro = new C1(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, afinacao);
                }
            } else carro = null;
            return carro;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Carro get(Object key) {
        var modelo = (String)key;
        try(
                var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
                var ps = conn.prepareStatement("SELECT * FROM Carro WHERE modelo = ?");
                var c1s = conn.prepareStatement("SELECT * FROM C1 WHERE modelo = ?");
                var c1hs = conn.prepareStatement("SELECT * FROM C1H WHERE modelo = ?");
                var c2s = conn.prepareStatement("SELECT * FROM C2 WHERE modelo = ?");
                var c2hs = conn.prepareStatement("SELECT * FROM C2H WHERE modelo = ?");
                var gts = conn.prepareStatement("SELECT * FROM GT WHERE modelo = ?");
                var gths = conn.prepareStatement("SELECT * FROM GTH WHERE modelo = ?");
                var scs = conn.prepareStatement("SELECT * FROM SC WHERE modelo = ?");
                ){
            var rs = ps.executeQuery();
            if(rs.next()) {
                modelo = rs.getString(1);
                var marca = rs.getString(2);
                var cilindrada = rs.getInt(3);
                var potenciaCombustao = rs.getInt(4);
                var fiabilidade = rs.getInt(5);
                var tipo = rs.getString(6);
                Carro carro = null;

                if(tipo.equals("C1")) {
                    carro = this.getC1OrC2(c1s, c1hs, modelo, marca, cilindrada, potenciaCombustao, fiabilidade);
                } else if(tipo.equals("C2")) {
                    carro = this.getC1OrC2(c2s, c2hs, modelo, marca, cilindrada, potenciaCombustao, fiabilidade);
                } else if(tipo.equals("GT")) {
                    gts.setString(1, modelo);
                    var gtr = gts.executeQuery();
                    if(gtr.next()) {
                        var fatorDesgaste = gtr.getFloat(2);
                        var hibrido = gtr.getBoolean(3);
                        if(hibrido) {
                            gths.setString(1, modelo);
                            var gthr = gths.executeQuery();
                            if(gthr.next()) {
                                var potenciaEletrica = gthr.getInt(2);
                                carro = new GTH(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, fatorDesgaste, potenciaEletrica);
                            }
                            else carro = null;
                        }
                        else {
                            carro = new GT(modelo, marca, cilindrada, potenciaCombustao, fiabilidade, fatorDesgaste);
                        }
                    }
                    else carro = null;
                } else if(tipo.equals("SC")) {
                    scs.setString(1, modelo);
                    var scr = scs.executeQuery();
                    if(scr.next()) {
                        return new SC(modelo, marca, cilindrada, potenciaCombustao, fiabilidade);
                    } else carro = null;
                }
                return carro;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
