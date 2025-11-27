package mmn.pro3kursusopgave.cutting;

import io.grpc.ManagedChannelBuilder;
import mmn.pro3kursusopgave.*;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class CuttingGrpcClient {

    private final CuttingServiceGrpc.CuttingServiceBlockingStub stub;

    private final Queue<AddAnimalPartRequest> animalPartRequests;
    private final Queue<CreateTrayRequest> trayQueue;

    public CuttingGrpcClient () {
        var channel = ManagedChannelBuilder.forAddress("localhost", 6969).usePlaintext().build();
        stub = CuttingServiceGrpc.newBlockingStub(channel);

        animalPartRequests = new ArrayDeque<>();
        trayQueue = new ArrayDeque<>();
    }

    public boolean addAnimalPart(double weight, int tray, int fromAnimal, String description, long cuttingDate) {
        AddAnimalPartRequest req = AddAnimalPartRequest.newBuilder()
                .setWeight(weight)
                .setTray(tray)
                .setFromAnimal(fromAnimal)
                .setDescription(description)
                .setCuttingDate(cuttingDate)
                .build();

        animalPartRequests.add(req);

        try {
            while (!animalPartRequests.isEmpty()) {
                var res = stub.addAnimalPart(animalPartRequests.peek());
                animalPartRequests.remove();
                System.out.println("Sender dyredel: " + res.getAnimalPart());
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createTray(double maxWeight, String type) {
        CreateTrayRequest req = CreateTrayRequest.newBuilder()
                .setMaxWeight(maxWeight)
                .setType(type)
                .build();

        trayQueue.add(req);

        try {
            while (!trayQueue.isEmpty()) {
                var res = stub.createTray(trayQueue.peek());
                trayQueue.remove();
                System.out.println("Opretter tray med type: " + res.getTray().getTypeOfAnimal());
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
