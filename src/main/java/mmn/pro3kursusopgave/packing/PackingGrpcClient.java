package mmn.pro3kursusopgave.packing;

import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import mmn.pro3kursusopgave.*;

import java.util.ArrayDeque;
import java.util.Queue;

public class PackingGrpcClient {

    private final PackingServiceGrpc.PackingServiceBlockingStub stub;

    private final Queue<PackageInfo> packageQueue = new ArrayDeque<>();

    public PackingGrpcClient() {
        var channel = ManagedChannelBuilder.forAddress("localhost", 6969).usePlaintext().build();
        stub = PackingServiceGrpc.newBlockingStub(channel);
    }

    public boolean createPackage(PackageInfo packageInfo) {
        if (packageInfo != null) {
            packageQueue.add(packageInfo);
        }

        if (packageQueue.isEmpty()) {
            System.out.println("Ingen package(s) at gemme");
            return true;
        }

        try {
            try {
                while (!packageQueue.isEmpty()) {
                    PackageInfo packageToUpload = packageQueue.peek();

                    var req = CreatePackageRequest.newBuilder()
                            .setExpireDate(packageToUpload.getExpireDate())
                            .addAllTrays(packageToUpload.getTrays())
                            .build();

                    var res = stub.createPackage(req);

                    packageQueue.remove();
                }

                return true;
            } catch (StatusRuntimeException e) {
                if (e.getStatus().getCode() != Status.Code.UNAVAILABLE) throw e;
            }
        } catch (Exception e) {
            packageQueue.remove();
            e.printStackTrace();
        }

        return false;
    }

    public int getQueueSize() {
        return packageQueue.size();
    }
}
