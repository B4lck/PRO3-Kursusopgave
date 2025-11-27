package mmn.pro3kursusopgave.server.server;

import io.grpc.stub.StreamObserver;
import mmn.pro3kursusopgave.*;
import mmn.pro3kursusopgave.server.model.AnimalManager;
import mmn.pro3kursusopgave.server.model.AnimalPartManager;
import mmn.pro3kursusopgave.server.model.PackageManager;
import mmn.pro3kursusopgave.server.model.TrayManager;
import mmn.pro3kursusopgave.server.model.entities.Animal;
import org.springframework.stereotype.Service;

@Service
public class CuttingGrpcServiceImpl extends CuttingServiceGrpc.CuttingServiceImplBase {
    private final AnimalManager animalManager;
    private final AnimalPartManager animalPartManager;
    private final PackageManager packageManager;
    private final TrayManager trayManager;

    public CuttingGrpcServiceImpl(AnimalManager animalManager, AnimalPartManager animalPartManager, PackageManager packageManager, TrayManager trayManager) {
        this.animalManager = animalManager;
        this.animalPartManager = animalPartManager;
        this.packageManager = packageManager;
        this.trayManager = trayManager;
    }

    @Override
    public void addAnimalPart(AddAnimalPartRequest request, StreamObserver<AddAnimalPartResponse> responseStreamObserver) {
        AddAnimalPartResponse.Builder res = AddAnimalPartResponse.newBuilder();

        int animalPartId = animalPartManager.addAnimalPart(request.getWeight(), request.getTray(), request.getFromAnimal(), request.getDescription(), request.getCuttingDate());

        Animal fromAnimal = animalManager.getAnimal(request.getFromAnimal());
        DTOAnimal fromDTOAnimal = DTOAnimal.newBuilder()
                .setTypeOfAnimal(fromAnimal.getAnimalType())
                .setAnimalNo(fromAnimal.getAnimalId())
                .setOrigin(fromAnimal.getOrigin())
                .setDateTime(fromAnimal.getDateTime())
                .build();

        res.setAnimalPart(DTOAnimalPart.newBuilder()
                        .setWeight(request.getWeight())
                        .setTrayNo(request.getTray())
                        .setFrom(fromDTOAnimal)
                        .setPartId(animalPartId)
                        .setDescription(request.getDescription())
                        .setCuttingDate(request.getCuttingDate())
                        .build());

        responseStreamObserver.onNext(res.build());
        responseStreamObserver.onCompleted();
    }

    @Override
    public void createTray(CreateTrayRequest request, StreamObserver<CreateTrayResponse> responseStreamObserver) {
        CreateTrayResponse.Builder res = CreateTrayResponse.newBuilder();

        int trayId = trayManager.addTray(request.getMaxWeight(), request.getType());

        DTOTray returnTray = DTOTray.newBuilder()
                .setTrayNo(trayId)
                .setMaxWeight(request.getMaxWeight())
                .setTypeOfAnimal(request.getType())
                .build();

        res.setTray(returnTray);

        responseStreamObserver.onNext(res.build());
        responseStreamObserver.onCompleted();
    }
}
