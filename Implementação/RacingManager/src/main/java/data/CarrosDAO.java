package data;

import business.carros.*;
import business.exceptions.CilindradaInvalidaException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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
      try(var result = s.executeQuery(query)) {
        if (result.next()) {
          return result.getInt(1);
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
    var cValue = (Carro)value;
    var carro = this.get(cValue.getModelo());
    return carro != null && carro.equals(value);
  }

  private @Nullable Carro getC1OrC2(@NotNull PreparedStatement pt1, PreparedStatement pt1h, String modelo,
                                    String marca, int cilindrada, int potenciaCombustao) {
    try {
      pt1.setString(1, modelo);
      try(var rs2 = pt1.executeQuery()) {
        if(!rs2.next()) return null;
          var afinacao = rs2.getFloat(2);
          var hibrido = rs2.getBoolean(3);
          if (hibrido) {
            pt1h.setString(1, modelo);
            try(var rs3 = pt1h.executeQuery()) {
              if(!rs3.next()) return null;
              var potencia_eletrica = rs3.getInt(2);
              return new C1H(modelo, marca, potenciaCombustao, afinacao, potencia_eletrica);
            }
          } else {
            System.out.println("Não é Hibrido");
            return new C1(modelo, marca, potenciaCombustao, afinacao);
          }
      }
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
      ps.setString(1, (String)key);
      try(var rs = ps.executeQuery()) {
        if(!rs.next()) return null;
        System.out.println("Carro encontrado");
        modelo = rs.getString(1);
        var marca = rs.getString(2);
        var cilindrada = rs.getInt(3);
        var potenciaCombustao = rs.getInt(4);
        var tipo = rs.getString(5);

        if (tipo.equals("C1")) {
          System.out.println("Tipo C1");
          return this.getC1OrC2(c1s, c1hs, modelo, marca, cilindrada, potenciaCombustao);
        } else if (tipo.equals("C2")) {
          return this.getC1OrC2(c2s, c2hs, modelo, marca, cilindrada, potenciaCombustao);
        } else if (tipo.equals("GT")) {
          gts.setString(1, modelo);
          try (var gtr = gts.executeQuery()) {
            if(!gtr.next()) return null;
            var fatorDesgaste = gtr.getFloat(2);
            var hibrido = gtr.getBoolean(3);
            if (hibrido) {
              gths.setString(1, modelo);
              try(var gthr = gths.executeQuery()) {
                if(!gthr.next()) return null;
                var potenciaEletrica = gthr.getInt(2);
                return new GTH(modelo, marca, cilindrada, potenciaCombustao, fatorDesgaste, potenciaEletrica);
              }
            } else {
              try {
                return new GT(modelo, marca, cilindrada, potenciaCombustao, fatorDesgaste);
              } catch (CilindradaInvalidaException e) {
                throw new RuntimeException(e);
              }
            }
          }
        } else if (tipo.equals("SC")) {
          scs.setString(1, modelo);
          try(var scr = scs.executeQuery()) {
            if(!scr.next()) return null;
            return new SC(modelo, marca, potenciaCombustao);
          }
          }
          return null;
      }
    } catch (SQLException | CilindradaInvalidaException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Carro put(String key, Carro value) {
    var carro = this.get(key);

    try (
    var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
    var pt1 = conn.prepareStatement("INSERT INTO Carro(modelo, marca, cilindrada, potencia_combustao, tipo) VALUES(?, ?, ?, ?, ?)");
  ) {
      if(carro == null) {
        //get all attributes from value
        var modelo = value.getModelo();
        var marca = value.getMarca();
        var cilindrada = value.getCilindrada();
        var potenciaCombustao = value.getPotenciaCombustao();
        var tipo = value.getClass().getSimpleName();
        var hibrido = false;
        if(tipo.equals("C1H")) {
          tipo = "C1";
          hibrido = true;
        }
        else if(tipo.equals("GTH")) {
          tipo = "GT";
          hibrido = true;
        }
        else if(tipo.equals("C2H")) {
          tipo = "C2";
          hibrido = true;
        }
        //insert into Carro
        pt1.setString(1, modelo);
        pt1.setString(2, marca);
        pt1.setInt(3, cilindrada);
        pt1.setInt(4, potenciaCombustao);
        pt1.setString(5, tipo);
        pt1.executeUpdate();
        //insert into C1, C2, GT, SC
        if(tipo.equals("C1")) {
          try(var pt2 = conn.prepareStatement("INSERT INTO C1(modelo, afinacao, hibrido) VALUES(?, ?, ?)")) {
            pt2.setString(1, modelo);
            C1 c1 = (C1) value;
            pt2.setFloat(2, c1.getAfinacao());
            pt2.setBoolean(3, hibrido);
            pt2.executeUpdate();
            if (hibrido) {
              try(var pt3 = conn.prepareStatement("INSERT INTO C1H(modelo, potencia_eletrica) VALUES(?, ?)")) {
                pt3.setString(1, modelo);
                pt3.setInt(2, ((C1H) value).getPotenciaEletrica());
                pt3.executeUpdate();
              }
            }
          }
        }
        else if(tipo.equals("C2")) {
          try(var pt2 = conn.prepareStatement("INSERT INTO C2(modelo, afinacao, hibrido) VALUES(?, ?, ?)")) {
            pt2.setString(1, modelo);
            C2 c2 = (C2) value;
            pt2.setFloat(2, c2.getAfinacao());
            pt2.setBoolean(3, hibrido);
            pt2.executeUpdate();
            if (hibrido) {
              try(var pt3 = conn.prepareStatement("INSERT INTO C2H(modelo, potencia_eletrica) VALUES(?, ?)")) {
                pt3.setString(1, modelo);
                pt3.setInt(2, ((C2H) value).getPotenciaEletrica());
                pt3.executeUpdate();
              }
            }
          }
        }
        else if(tipo.equals("GT")) {
          var pt2 = conn.prepareStatement("INSERT INTO GT(modelo, fator_desgaste, hibrido) VALUES(?, ?, ?)");
          pt2.setString(1, modelo);
          pt2.setFloat(2, ((GT)value).getFatorDesgaste());
          pt2.setBoolean(3, hibrido);
          pt2.executeUpdate();
          if(hibrido) {
            try(var pt3 = conn.prepareStatement("INSERT INTO GTH(modelo, potencia_eletrica) VALUES(?, ?)")) {
              pt3.setString(1, modelo);
              pt3.setInt(2, ((GTH) value).getPotenciaEletrica());
              pt3.executeUpdate();
            }
          }
        }
        else if(tipo.equals("SC")) {
          try(var pt2 = conn.prepareStatement("INSERT INTO SC(modelo) VALUES(?)")) {
            pt2.setString(1, modelo);
            pt2.executeUpdate();
          }
        }
      }
      else {
        //update Carro
        var pt2 = conn.prepareStatement("UPDATE Carro SET marca = ?, cilindrada = ?, potencia_combustao = ?, WHERE modelo = ?");
        pt2.setString(1, value.getMarca());
        pt2.setInt(2, value.getCilindrada());
        pt2.setInt(3, value.getPotenciaCombustao());
        pt2.setString(4, value.getModelo());
        pt2.executeUpdate();
        //update C1, C2, GT, SC
        var tipo = value.getClass().getSimpleName();
        if(tipo.equals("C1")) {
          var pt3 = conn.prepareStatement("UPDATE C1 SET afinacao = ? WHERE modelo = ?");
          pt3.setFloat(1, ((C1)value).getAfinacao());
          pt3.setString(2, value.getModelo());
          pt3.executeUpdate();
          if(value instanceof C1H) {
            var pt4 = conn.prepareStatement("UPDATE C1H SET potencia_eletrica = ? WHERE modelo = ?");
            pt4.setInt(1, ((C1H)value).getPotenciaEletrica());
            pt4.setString(2, value.getModelo());
            pt4.executeUpdate();
          }
        }
        else if(tipo.equals("C2")) {
          var pt3 = conn.prepareStatement("UPDATE C2 SET afinacao = ? WHERE modelo = ?");
          pt3.setFloat(1, ((C2)value).getAfinacao());
          pt3.setString(2, value.getModelo());
          pt3.executeUpdate();
          if(value instanceof C2H) {
            var pt4 = conn.prepareStatement("UPDATE C2H SET potencia_eletrica = ? WHERE modelo = ?");
            pt4.setInt(1, ((C2H)value).getPotenciaEletrica());
            pt4.setString(2, value.getModelo());
            pt4.executeUpdate();
          }
        }
        else if(tipo.equals("GT")) {
          var pt3 = conn.prepareStatement("UPDATE GT SET fator_desgaste = ? WHERE modelo = ?");
          pt3.setFloat(1, ((GT)value).getFatorDesgaste());
          pt3.setString(2, value.getModelo());
          pt3.executeUpdate();
          if(value instanceof GTH) {
            var pt4 = conn.prepareStatement("UPDATE GTH SET potencia_eletrica = ? WHERE modelo = ?");
            pt4.setInt(1, ((GTH)value).getPotenciaEletrica());
            pt4.setString(2, value.getModelo());
            pt4.executeUpdate();
          }
        }
        else if(tipo.equals("SC")) {
          //nothing to update
        }
      }
      return carro;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException(); 
    }
  }

  @Override
  public Carro remove(Object key) {
    var carro = this.get(key);
    try(var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
        var pt1 = conn.prepareStatement("DELETE FROM CARRO WHERE modelo = ?")) {
      if(carro == null) return null;

      if(carro instanceof C1) {
        try(var pt2 = conn.prepareStatement("DELETE FROM C1 WHERE modelo = ?")) {
          if (carro instanceof C1H) {
            try(var pt3 = conn.prepareStatement("DELETE FROM C1H WHERE modelo = ?")) {
              pt3.setString(1, carro.getModelo());
              pt3.executeUpdate();
            }
          }
          pt2.setString(1, carro.getModelo());
          pt2.executeUpdate();
        }
      }
      else if(carro instanceof C2) {
        try (var pt2 = conn.prepareStatement("DELETE FROM C2 WHERE modelo = ?")) {
          if (carro instanceof C2H) {
            try(var pt3 = conn.prepareStatement("DELETE FROM C2H WHERE modelo = ?")) {
              pt3.setString(1, carro.getModelo());
              pt3.executeUpdate();
            }
          }
          pt2.setString(1, carro.getModelo());
          pt2.executeUpdate();
        }
      }
      else if(carro instanceof GT) {
        try(var pt2 = conn.prepareStatement("DELETE FROM GT WHERE modelo = ?")) {
          if (carro instanceof GTH) {
            try (var pt3 = conn.prepareStatement("DELETE FROM GTH WHERE modelo = ?")) {
              pt3.setString(1, carro.getModelo());
              pt3.executeUpdate();
            }
          }
          pt2.setString(1, carro.getModelo());
          pt2.executeUpdate();
        }
      }
      else if(carro instanceof SC) {
        try(var pt2 = conn.prepareStatement("DELETE FROM SC WHERE modelo = ?")) {
          pt2.setString(1, carro.getModelo());
          pt2.executeUpdate();
        }
      }
      pt1.setString(1, carro.getModelo());
      pt1.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return carro;
  }

  @Override
  public void putAll(Map<? extends String, ? extends Carro> m) {
    for(Carro c : m.values()) {
      this.put(c.getModelo(), c);
    }
  }

  @Override
  public void clear() {
    try (var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd)) {
      try (
              var pt1 = conn.prepareStatement("DELETE FROM C1H");
              var pt2 = conn.prepareStatement("DELETE FROM C2H");
              var pt3 = conn.prepareStatement("DELETE FROM GTH");
              var pt4 = conn.prepareStatement("DELETE FROM SC");
              var pt5 = conn.prepareStatement("DELETE FROM C1");
              var pt6 = conn.prepareStatement("DELETE FROM C2");
              var pt7 = conn.prepareStatement("DELETE FROM GT");
              var pt8 = conn.prepareStatement("DELETE FROM Carro");
      ) {
        conn.setAutoCommit(false);
        pt1.executeUpdate();
        pt2.executeUpdate();
        pt3.executeUpdate();
        pt4.executeUpdate();
        pt5.executeUpdate();
        pt6.executeUpdate();
        pt7.executeUpdate();
        pt8.executeUpdate();
        conn.commit();
      }
      catch (SQLException e) {
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
        var st = conn.createStatement();
    ) {
      Set<String> keys = new TreeSet<>();
      try (var rs = st.executeQuery("SELECT modelo FROM Carro")) {
        while(rs.next()) {
          keys.add(rs.getString(1));
        }
      }
      return keys;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Collection<Carro> values() {
    return this.keySet().stream().map(modelo -> this.get(modelo)).collect(Collectors.toList());
  }

  @Override
  public Set<Entry<String, Carro>> entrySet() {
    return this.values()
            .stream()
            .map(carro -> new AbstractMap.SimpleEntry<>(carro.getModelo(), carro))
            .collect(Collectors.toSet());
  }
}
