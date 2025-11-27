package mmn.pro3kursusopgave.server.model;

import mmn.pro3kursusopgave.server.database.Database;
import mmn.pro3kursusopgave.server.model.entities.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see AnimalManager
 */
public class AnimalManagerDB implements AnimalManager
{
    private final Connection connection = Database.getConnection();

    /**
     * Lokal cache over dyr.
     */
    private final Map<Integer, Animal> animals = new HashMap<>();

    @Override
    public List<Animal> getAllAnimals() {
        List<Animal> list = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.animal");
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                double weight = res.getDouble("weight");
                String type = res.getString("type_of_animal");
                int id = res.getInt("animal_no");
                String origin = res.getString("origin");
                long dateTime = res.getLong("dateTime");

                Animal animal = new Animal(weight, type, id, origin, dateTime);
                animals.put(id, animal);
                list.add(animal);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     * Hvis dyret er cached, returner den dyret fra cachen.
     */
    @Override
    public Animal getAnimal(int id) {
        Animal animal = animals.get(id);
        if (animal != null)
            return animal;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.animal WHERE animal_no=?");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                double weight = res.getDouble("weight");
                String type = res.getString("type_of_animal");
                String origin = res.getString("origin");
                long dateTime = res.getLong("dateTime");

                animal = new Animal(weight, type, id, origin, dateTime);
                animals.put(id, animal);
                return animal;
            } else {
                throw new RuntimeException("Animal does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addAnimal(double weight, String type, String origin, long dateTime) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO slaughter_house.animal (weight, type_of_animal, origin, datetime) VALUES (?, ?, ?, ?) RETURNING slaughter_house.animal.animal_no as id");
            statement.setDouble(1, weight);
            statement.setString(2, type);
            statement.setString(3, origin);
            statement.setLong(4, dateTime);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                animals.put(id, new Animal(weight, type, id, origin, dateTime));
                return id;
            } else {
                throw new RuntimeException("Kunne ikke indsætte");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAnimal(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM slaughter_house.animal WHERE animal_no=? RETURNING slaughter_house.animal.animal_no AS id");
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();

            if (res.next()) {
                animals.remove(id);
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
    public List<Animal> getAllAnimalsInProduct(int packageId) {
        List<Animal> returnList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT slaughter_house.animal.animal_no as id, slaughter_house.animal.type_of_animal as type, slaughter_house.animal.weight as weight, slaughter_house.animal.origin as origin, slaughter_house.animal.dateTime as dateTime FROM slaughter_house.animal INNER JOIN slaughter_house.animalpart a on slaughter_house.animal.animal_no = a.from_animal INNER JOIN slaughter_house.trayinpackage t on t.tray_no = a.tray_no WHERE t.package_no = ?;");
            statement.setInt(1, packageId);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                double weight = res.getDouble("weight");
                String type = res.getString("type");
                int animalId = res.getInt("id");
                String origin = res.getString("origin");
                long dateTime = res.getLong("dateTime");

                Animal animal = new Animal(weight, type, animalId, origin, dateTime);
                returnList.add(animal);

                animals.put(animalId, animal);
            }

            return returnList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Animal> getAllAnimalsFromDate(long dateTime) {
        List<Animal> returnList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.animal WHERE slaughter_house.animal.datetime = ?;");
            statement.setLong(1, dateTime);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                double weight = res.getDouble("weight");
                String type = res.getString("type_of_animal");
                int animalId = res.getInt("animal_no");
                String origin = res.getString("origin");

                Animal animal = new Animal(weight, type, animalId, origin, dateTime);
                returnList.add(animal);

                animals.put(animalId, animal);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return returnList;
    }

    @Override
    public List<Animal> getAllAnimalsFromOrigin(String origin) {
        List<Animal> returnList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM slaughter_house.animal WHERE slaughter_house.animal.origin = ?;");
            statement.setString(1, origin);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                double weight = res.getDouble("weight");
                String type = res.getString("type_of_animal");
                int animalId = res.getInt("animal_no");
                long dateTime = res.getLong("datetime");

                Animal animal = new Animal(weight, type, animalId, origin, dateTime);
                returnList.add(animal);

                animals.put(animalId, animal);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return returnList;
    }
}
