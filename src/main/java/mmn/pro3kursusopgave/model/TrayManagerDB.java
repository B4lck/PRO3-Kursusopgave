package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.database.Database;
import mmn.pro3kursusopgave.model.entities.Tray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrayManagerDB implements TrayManager {
    private final Connection connection = Database.getConnection();

    private final Map<Integer, Tray> trays = new HashMap<>();

    @Override
    public List<Tray> getAllTrays() {
        List<Tray> returnList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.tray");
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                double maxWeight = res.getDouble("max_weight");
                String type = res.getString("type_of_animal");
                int packageNumber = res.getInt("package_no");
                int trayNumber = res.getInt("tray_no");

                Tray tray = new Tray(maxWeight, type, packageNumber, trayNumber);
                returnList.add(tray);
                trays.put(trayNumber, tray);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnList;
    }

    @Override
    public Tray getTray(int id) {
        Tray tray = trays.get(id);
        if (tray != null)
            return tray;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.tray WHERE tray_no = ?");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                double maxWeight = res.getDouble("max_weight");
                String type = res.getString("type_of_animal");
                int packageNumber = res.getInt("package_no");
                int trayNumber = res.getInt("tray_no");

                tray = new Tray(maxWeight, type, packageNumber, trayNumber);
                trays.put(trayNumber, tray);

                return tray;
            } else {
                throw new RuntimeException("Tray does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addTray(double max_weight, String animalType, int packageNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO slaughter_house.tray (max_weight, type_of_animal, package_no) VALUES (?, ?, ?) RETURNING slaughter_house.tray.tray_no as id");
            statement.setDouble(1, max_weight);
            statement.setString(2, animalType);
            statement.setInt(3, packageNumber);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                trays.put(id, new Tray(max_weight, animalType, packageNumber, id));
                return id;
            } else {
                throw new RuntimeException("Kunne ikke indsætte");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteTray(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM slaughter_house.tray WHERE tray_no=? RETURNING slaughter_house.tray.tray_no");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                trays.remove(id);
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
