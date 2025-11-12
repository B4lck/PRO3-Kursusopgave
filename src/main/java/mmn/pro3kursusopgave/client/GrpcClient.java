package mmn.pro3kursusopgave.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import mmn.pro3kursusopgave.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GrpcClient {

    private final SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub;
    private final ManagedChannel channel;

    public GrpcClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 6969).usePlaintext().build();
        stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);
    }

    public List<DTOAnimal> getAllAnimalsInvolvedInProduct(int productId) {
        GetAllAnimalsInvolvedInProductRequest req = GetAllAnimalsInvolvedInProductRequest.newBuilder().setProductId(productId).build();

        GetAllAnimalsInvolvedInProductResponse res = stub.getAllAnimalsInvolvedInProduct(req);

        return res.getAnimalsList();
    }

    public List<DTOAnimal> getAllAnimalsFromDate(long dateTime) {
        GetAllAnimalsFromDateRequest req = GetAllAnimalsFromDateRequest.newBuilder().setDateTime(dateTime).build();

        GetAllAnimalsFromDateResponse res = stub.getAllAnimalsFromDate(req);

        return res.getAnimalsList();
    }

    public List<DTOAnimal> getAllAnimalsFromOrigin(String origin) {
        GetAllAnimalsFromOriginRequest req = GetAllAnimalsFromOriginRequest.newBuilder().setOrigin(origin).build();

        GetAllAnimalsFromOriginResponse res = stub.getAllAnimalsFromOrigin(req);

        return res.getAnimalsList();
    }
}
