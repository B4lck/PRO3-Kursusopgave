package org.example.model;

import org.example.database.Database;
import org.example.model.entities.Animal;
import org.example.model.entities.AnimalPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalPartManagerDB implements AnimalPartManager{
    private Connection connection;

    private Map<Integer, AnimalPart> animalParts = new HashMap<>();

    public AnimalPartManagerDB() {
        try {
            connection = Database.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
                String description = res.getString("description");

                AnimalPart animal = new AnimalPart(weight, trayNumber, fromAnimal, id, description);
                animalParts.put(id, animal);
                list.add(animal);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
                String description = res.getString("description");

                animal = new AnimalPart(weight, trayNumber, fromAnimal, partId, description);
                animalParts.put(partId, animal);
                return animal;
            } else {
                throw new RuntimeException("Animal does not exist");
            }
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
                String description = res.getString("description");

                AnimalPart animal = new AnimalPart(weight, trayNumber, fromAnimal, partId, description);
                animalParts.put(partId, animal);
                list.add(animal);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addAnimalPart(double weight, int tray, int fromAnimal, String description) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO slaughter_house.animalpart (weight, tray_no, from_animal, description) VALUES (?, ?, ?, ?) RETURNING slaughter_house.animalpart.part_id as id");
            statement.setDouble(1, weight);
            statement.setInt(2, tray);
            statement.setInt(3, fromAnimal);
            statement.setString(4, description);

            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                animalParts.put(id, new AnimalPart(weight, tray, fromAnimal, id, description));
                return id;
            } else {
                throw new RuntimeException("Kunne ikke indsætte");
            }
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
            else {
                throw new RuntimeException("Findes ikke");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
