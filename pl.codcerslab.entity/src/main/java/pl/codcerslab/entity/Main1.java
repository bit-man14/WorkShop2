package pl.codcerslab.entity;

public class Main1 {//for testing only
    public static void main(String[] args) {
        UserDao userDao=new UserDao();
        User read = userDao.read(15);
    
        System.out.println(read.getPassword());
        
        
    }
}
