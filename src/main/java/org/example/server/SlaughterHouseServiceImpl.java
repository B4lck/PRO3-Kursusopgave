package org.example.server;

import io.grpc.stub.StreamObserver;
import org.example.DTOAnimal;
import org.example.GetAllAnimalsInvolvedInProductRequest;
import org.example.GetAllAnimalsInvolvedInProductResponse;
import org.example.SlaughterHouseServiceGrpc;
import org.example.model.AnimalManager;
import org.example.model.AnimalPartManager;
import org.example.model.PackageManager;
import org.example.model.TrayManager;
import org.example.model.entities.Animal;

import java.util.List;

public class SlaughterHouseServiceImpl extends SlaughterHouseServiceGrpc.SlaughterHouseServiceImplBase
{
    private final AnimalManager animalManager;
    private final AnimalPartManager animalPartManager;
    private final PackageManager packageManager;
    private final TrayManager trayManager;

    public SlaughterHouseServiceImpl(AnimalManager animalManager, AnimalPartManager animalPartManager, PackageManager packageManager, TrayManager trayManager) {
        this.animalManager = animalManager;
        this.animalPartManager = animalPartManager;
        this.packageManager = packageManager;
        this.trayManager = trayManager;
    }

    @Override
    public void getAllAnimalsInvolvedInProduct(GetAllAnimalsInvolvedInProductRequest request,
                                               StreamObserver<GetAllAnimalsInvolvedInProductResponse> responseObserver) {
        GetAllAnimalsInvolvedInProductResponse.Builder response = GetAllAnimalsInvolvedInProductResponse.newBuilder();

        List<Animal> animals = animalManager.getAllAnimalsInProduct(request.getProductId());
        for (Animal animal : animals) {
            var animalDTO = DTOAnimal.newBuilder()
                    .setAnimalNo(animal.getAnimalId())
                    .setTypeOfAnimal(animal.getAnimalType())
                    .setWeight(animal.getWeight())
                    .build();
            response.addAnimals(animalDTO);
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
