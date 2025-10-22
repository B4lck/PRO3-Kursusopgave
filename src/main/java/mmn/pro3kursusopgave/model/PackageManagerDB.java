package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.database.Database;
import mmn.pro3kursusopgave.model.entities.Package;

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

    @Override
    public List<Package> getAllPackages() {
        List<Package> returnList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM package");
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                int packageNumber = res.getInt("package_no");
                LocalDate expireDate = res.getDate("expire_date").toLocalDate();

                Package pack = new Package(packageNumber, expireDate);
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
        Package pack = packages.get(id);
        if (pack != null)
            return pack;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM package WHERE package_no = ?");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                int packageNumber = res.getInt("package_no");
                LocalDate expireDate = res.getDate("expire_date").toLocalDate();

                pack = new Package(packageNumber, expireDate);
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
    public int addPackage(LocalDate expireDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO package (expire_date) VALUES (?) RETURNING package_no as id");
            statement.setString(1, expireDate.toString());

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                packages.put(id, new Package(id, expireDate));
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
}
