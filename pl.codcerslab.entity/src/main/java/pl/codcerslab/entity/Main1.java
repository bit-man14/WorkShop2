package pl.codcerslab.entity;

public class Main1 {//for testing only
    public static void main(String[] args) {
        UserDao userDao=new UserDao();
        
        User userToUpdate = userDao.read(7);
    
        userToUpdate.setUserName("Basia K");
    
        userToUpdate.setEmail("Basia@gmail.com");
    
        userToUpdate.setPassword("MojaBasia123");
    
        userDao.update(userToUpdate);
    
    
    }
}
