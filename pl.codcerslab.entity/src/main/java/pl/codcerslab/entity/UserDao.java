package pl.codcerslab.entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String CHANGE_USER_DATA =
            "UPDATE users SET username=?, email=?, password=? WHERE id=?";
    private static final String READ_USER_DATA =
            "SELECT * FROM users WHERE id=?";
    private static final String DELETE_USER_DATA =
            "DELETE FROM users WHERE id=?";
    //pierwsza wersja
    public static int addNewUser(String userName, String userEmail, String userPass) throws SQLIntegrityConstraintViolationException {
        Connection con1 = pl.codcerslab.entity.DBUtil.conn();
        int r = pl.codcerslab.entity.DBUtil.execUpdate(con1, CREATE_USER_QUERY, userName, userEmail, userPass);
        return r;//number of records affected
    }
    //dane z obiektu klasy User
    public static User create(User user) {
        try (Connection conn = pl.codcerslab.entity.DBUtil.conn()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //pierwsza wersja
    public static int changeUserData(String userName, String userEmail, String userPass, int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = pl.codcerslab.entity.DBUtil.conn();
        int r = pl.codcerslab.entity.DBUtil.execUpdate(con1, CHANGE_USER_DATA, userName, userEmail, userPass, String.valueOf(userId));
        return r;//number of records affected
    }
    //dane z obiektu klasy User
    public static int update(int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = pl.codcerslab.entity.DBUtil.conn();
        int r = pl.codcerslab.entity.DBUtil.execUpdate(con1, CHANGE_USER_DATA, userName, userEmail, userPass, String.valueOf(userId));
        return r;//number of records affected
    }
    
    
    public static int removeUserData(int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = pl.codcerslab.entity.DBUtil.conn();
        int r = pl.codcerslab.entity.DBUtil.execUpdate(con1, DELETE_USER_DATA, String.valueOf(userId));
        return r;//number of records affected
    }
    
    public static void printUserData(int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = pl.codcerslab.entity.DBUtil.conn();
        pl.codcerslab.entity.DBUtil.execSelect(con1, "SELECT * FROM users WHERE id=" + userId + ";", "id", "email", "username", "password");
    }
    
    public static void printAllUserData() throws SQLIntegrityConstraintViolationException {
        Connection con1 = pl.codcerslab.entity.DBUtil.conn();
        pl.codcerslab.entity.DBUtil.execSelect(con1, "SELECT * FROM users", "email", "username", "password");
    }
    
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    
}