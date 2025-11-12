package mmn.pro3kursusopgave.server.server;

import io.grpc.stub.StreamObserver;
import mmn.pro3kursusopgave.DTOAnimal;
import mmn.pro3kursusopgave.GetAllAnimalsInvolvedInProductRequest;
import mmn.pro3kursusopgave.GetAllAnimalsInvolvedInProductResponse;
import mmn.pro3kursusopgave.SlaughterHouseServiceGrpc;
import mmn.pro3kursusopgave.server.model.AnimalManager;
import mmn.pro3kursusopgave.server.model.AnimalPartManager;
import mmn.pro3kursusopgave.server.model.PackageManager;
import mmn.pro3kursusopgave.server.model.TrayManager;
import mmn.pro3kursusopgave.server.model.entities.Animal;

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
