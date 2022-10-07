package api.veiculos.service;

import api.veiculos.controller.dto.ParkingDTO;
import api.veiculos.exception.ParkingNotFoundException;
import api.veiculos.model.Parking;
import api.veiculos.repository.ParkingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional()
    public List<Parking> findAll(){
        return parkingRepository.findAll();
    }
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(() -> {
            return new ParkingNotFoundException(id);
        });
    }


    @Transactional
    public Parking save(Parking parkingCreate) {
        var newID = getUUID();
        parkingCreate.setId(newID);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingRepository.save(parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public void delete(String id) {
        Parking parking = findById(id);
        parkingRepository.deleteById(id);
    }
    @Transactional
    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setColor(parkingCreate.getLicense());
        parking.setColor(parkingCreate.getModel());
        parking.setColor(parkingCreate.getState());
        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public Parking checkout(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckout.getBill(parking));
        parkingRepository.save(parking);
        return parking;
    }
}
