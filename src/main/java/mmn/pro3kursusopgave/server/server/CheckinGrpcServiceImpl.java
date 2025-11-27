package mmn.pro3kursusopgave.server.server;


import io.grpc.stub.StreamObserver;
import mmn.pro3kursusopgave.CheckinServiceGrpc;
import mmn.pro3kursusopgave.DTOAnimal;
import mmn.pro3kursusopgave.RegisterAnimalRequest;
import mmn.pro3kursusopgave.RegisterAnimalResponse;
import mmn.pro3kursusopgave.server.model.AnimalManager;
import mmn.pro3kursusopgave.server.model.AnimalPartManager;
import mmn.pro3kursusopgave.server.model.PackageManager;
import mmn.pro3kursusopgave.server.model.TrayManager;
import mmn.pro3kursusopgave.server.model.entities.Animal;

public class CheckinGrpcServiceImpl extends CheckinServiceGrpc.CheckinServiceImplBase {
    private final AnimalManager animalManager;
    private final AnimalPartManager animalPartManager;
    private final PackageManager packageManager;
    private final TrayManager trayManager;

    public CheckinGrpcServiceImpl(AnimalManager animalManager, AnimalPartManager animalPartManager, PackageManager packageManager, TrayManager trayManager) {
        this.animalManager = animalManager;
        this.animalPartManager = animalPartManager;
        this.packageManager = packageManager;
        this.trayManager = trayManager;
    }

    @Override
    public void registerAnimal(RegisterAnimalRequest request, StreamObserver<RegisterAnimalResponse> responseStreamObserver) {
        RegisterAnimalResponse.Builder res = RegisterAnimalResponse.newBuilder();

        int animalId = animalManager.addAnimal( request.getWeight(),
                                                request.getType(),
                                                request.getOrigin(),
                                                request.getDate());

        Animal animal = animalManager.getAnimal(animalId);

        res.setAnimal(DTOAnimal.newBuilder()
                .setDateTime(animal.getDateTime())
                .setOrigin(animal.getOrigin())
                .setAnimalNo(animalId)
                .setTypeOfAnimal(animal.getAnimalType())
                .build());

        responseStreamObserver.onNext(res.build());
        responseStreamObserver.onCompleted();
    }
}
