package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.database.Database;
import mmn.pro3kursusopgave.server.model.entities.Tray;

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
                String type = res.getString("type_of_part");
                int trayNumber = res.getInt("tray_no");

                Tray tray = new Tray(maxWeight, type, trayNumber);
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
                String type = res.getString("type_of_part");
                int packageNumber = res.getInt("package_no");
                int trayNumber = res.getInt("tray_no");

                tray = new Tray(maxWeight, type, trayNumber);
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
    public int addTray(double max_weight, String animalPartType) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO slaughter_house.tray (max_weight, type_of_part) VALUES (?, ?) RETURNING slaughter_house.tray.tray_no as id");
            statement.setDouble(1, max_weight);
            statement.setString(2, animalPartType);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                trays.put(id, new Tray(max_weight, animalPartType, id));
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

    @Override
    public List<Tray> getAllTraysInPackage(int packageNumber) {
        List<Tray> returnList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.trayinpackage WHERE package_no = ?");
            statement.setInt(1, packageNumber);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                double maxWeight = res.getDouble("max_weight");
                String type = res.getString("type_of_part");
                int trayNumber = res.getInt("tray_no");

                Tray tray = new Tray(maxWeight, type, trayNumber);
                returnList.add(tray);
                trays.put(trayNumber, tray);
            }

            return returnList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
