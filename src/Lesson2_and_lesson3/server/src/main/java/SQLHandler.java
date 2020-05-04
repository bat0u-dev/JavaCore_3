import java.sql.*;

public class SQLHandler {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\User\\" +
                    "JavaDev\\JavaUniversity\\Java_3_homework's\\JavaCore_3\\src\\Lesson2_and_lesson3\\server\\Database.db");
            stmt = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAnPassword(String login, String password){
        try {
        ResultSet rs = stmt.executeQuery("Select nickname FROM users WHERE login='"
                    + login + "' and password='" + password + "';");
        if(rs.next()){
         return rs.getString(1);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int changeNick (String oldNick, String newNick) {

        try {
         return stmt.executeUpdate("UPDATE users SET nickname = '"
                    + newNick + "' WHERE nickname ='" + oldNick+"'");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
