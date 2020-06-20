package pl.coderslab.entity;


import org.apache.commons.lang3.ArrayUtils;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
  
    
    //nowe dane z obiektu klasy User - do DB
    public  User create(User user) {
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
    
    
    //update tabeli wg obiektu
    public void update(User user) {
        try (Connection conn = DBUtil.conn()) {
            PreparedStatement pStm = conn.prepareStatement(CHANGE_USER_DATA);
            pStm.setString(1, user.getUserName());
            pStm.setString(2, user.getEmail());
            pStm.setString(3, this.hashPassword(user.getPassword()));
            //System.out.println("id:" +user.getId());
            pStm.setInt(4, user.getId());
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //dane z tabeli do obiektu, wg id
    public  User read(int userId) {
        try (Connection conn = DBUtil.conn()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_DATA);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                //System.out.println("Read, id: "+user.getId());
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //dane z całej tabeli DB do tablicy obiektów
    public  User[] readAllUsers() {
        try (Connection conn = DBUtil.conn()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
            //statement.setString("");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users = ArrayUtils.add(users, user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public  void removeUserData(int userId) {
        try (Connection con1 = DBUtil.conn()) {
            PreparedStatement pStm = con1.prepareStatement(DELETE_USER_DATA);
            pStm.setInt(1, userId);
            pStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hashowanie hasła
    public  String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
