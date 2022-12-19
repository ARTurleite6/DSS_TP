import business.exceptions.UtilizadorNaoExisteException;

public class Main {
  public static void main(String[] args) throws UtilizadorNaoExisteException {
    new RacingManager().run();
  }
}
