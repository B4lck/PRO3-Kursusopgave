package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.database.Database;
import mmn.pro3kursusopgave.server.model.entities.Package;
import mmn.pro3kursusopgave.server.model.entities.Tray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageManagerDB implements PackageManager{
    private final Connection connection = Database.getConnection();

    private final Map<Integer, Package> packages = new HashMap<>();

    private final TrayManager trayManager;

    public PackageManagerDB(TrayManager trayManager) {
        this.trayManager = trayManager;
    }

    @Override
    public List<Package> getAllPackages() {
        List<Package> returnList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM package");
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                int packageNumber = res.getInt("package_no");
                long expireDate = res.getLong("expire_date");

                List<Tray> trays = trayManager.getAllTraysInPackage(packageNumber);

                Package pack = new Package(packageNumber, expireDate, trays);
                returnList.add(pack);
                packages.put(pack.getPackageNumber(), pack);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnList;
    }

    @Override
    public Package getPackage(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM package WHERE package_no = ?");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                int packageNumber = res.getInt("package_no");
                long expireDate = res.getLong("expire_date");

                List<Tray> trays = trayManager.getAllTraysInPackage(packageNumber);

                Package pack = new Package(packageNumber, expireDate, trays);
                packages.put(id, pack);
                return pack;
            } else {
                throw new RuntimeException("Package does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addPackage(long expireDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO package (expire_date) VALUES (?) RETURNING package_no as id");
            statement.setLong(1, expireDate);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                packages.put(id, new Package(id, expireDate, new ArrayList<>()));
                return id;
            } else {
                throw new RuntimeException("Kunne ikke indsætte");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deletePackage(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM slaughter_house.package WHERE package_no=? RETURNING slaughter_house.animalpart.part_id");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                packages.remove(id);
                return id;
            }
            else {
                throw new RuntimeException("Findes ikke");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTrayToPackage(int trayId, int packageId) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO trayinpackage (tray_no, package_no) VALUES (?, ?)");
            statement.setInt(1, trayId);
            statement.setInt(2, packageId);
            
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
