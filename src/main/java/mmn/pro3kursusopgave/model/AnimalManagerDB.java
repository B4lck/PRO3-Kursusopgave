package mmn.pro3kursusopgave.model;

import mmn.pro3kursusopgave.database.Database;
import mmn.pro3kursusopgave.model.entities.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

                Animal animal = new Animal(weight, type, id);
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

                animal = new Animal(weight, type, id);
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
    public int addAnimal(double weight, String type) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO slaughter_house.animal (weight, type_of_animal) VALUES (?, ?) RETURNING slaughter_house.animal.animal_no as id");
            statement.setDouble(1, weight);
            statement.setString(2, type);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                animals.put(id, new Animal(weight, type, id));
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
            PreparedStatement statement = connection.prepareStatement("SELECT slaughter_house.animal.animal_no as id, slaughter_house.animal.type_of_animal as type, slaughter_house.animal.weight as weight FROM slaughter_house.animal INNER JOIN slaughter_house.animalpart a on slaughter_house.animal.animal_no = a.from_animal INNER JOIN slaughter_house.tray t on t.tray_no = a.tray_no WHERE t.package_no = ?;");
            statement.setInt(1, packageId);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                double weight = res.getDouble("weight");
                String type = res.getString("type");
                int animalId = res.getInt("id");

                Animal animal = new Animal(weight, type, animalId);
                returnList.add(animal);

                animals.put(animalId, animal);
            }

            return returnList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
