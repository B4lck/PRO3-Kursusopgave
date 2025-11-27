package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.database.Database;
import mmn.pro3kursusopgave.server.model.entities.AnimalPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see AnimalPartManager
 */
public class AnimalPartManagerDB implements AnimalPartManager{
    private final Connection connection = Database.getConnection();

    /**
     * Lokal cache over delene af dyr fra databasen.
     */
    private final Map<Integer, AnimalPart> animalParts = new HashMap<>();

    @Override
    public List<AnimalPart> getAllAnimalParts() {
        List<AnimalPart> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.animalpart");
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                double weight = res.getDouble("weight");
                int trayNumber = res.getInt("tray_no");
                int fromAnimal = res.getInt("from_animal");
                int id = res.getInt("part_id");
                String typeOfPart = res.getString("type_of_part");
                long cuttingDate = res.getLong("cutting_date");

                AnimalPart animal = new AnimalPart(weight, trayNumber, fromAnimal, id, typeOfPart, cuttingDate);
                animalParts.put(id, animal);
                list.add(animal);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     * Hvis delen er gemt i cachen, returner den fra cachen.
     */
    @Override
    public AnimalPart getPart(int id) {
        AnimalPart animal = animalParts.get(id);
        if (animal != null)
            return animal;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.animalpart WHERE part_id = ?");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                double weight = res.getDouble("weight");
                int trayNumber = res.getInt("tray_no");
                int fromAnimal = res.getInt("from_animal");
                int partId = res.getInt("part_id");
                String typeOfPart = res.getString("type_of_part");
                long cuttingDate  = res.getLong("cutting_date");

                animal = new AnimalPart(weight, trayNumber, fromAnimal, partId, typeOfPart, cuttingDate);
                animalParts.put(partId, animal);
                return animal;
            }

            throw new RuntimeException("AnimalPart with id " + id + " does not exist");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AnimalPart> getAllPartsFromParent(int id) {
        List<AnimalPart> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.animalpart WHERE from_animal = ?");
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                double weight = res.getDouble("weight");
                int trayNumber = res.getInt("tray_no");
                int fromAnimal = res.getInt("from_animal");
                int partId = res.getInt("part_id");
                String typeOfPart = res.getString("type_of_part");
                long cuttingDate = res.getLong("cutting_date");

                AnimalPart animal = new AnimalPart(weight, trayNumber, fromAnimal, partId, typeOfPart, cuttingDate);
                animalParts.put(partId, animal);
                list.add(animal);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addAnimalPart(double weight, int tray, int fromAnimal, String typeOfPart, long cuttingDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO slaughter_house.animalpart (weight, tray_no, from_animal, type_of_part, cutting_date) VALUES (?, ?, ?, ?, ?) RETURNING slaughter_house.animalpart.part_id as id");
            statement.setDouble(1, weight);
            statement.setInt(2, tray);
            statement.setInt(3, fromAnimal);
            statement.setString(4, typeOfPart);
            statement.setLong(5, cuttingDate);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                animalParts.put(id, new AnimalPart(weight, tray, fromAnimal, id, typeOfPart, cuttingDate));
                return id;
            }

            throw new RuntimeException("Dyr kunne ikke indsætte");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAnimalPart(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM slaughter_house.animalpart WHERE part_id=? RETURNING slaughter_house.animalpart.part_id");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                animalParts.remove(id);
                return id;
            }

            throw new RuntimeException("Animal part with id: " + id + " does not exist");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
