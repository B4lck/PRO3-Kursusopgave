package org.example.model;

import org.example.model.entities.Animal;
import org.example.model.entities.Package;

import java.time.LocalDate;
import java.util.List;

public interface PackageManager {
    List<Package> getAllPackages();
    Package getPackage(int id);
    int addPackage(LocalDate expireDate);
    int removePackage(int id);
}
