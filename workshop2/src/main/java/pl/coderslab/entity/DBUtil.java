package pl.coderslab.entity;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";
    //podłączenie do bazy
    public static Connection conn() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn();
    }
    
    //executeUpdate - zmiany w tabeli
    public static int execUpdate(Connection conn, String sql, String... params) {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException sq) {
            System.out.println("SQL error, check integrity constraints");
            //sq.printStackTrace();
            return -1;
        }
    }
    
    
    //executeQuery - odczyt z tabeli
    public static void execSelect(Connection conn, String query, String... columnNames) {
        
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                for (String param : columnNames) {
                    System.out.print((resultSet.getString(param)) + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void printData(Connection conn, String query, String... columnNames) {
        
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                for (String param : columnNames) {
                    System.out.print(StringUtils.rightPad(resultSet.getString(param),20));
                    
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
