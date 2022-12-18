package data;

public class ConnectionData {

    private static final String server = "localhost:3306";
    private static final String database = "RacingManager";
    public static final String user = "artur";
    public static final String pwd = "1234";

    public static String getUrl() {
        return "jdbc:mysql://" + server + "/" + database;
    }


}
