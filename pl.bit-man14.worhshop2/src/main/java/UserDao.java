import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String CHANGE_USER_DATA =
            "UPDATE users SET username=?, email=?, password=? WHERE id=?";
    private static final String READ_USER_DATA =
            "SELECT * FROM users WHERE id=?";
    private static final String DELETE_USER_DATA =
            "DELETE FROM users WHERE id=?";
    
    public static int addNewUser(String userName, String userEmail, String userPass) throws SQLIntegrityConstraintViolationException {
        Connection con1 = DBUtil.conn();
        int r = DBUtil.execUpdate(con1, CREATE_USER_QUERY, userName, userEmail, userPass);
        return r;//number of records affected
    }
    public static int changeUserData(String userName, String userEmail, String userPass,int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = DBUtil.conn();
        int r = DBUtil.execUpdate(con1,CHANGE_USER_DATA, userName, userEmail, userPass, String.valueOf(userId));
        return r;//number of records affected
    }
    public static int removeUserData(String userName, String userEmail, String userPass,int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = DBUtil.conn();
        int r = DBUtil.execUpdate(con1,CHANGE_USER_DATA, userName, userEmail, userPass, String.valueOf(userId));
        return r;//number of records affected
    }
}
