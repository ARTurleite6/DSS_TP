import campeonatos.Piloto;
import data.ConnectionData;
import data.PilotosDAO;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException {
    var conn = DriverManager.getConnection(ConnectionData.getUrl(), ConnectionData.user, ConnectionData.pwd);
    var ps = conn.prepareStatement("SELECT * FROM Piloto");
    var rs = ps.executeQuery();
    while(rs.next()) {
      System.out.println(rs.getString(1));
    }
  }
}
