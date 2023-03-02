import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UseForumQueries {

    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        ForumQueries forumQueries = new ForumQueries(conn);

        String congregation = "Welcome!";
        String post = "README";
        String comment = "Excitation";
        forumQueries.queryCongregations();
        forumQueries.queryUsers();
        forumQueries.queryCongregationUsers(congregation);
        forumQueries.queryPosts(congregation);
        forumQueries.queryPostLikes(congregation, post);
        forumQueries.queryPostComments(congregation, post);
        forumQueries.queryCommentLikes(congregation, post, comment);

        conn.close();
    }

    public static Connection getConnection() throws SQLException{
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        //Create a connection to the database
        String serverName = "127.0.0.1:3306";
        String myDatabase = "Forum";
        String url = "jdbc:mysql://" + serverName + "/" + myDatabase; // a JDBC url
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}