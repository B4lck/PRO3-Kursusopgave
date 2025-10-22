package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static boolean isDriverRegistered;

    private synchronized static void init() {
        try {
            if (!isDriverRegistered) {
                DriverManager.registerDriver(new org.postgresql.Driver());
                isDriverRegistered = true;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!isDriverRegistered) init();

        var context = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pro3_kursusopgave", "postgres", "postgres");
        context.setSchema("slaughter_house");
        return context;
    }
}
