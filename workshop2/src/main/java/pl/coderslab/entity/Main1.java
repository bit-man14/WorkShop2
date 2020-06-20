package pl.coderslab.entity;

import java.util.Arrays;

public class Main1 {//for testing only
    
    public static void main(String[] args) {
       //test
        //new user-obiekt
        int idShow=13, idRemove=16,run=4;//run - kolejny test
        User user=new User();
        user.setUserName("nameTest"+run);
        user.setEmail("xxx"+run+"@xxx.com");
        user.setPassword("1xxx");
        System.out.println("object user created.");
        //create w DB
        UserDao userDao=new UserDao();//nowy "zestaw narzędzi" do DB
        userDao.create(user);
        System.out.print("new user created in DB.\n");
        
       
        //read all
        System.out.println("All: ------");
        for (User readAllUser : userDao.readAllUsers()) {
            System.out.print(readAllUser.getId()+"\t");
            System.out.println(readAllUser.getUserName());
        }
        System.out.println("------");
        System.out.print("User id="+idShow+" : ");
        //read one
        System.out.println(userDao.read(idShow).getUserName());
        //and update with new data
        System.out.println("Update:");
        User user1=userDao.read(idShow);
        user1.setUserName("NewName");
        user1.setEmail("Newxxx@xxx.com");
        user1.setPassword("New1xxx");
        userDao.update(user1);
        //read all again
        System.out.println("All: ------");
        for (User readAllUser : userDao.readAllUsers()) {
            System.out.print(readAllUser.getId()+"\t");
            System.out.println(readAllUser.getUserName());
        }
        System.out.println("------");
        
        //remove id
        System.out.println("User id "+idRemove+" - remove");
        userDao.removeUserData(idRemove);
    }
}
