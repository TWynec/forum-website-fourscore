import java.sql.*;

@SuppressWarnings("unused")
public class ForumQueries {

    private final Statement statement;
    private ResultSet resultSet = null;

    public ForumQueries(Connection c) throws SQLException {
        statement = c.createStatement();
    }

    /** Congregation **/

    public void createCongregation(String title, String description) throws SQLException {
        String query = "INSERT INTO congregations (title, description) VALUES (\"" + title + "\", \"" + description + "\");";
        try {
            statement.execute(query);
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Congregation already exists with this name.");
        }
    }

    public void editCongregationTitle(String oldTitle, String newTitle) throws SQLException {
        statement.execute("UPDATE congregations SET title = \"" + newTitle + "\" WHERE title LIKE \"" + oldTitle + "\";");
    }

    public void editCongregationDescription(String title, String description) throws SQLException {
        statement.execute("UPDATE congregations SET description = \"" + description + "\" WHERE title LIKE \"" + title + "\";");
    }

    public void deleteCongregation(String title) throws SQLException {
        statement.execute("DELETE FROM congregations WHERE title LIKE \"" + title + "\";");
    }

    public void queryCongregations() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM congregations;");
        printCongregations();
    }

    /** Post & Congregation User **/

    public void createPost(String congregation, String title, String author, String post) throws SQLException {
        String query = "INSERT INTO posts (congregation, title, author, post) VALUES (\"" + congregation + "\", \"" + title + "\", \"" + author + "\", \"" + post + "\");";
        try {
            statement.execute(query);
            query = "INSERT INTO congregation_users (congregation, user) VALUES (\"" + congregation + "\", \"" + author + "\");";
            try {
                statement.execute(query);
            }
            catch (SQLIntegrityConstraintViolationException e) {
                statement.execute("UPDATE congregation_users SET posts = posts + 1 WHERE congregation LIKE \"" + congregation + "\" AND user LIKE \"" + author + "\";");
            }
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Post already exists with this title.");
        }
    }

    public void editPostTitle(String congregation, String oldTitle, String newTitle) throws SQLException {
        statement.execute("UPDATE posts SET title = \"" + newTitle + "\" WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + oldTitle + "\";");
    }

    public void editPostBody(String congregation, String title, String post) throws SQLException {
        statement.execute("UPDATE posts SET post = \"" + post + "\" WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";");
    }

    public void likePost(String congregation, String title, String user) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM post_likes WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + title + "\" AND user LIKE \"" + user + "\";");
        if (resultSet.next()) {
            if (resultSet.getBoolean("liked")) {
                statement.execute("DELETE FROM post_likes WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + title + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE posts SET likes = likes - 1 WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";");
            }
            else {
                statement.execute("UPDATE post_likes SET liked = true, disliked = false WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + title + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE posts SET likes = likes + 1, dislikes = dislikes - 1 WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";");
            }
        }
        else {
            statement.execute("INSERT INTO post_likes (congregation, post_title, user, liked) values (\"" + congregation + "\", \"" + title + "\", \"" + user + "\", true);");
            statement.execute("UPDATE posts SET likes = likes + 1 WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";");
        }
    }

    public void dislikePost(String congregation, String title, String user) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM post_likes WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + title + "\" AND user LIKE \"" + user + "\";");
        if (resultSet.next()) {
            if (resultSet.getBoolean("disliked")) {
                statement.execute("DELETE FROM post_likes WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + title + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE posts SET dislikes = dislikes - 1 WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";");
            }
            else {
                statement.execute("UPDATE post_likes SET liked = false, disliked = true WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + title + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE posts SET likes = likes - 1, dislikes = dislikes + 1 WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";");
            }
        }
        else {
            statement.execute("INSERT INTO post_likes (congregation, post_title, user, disliked) values (\"" + congregation + "\", \"" + title + "\", \"" + user + "\", true);");
            statement.execute("UPDATE posts SET dislikes = dislikes + 1 WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";");
        }
    }

    public void deletePost(String congregation, String title, String author) throws SQLException {
        String query = "DELETE FROM posts WHERE congregation LIKE \"" + congregation + "\" AND title LIKE \"" + title + "\";";
        statement.execute(query);
        query = "UPDATE congregation_users SET posts = posts - 1 WHERE congregation LIKE \"" + congregation + "\" AND user LIKE \"" + author + "\";";
        statement.execute(query);
        query = "SELECT * FROM congregation_users WHERE congregation LIKE \"" + congregation + "\" AND user LIKE \"" + author + "\";";
        resultSet = statement.executeQuery(query);
        resultSet.next();
        if (resultSet.getInt("posts") == 0) statement.execute("DELETE FROM congregation_users WHERE " +
                "congregation LIKE \"" + congregation + "\" AND user LIKE \"" + author + "\";");
    }

    public void queryPosts(String congregation) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM posts WHERE congregation LIKE \"" + congregation + "\";");
        printPosts();
    }

    public void queryPostLikes(String congregation, String post) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM post_likes " +
                "WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\";");
        printPostLikes();
    }

    public void queryCongregationUsers(String congregation) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM congregation_users WHERE congregation LIKE \"" + congregation + "\";");
        printCongregationUsers();
    }

    /** Comment **/

    public void createComment(String congregation, String post, String title, String author, String comment) throws SQLException {
        String query = "INSERT INTO post_comments (congregation, post_title, comment_title, comment_author, comment) " +
                "VALUES (\"" + congregation + "\", \"" + post + "\", \"" + title + "\", \"" + author + "\", \"" + comment + "\");";
        try {
            statement.execute(query);
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Comment already exists with this title under this post.");
        }
    }

    public void editCommentTitle(String congregation, String post, String oldTitle, String newTitle) throws SQLException {
        statement.execute("UPDATE post_comments SET comment_title = \"" + newTitle + "\" WHERE " +
                "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + oldTitle + "\";");
    }

    public void editCommentBody(String congregation, String post, String title, String comment) throws SQLException {
        statement.execute("UPDATE post_comments SET comment = \"" + comment + "\" WHERE " +
                "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + title + "\";");
    }

    public void likeComment(String congregation, String post, String comment, String user) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM comment_likes WHERE " +
                "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\" AND user LIKE \"" + user + "\";");
        if (resultSet.next()) {
            if (resultSet.getBoolean("liked")) {
                statement.execute("DELETE FROM comment_likes WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE post_comments SET likes = likes - 1 WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
            }
            else {
                statement.execute("UPDATE comment_likes SET liked = true, disliked = false WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE post_comments SET likes = likes + 1, dislikes = dislikes - 1 WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
            }
        }
        else {
            statement.execute("INSERT INTO comment_likes (congregation, post_title, comment_title, user, liked) " +
                    "values (\"" + congregation + "\", \"" + post + "\", \"" + comment + "\", \"" + user + "\", true);");
            statement.execute("UPDATE post_comments SET likes = likes + 1 WHERE " +
                    "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
        }
    }

    public void dislikeComment(String congregation, String post, String comment, String user) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM comment_likes WHERE " +
                "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\" AND user LIKE \"" + user + "\";");
        if (resultSet.next()) {
            if (resultSet.getBoolean("disliked")) {
                statement.execute("DELETE FROM comment_likes WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE post_comments SET dislikes = dislikes - 1 WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
            }
            else {
                statement.execute("UPDATE comment_likes SET liked = false, disliked = true WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\" AND user LIKE \"" + user + "\";");
                statement.execute("UPDATE post_comments SET likes = likes - 1, dislikes = dislikes + 1 WHERE " +
                        "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
            }
        }
        else {
            statement.execute("INSERT INTO comment_likes (congregation, post_title, comment_title, user, disliked) " +
                    "values (\"" + congregation + "\", \"" + post + "\", \"" + comment + "\", \"" + user + "\", true);");
            statement.execute("UPDATE post_comments SET dislikes = dislikes + 1 WHERE " +
                    "congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
        }
    }

    public void deleteComment(String congregation, String post, String comment) throws SQLException {
        statement.execute("DELETE FROM post_comments WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
    }

    public void queryPostComments(String congregation, String post) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM post_comments " +
                "WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\";");
        printPostComments();
    }

    public void queryCommentLikes(String congregation, String post, String comment) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM comment_likes " +
                "WHERE congregation LIKE \"" + congregation + "\" AND post_title LIKE \"" + post + "\" AND comment_title LIKE \"" + comment + "\";");
        printCommentLikes();
    }

    /** User **/

    public void createNewUser(String username, String password, String email) throws SQLException {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@"
        + "[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$";
        if (email.matches(emailRegex)) {
            String query = "INSERT INTO users (username, password, email) values (\"" + username + "\", \"" + password + "\", \"" + email + "\");";
            try {
                statement.execute(query);
            }
            catch (SQLIntegrityConstraintViolationException e) {
                resultSet = statement.executeQuery("SELECT * FROM users WHERE username LIKE \"" + username + "\";");
                if (resultSet.next()) System.out.println("Username already in use.");
                resultSet = statement.executeQuery("SELECT * FROM users WHERE email LIKE \"" + email + "\";");
                if (resultSet.next()) System.out.println("Email already in use.");
            }
        }
        else {
            System.out.println("Please enter a valid email.");
        }
    }

    public void editUsername(String oldUsername, String newUsername, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username LIKE \"" + oldUsername + "\" AND password LIKE \"" + password + "\";";
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            resultSet = statement.executeQuery("SELECT * FROM users WHERE username LIKE \"" + newUsername + "\";");
            if (resultSet.next()) System.out.println("Username already in use.");
            else statement.execute("UPDATE users SET username = \"" + newUsername + "\" WHERE username LIKE \"" + oldUsername + "\";");
        }
        else System.out.println("Incorrect password; Please try again.");
    }

    public void editPassword(String username, String oldPassword, String newPassword) throws SQLException {
        String query = "SELECT * FROM users WHERE username LIKE \"" + username + "\" AND password LIKE \"" + oldPassword + "\";";
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            statement.execute("UPDATE users SET password = \"" + newPassword + "\" WHERE username LIKE \"" + username + "\";");
        }
        else System.out.println("Incorrect password; Please try again.");
    }

    public void editEmail(String username, String password, String newEmail) throws SQLException {
        String query = "SELECT * FROM users WHERE username LIKE \"" + username + "\" AND password LIKE \"" + password + "\";";
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            resultSet = statement.executeQuery("SELECT * FROM users WHERE email LIKE \"" + newEmail + "\";");
            if (resultSet.next()) System.out.println("Email already in use.");
            else statement.execute("UPDATE users SET email = \"" + newEmail + "\" WHERE username LIKE \"" + username + "\";");
        }
        else System.out.println("Incorrect password; Please try again.");
    }

    public void editBio(String username, String password, String newBio) throws SQLException {
        String query = "SELECT * FROM users WHERE username LIKE \"" + username + "\" AND password LIKE \"" + password + "\";";
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            statement.execute("UPDATE users SET bio = \"" + newBio + "\" WHERE username LIKE \"" + username + "\";");
        }
        else System.out.println("Incorrect password; Please try again.");
    }

    public void editAvatar(String username, String password, String newAvatar) throws SQLException {
        String query = "SELECT * FROM users WHERE username LIKE \"" + username + "\" AND password LIKE \"" + password + "\";";
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            statement.execute("UPDATE users SET pfp = \"" + newAvatar + "\" WHERE username LIKE \"" + username + "\";");
        }
        else System.out.println("Incorrect password; Please try again.");
    }

    public void deleteUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username LIKE \"" + username + "\" AND password LIKE \"" + password + "\";";
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            statement.execute("DELETE FROM users WHERE username LIKE \"" + username + "\" AND password LIKE \"" + password + "\";");
        }
        else {
            System.out.println("Incorrect password; Please try again.");
        }
    }

    public void queryUsers() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM users");
        printUsers();
    }

    /** Print ResultSet **/

    public void printCongregations() throws SQLException {
        System.out.println("*** Congregations ***");
        System.out.println("title -- description");
        System.out.println("--------------------");
        while (resultSet.next()) {
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            System.out.printf("%s -- %s\n", title, description);
        }
        System.out.println();
    }

    public void printUsers() throws SQLException {
        System.out.println("*** Users ***");
        System.out.println("username -- password -- email -- bio -- pfp");
        System.out.println("-------------------------------------------");
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            String bio = resultSet.getString("bio");
            String pfp = resultSet.getString("pfp");
            System.out.printf("%s -- %s -- %s -- %s -- %s\n", username, password, email, bio, pfp);
        }
        System.out.println();
    }

    public void printCongregationUsers() throws SQLException {
        System.out.println("*** Congregation Users ***");
        System.out.println("congregation -- user -- posts");
        System.out.println("-----------------------------");
        while (resultSet.next()) {
            String congregation = resultSet.getString("congregation");
            String user = resultSet.getString("user");
            int posts = resultSet.getInt("posts");
            System.out.printf("%s -- %s -- %d\n", congregation, user, posts);
        }
        System.out.println();
    }

    public void printPosts() throws SQLException {
        System.out.println("*** Posts ***");
        System.out.println("congregation -- title -- author -- post -- likes/dislikes");
        System.out.println("---------------------------------------------------------");
        while (resultSet.next()) {
            String congregation = resultSet.getString("congregation");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String post = resultSet.getString("post");
            int likes = resultSet.getInt("likes");
            int dislikes = resultSet.getInt("dislikes");
            System.out.printf("%s -- %s -- %s -- %s -- %d/%d\n", congregation, title, author, post, likes, dislikes);
        }
        System.out.println();
    }

    public void printPostLikes() throws SQLException {
        System.out.println("*** Post Likes ***");
        System.out.println("congregation -- post title -- user -- liked/disliked");
        System.out.println("----------------------------------------------------");
        while (resultSet.next()) {
            String congregation = resultSet.getString("congregation");
            String post_title = resultSet.getString("post_title");
            String user = resultSet.getString("user");
            boolean liked = resultSet.getBoolean("liked");
            boolean disliked = resultSet.getBoolean("disliked");
            System.out.printf("%s -- %s -- %s -- %b/%b\n", congregation, post_title, user, liked, disliked);
        }
        System.out.println();
    }

    public void printPostComments() throws SQLException {
        System.out.println("*** Post Comments ***");
        System.out.println("congregation -- post title -- comment title -- comment author -- comment -- likes/dislikes");
        System.out.println("------------------------------------------------------------------------------------------");
        while (resultSet.next()) {
            String congregation = resultSet.getString("congregation");
            String post_title = resultSet.getString("post_title");
            String comment_title = resultSet.getString("comment_title");
            String comment_author = resultSet.getString("comment_author");
            String comment = resultSet.getString("comment");
            int likes = resultSet.getInt("likes");
            int dislikes = resultSet.getInt("dislikes");
            System.out.printf("%s -- %s -- %s -- %s -- %s -- %d/%d\n", congregation, post_title, comment_title, comment_author, comment, likes, dislikes);
        }
        System.out.println();
    }

    public void printCommentLikes() throws SQLException {
        System.out.println("*** Comment Likes ***");
        System.out.println("congregation -- post title -- comment title -- user -- liked/disliked");
        System.out.println("---------------------------------------------------------------------");
        while (resultSet.next()) {
            String congregation = resultSet.getString("congregation");
            String post_title = resultSet.getString("post_title");
            String comment_title = resultSet.getString("comment_title");
            String user = resultSet.getString("user");
            boolean liked = resultSet.getBoolean("liked");
            boolean disliked = resultSet.getBoolean("disliked");
            System.out.printf("%s -- %s -- %s -- %s -- %b/%b\n", congregation, post_title, comment_title, user, liked, disliked);
        }
        System.out.println();
    }
}