import java.sql.SQLIntegrityConstraintViolationException;

public class Main1 {//for testing only
    public static void main(String[] args) {
        try {
            UserDao.printUserData(3);

        }catch(SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
        }
    }
}
