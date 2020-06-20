package pl.codcerslab.entity;

public class Main1 {//for testing only
    public static void main(String[] args) {
      User user=new User();
      user.setUserName("NewUser");
      user.setEmail("user@user.com");
      user.setPassword("HasłoUsera");
      
      UserDao.create(user);//wpis do bazy danych
      
        UserDao userDao=new UserDao();
        
        
    }
}
