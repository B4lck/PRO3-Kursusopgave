package mmn.pro3kursusopgave.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import mmn.pro3kursusopgave.server.model.*;
import mmn.pro3kursusopgave.server.server.SlaughterHouseServiceImpl;

public class Main {
    public static void main(String[] args) {
        AnimalManager animalManager = new AnimalManagerDB();
        AnimalPartManager animalPartManager = new AnimalPartManagerDB();
        PackageManager packageManager = new PackageManagerDB();
        TrayManager trayManager = new TrayManagerDB();

        Server server = ServerBuilder.forPort(6969).addService(new SlaughterHouseServiceImpl(animalManager, animalPartManager, packageManager, trayManager)).build();
        try {
            System.out.println("grpc server starter");
            server.start();
            server.awaitTermination();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}