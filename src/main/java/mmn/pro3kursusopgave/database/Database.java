package mmn.pro3kursusopgave.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection context;

    public static Connection getConnection() {
        try {
            if (context == null) {
                context = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pro3_slagteri", "pro3_slagteri", "pro3_slagteri");
                context.setSchema("slaughter_house");
                return context;
            }
            return context;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception.getMessage());
        }
    }
}
