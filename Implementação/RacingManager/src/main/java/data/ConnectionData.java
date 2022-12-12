package data;

public class ConnectionData {

    private static final String server = "localhost";
    private static final String database = "RacingManager";
    private static final String user = "artur";
    private static final String pwd = "1234";

    public static String getConnectionString() {
        return "jdbc:mysql://" + server + "/" + database + "user=" + user + "&password=" + pwd;
    }
}
