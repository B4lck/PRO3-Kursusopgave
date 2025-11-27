package mmn.pro3kursusopgave.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import mmn.pro3kursusopgave.server.model.*;
import mmn.pro3kursusopgave.server.server.CheckinGrpcServiceImpl;
import mmn.pro3kursusopgave.server.server.CuttingGrpcServiceImpl;
import mmn.pro3kursusopgave.server.server.PackingGrpcServiceImpl;
import mmn.pro3kursusopgave.server.server.SlaughterHouseServiceImpl;

public class Main {
    public static void main(String[] args) {
        AnimalManager animalManager = new AnimalManagerDB();
        AnimalPartManager animalPartManager = new AnimalPartManagerDB();
        TrayManager trayManager = new TrayManagerDB();
        PackageManager packageManager = new PackageManagerDB(trayManager);

        SlaughterHouseServiceImpl webApiGrpcService = new SlaughterHouseServiceImpl(animalManager, animalPartManager, packageManager, trayManager);
        CheckinGrpcServiceImpl checkinGrpcService = new CheckinGrpcServiceImpl(animalManager, animalPartManager, packageManager, trayManager);
        CuttingGrpcServiceImpl cuttingGrpcService = new CuttingGrpcServiceImpl(animalManager, animalPartManager, packageManager, trayManager);
        PackingGrpcServiceImpl packingGrpcService = new PackingGrpcServiceImpl(animalManager, animalPartManager, packageManager, trayManager);

        Server server = ServerBuilder.forPort(6969).addService(webApiGrpcService).addService(checkinGrpcService).addService(cuttingGrpcService).addService(packingGrpcService).build();
        try {
            System.out.println("grpc server starter");
            server.start();
            server.awaitTermination();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}