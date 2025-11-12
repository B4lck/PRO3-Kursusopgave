package mmn.pro3kursusopgave.client;


import mmn.pro3kursusopgave.DTOAnimal;

import mmn.pro3kursusopgave.client.responses.AnimalResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SlaughterHouseController {

    private final GrpcClient grpcClient;

    public SlaughterHouseController(GrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    @GetMapping("/animalsFromProduct/{id}")
    public List<AnimalResponse> getAllAnimals(@PathVariable int id) {
        List<DTOAnimal> animals = grpcClient.getAllAnimalsInvolvedInProduct(id);
        return animals.stream().map(a -> new AnimalResponse(a.getAnimalNo(), a.getWeight(), a.getTypeOfAnimal(), a.getOrigin(), a.getDateTime())).toList();
    }

    @GetMapping("/animalsFromDate")
    public List<AnimalResponse> getAllAnimalsFromDate(@RequestParam long fromDate) {
        List<DTOAnimal> animals = grpcClient.getAllAnimalsFromDate(fromDate);
        return animals.stream().map(a -> new AnimalResponse(a.getAnimalNo(), a.getWeight(), a.getTypeOfAnimal(), a.getOrigin(), a.getDateTime())).toList();
    }

    @GetMapping("/animalsFromOrigin")
    public List<AnimalResponse> getAllAnimalsFromOrigin(@RequestParam String origin) {
        List<DTOAnimal> animals = grpcClient.getAllAnimalsFromOrigin(origin);
        return animals.stream().map(a -> new AnimalResponse(a.getAnimalNo(), a.getWeight(), a.getTypeOfAnimal(), a.getOrigin(), a.getDateTime())).toList();
    }
}
