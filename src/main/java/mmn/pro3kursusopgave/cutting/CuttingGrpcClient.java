package mmn.pro3kursusopgave.cutting;

import io.grpc.ManagedChannelBuilder;
import mmn.pro3kursusopgave.*;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class CuttingGrpcClient {

    private final CuttingServiceGrpc.CuttingServiceBlockingStub stub;

    private final Queue<TrayInfo> trayQueue;

    public CuttingGrpcClient() {
        var channel = ManagedChannelBuilder.forAddress("localhost", 6969).usePlaintext().build();
        stub = CuttingServiceGrpc.newBlockingStub(channel);

        trayQueue = new ArrayDeque<>();
    }

    public boolean createTray(TrayInfo tray) {
        trayQueue.add(tray);

        try {
            while (!trayQueue.isEmpty()) {
                TrayInfo trayToUpload = trayQueue.peek();

                // 1. Lav tray
                int id;

                if (!tray.isUploaded()) {
                    var res = stub.createTray(
                            CreateTrayRequest.newBuilder()
                                    .setMaxWeight(trayToUpload.getMaxWeight())
                                    .setType(trayToUpload.getDescription())
                                    .build()
                    );

                    id = res.getTray().getTrayNo();

                    tray.setUploaded(id);
                }
                else {
                    id = tray.getUploadedId();
                }

                // 2. Tilføj parts
                for (PartInfo part : tray.getParts()) {
                    if (part.isUploaded()) continue;

                    var partRes = stub.addAnimalPart(
                            AddAnimalPartRequest.newBuilder()
                                    .setTray(id)
                                    .setCuttingDate(part.getCuttingDate())
                                    .setDescription(tray.getDescription())
                                    .setFromAnimal(part.getFromAnimal())
                                    .setWeight(part.getWeight())
                                    .build()
                    );

                    part.setUploaded();
                }

                trayQueue.remove();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
