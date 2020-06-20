import java.sql.SQLIntegrityConstraintViolationException;

public class Main1 {
    public static void main(String[] args) {
        try {
            int r=UserDao.changeUserData("BasiaN","b@b1N","nowabasia1N",3);
//            int r=UserDao.addNewUser("Basia","b@b1","basia1");
            System.out.println("Rows changed: "+r);
        }catch(SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
        }
    }
}
