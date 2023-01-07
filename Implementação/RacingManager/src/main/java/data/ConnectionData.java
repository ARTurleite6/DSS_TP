package data;

public class ConnectionData {

    private static final String server = "localhost:3306";
    private static final String database = "racing_manager";
    public static final String user = "dss_tp";
    public static final String pwd = "password";

    public static String getUrl() {
        return "jdbc:mysql://" + server + "/" + database;
    }


}
