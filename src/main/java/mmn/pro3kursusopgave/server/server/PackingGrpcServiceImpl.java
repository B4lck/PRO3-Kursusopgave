package mmn.pro3kursusopgave.server.server;

import io.grpc.stub.StreamObserver;
import mmn.pro3kursusopgave.*;
import mmn.pro3kursusopgave.server.model.AnimalManager;
import mmn.pro3kursusopgave.server.model.AnimalPartManager;
import mmn.pro3kursusopgave.server.model.PackageManager;
import mmn.pro3kursusopgave.server.model.TrayManager;
import mmn.pro3kursusopgave.server.model.entities.Tray;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackingGrpcServiceImpl extends PackingServiceGrpc.PackingServiceImplBase {
    private final AnimalManager animalManager;
    private final AnimalPartManager animalPartManager;
    private final PackageManager packageManager;
    private final TrayManager trayManager;

    public PackingGrpcServiceImpl(AnimalManager animalManager, AnimalPartManager animalPartManager, PackageManager packageManager, TrayManager trayManager) {
        this.animalManager = animalManager;
        this.animalPartManager = animalPartManager;
        this.packageManager = packageManager;
        this.trayManager = trayManager;
    }

    @Override
    public void createPackage(CreatePackageRequest request, StreamObserver<CreatePackageResponse> responseStreamObserver) {
        CreatePackageResponse.Builder res = CreatePackageResponse.newBuilder();

        int packageId = packageManager.addPackage(request.getExpireDate());

        DTOPackage.Builder returnDto = DTOPackage.newBuilder()
                .setPackageNo(packageId)
                .setExpireDate(request.getExpireDate());

        for (int trayInPackage : request.getTraysList()) {
            packageManager.addTrayToPackage(trayInPackage, packageId);
            returnDto.addTrays(trayInPackage);
        }

        res.setPackage(returnDto.build());

        responseStreamObserver.onNext(res.build());
        responseStreamObserver.onCompleted();
    }

    @Override
    public void getAllTraysNotInPackage(Empty request, StreamObserver<GetAllTraysNotInPackageResponse> responseStreamObserver) {
        GetAllTraysNotInPackageResponse.Builder res = GetAllTraysNotInPackageResponse.newBuilder();

        List<Tray> traysNotInPackage = trayManager.getAllTraysNotInPackage();

        for (var tray : traysNotInPackage) {
            res.addTrays(tray.getTrayNumber());
        }

        responseStreamObserver.onNext(res.build());
        responseStreamObserver.onCompleted();
    }

}
