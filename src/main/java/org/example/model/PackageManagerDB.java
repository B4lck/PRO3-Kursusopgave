package org.example.model;

import org.example.model.entities.Animal;
import org.example.model.entities.Package;

import java.time.LocalDate;
import java.util.List;

public class PackageManagerDB implements PackageManager{
    @Override
    public List<Package> getAllPackages() {
        return List.of();
    }

    @Override
    public Package getPackage(int id) {
        return null;
    }

    @Override
    public void addPackage(LocalDate expireDate) {

    }

    @Override
    public void removePackage(int id) {

    }

    @Override
    public List<Animal> getAllAnimalsInProduct() {
        return List.of();
    }
}
