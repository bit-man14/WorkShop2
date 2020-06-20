import java.sql.SQLException;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String CHANGE_USER_DATA =
            "UPDATE users SET email=?, username=?, password=? WHERE id=?";
    private static final String READ_USER_DATA =
            "SELECT * FROM users WHERE id=?";
    private static final String DELETE_USER_DATA =
            "DELETE FROM users WHERE id=?";
    
    public static void main(String[] args) {
        try {
            System.out.println(DBUtil.conn());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
