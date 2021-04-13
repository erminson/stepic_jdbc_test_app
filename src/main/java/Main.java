import exception.DBException;
import service.DBService;
import model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello JDBC");

        final DBService dbService = new DBService(DBService.DBType.SQLITE);
        try {
            dbService.createTable();
            dbService.saveUser("Lev");
            dbService.saveUser("Ilnar");
            dbService.saveUser("Radik");

            List<User> users = dbService.getAllUsers();
            System.out.println(users);
            dbService.cleanUp();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
