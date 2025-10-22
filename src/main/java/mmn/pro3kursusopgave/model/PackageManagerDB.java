package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.database.Database;
import mmn.pro3kursusopgave.model.entities.Package;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageManagerDB implements PackageManager{
    private Connection connection;

    private Map<Integer, Package> packages = new HashMap<>();

    public PackageManagerDB() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Package> getAllPackages() {
        return List.of();
    }

    @Override
    public Package getPackage(int id) {
        return null;
    }

    @Override
    public int addPackage(LocalDate expireDate) {
        return 0;
    }

    @Override
    public int removePackage(int id) {
        return 0;
    }
}
