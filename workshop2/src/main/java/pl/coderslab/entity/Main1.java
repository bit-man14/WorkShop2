package pl.coderslab.entity;

import java.util.Arrays;

public class Main1 {//for testing only
    
    public static void main(String[] args) {
        //test
        //new user-obiekt
        int idShow = 28, run = 22;//run - kolejny test
        User user = new User();
        user.setUserName("nameTest" + run);
        user.setEmail("xxx" + run + "@xxx.com");
        user.setPassword("1xxx");
        System.out.println("object >user< created.");
        
        //create w DB
        UserDao userDao = new UserDao();//nowy "zestaw narzędzi" do DB
        userDao.create(user);
        System.out.print("new user created in DB.\n");
        System.out.println(user.toString());
        //read all
        System.out.println("All users: ------");
        DBUtil.printData(DBUtil.conn(), "SELECT * FROM users", "id", "username", "email");
        System.out.println("------");
        
        System.out.print("User to see, id=" + idShow + " : ");
        //read one
        System.out.println(userDao.read(idShow).toString());
        //and update with new data
        System.out.println("Update: "+idShow);
        
        User user1 = userDao.read(idShow);
        user1.setUserName("NewName");
        user1.setEmail("Update"+run+"@xxx.com");
        user1.setPassword("New1xxx");
        userDao.update(user1);
      
        
        //remove id
        System.out.println("User id " + (idShow+run) + " - removed");
        userDao.removeUserData(idShow+run);
        //read all again
        System.out.println("All users: ------");
        DBUtil.printData(DBUtil.conn(), "SELECT * FROM users", "id", "username", "email");
        System.out.println("------");
    }
}
