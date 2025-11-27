package mmn.pro3kursusopgave.checkin;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import mmn.pro3kursusopgave.*;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class CheckinGrpcClient {

    private final CheckinServiceGrpc.CheckinServiceBlockingStub stub;

    private Queue<RegisterAnimalRequest> requestQueue;

    public CheckinGrpcClient () {
        var channel = ManagedChannelBuilder.forAddress("localhost", 6969).usePlaintext().build();
        stub = CheckinServiceGrpc.newBlockingStub(channel);

        requestQueue = new ArrayDeque<>();
    }

    public boolean registerAnimal(double weight, String type, String origin, long date) {
        RegisterAnimalRequest req = RegisterAnimalRequest.newBuilder()
                    .setWeight(weight)
                    .setType(type)
                    .setOrigin(origin)
                    .setDate(date)
                    .build();
        requestQueue.add(req);
        try {
            try {
                while (!requestQueue.isEmpty()) {
                    var res = stub.registerAnimal(requestQueue.peek());
                    requestQueue.remove();
                }
                return true;

            } catch (StatusRuntimeException e) {
                if (e.getStatus().getCode() != Status.Code.UNAVAILABLE) throw e;
            }
        } catch (Exception e) {
            requestQueue.remove();
            e.printStackTrace();
        }

        return false;
    }
}
