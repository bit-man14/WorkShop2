package pl.codcerslab.entity;


import com.sun.tools.javac.util.ArrayUtils;
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
        try (Connection conn = DBUtil.conn()) {
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
    
    
    //update wg obiektu
    public void update(User user) {
        try (Connection conn = DBUtil.conn()) {
            PreparedStatement pStm = conn.prepareStatement(CHANGE_USER_DATA);
            pStm.setString(1, user.getUserName());
            pStm.setString(2, user.getEmail());
            pStm.setString(3, this.hashPassword(user.getPassword()));
            pStm.setInt(4, user.getId());
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //dane z tabeli do obiektu, wg id
    public static User read(int userId) {
        try (Connection conn = DBUtil.conn()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_DATA);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //dane z tabeli do obiektu, wg id
    public static User[] readAllUsers() {
        try (Connection conn = DBUtil.conn()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
            statement.setString(1, "IS NOT NULL");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users
            }
            return users;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static int removeUserData(int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = DBUtil.conn();
        int r = DBUtil.execUpdate(con1, DELETE_USER_DATA, String.valueOf(userId));
        return r;//number of records affected
    }
    
    public static void printUserData(int userId) throws SQLIntegrityConstraintViolationException {
        Connection con1 = DBUtil.conn();
        DBUtil.execSelect(con1, "SELECT * FROM users WHERE id=" + userId + ";", "id", "email", "username", "password");
    }
    
    public static void printAllUserData() throws SQLIntegrityConstraintViolationException {
        Connection con1 = DBUtil.conn();
        DBUtil.execSelect(con1, "SELECT * FROM users", "email", "username", "password");
    }
    
    //hashowanie hasła
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    
}
