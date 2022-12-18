import business.exceptions.UtilizadorNaoExisteException;
import data.ConnectionData;
import ui.Menu;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws UtilizadorNaoExisteException {
    new RacingManager().run();
  }
}
