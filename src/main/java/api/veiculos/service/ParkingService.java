package api.veiculos.service;

import api.veiculos.controller.dto.ParkingDTO;
import api.veiculos.exception.ParkingNotFoundException;
import api.veiculos.model.Parking;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    private static Map<String, Parking>  parkingMap = new HashMap();


public List<Parking> findAll(){
    return parkingMap.values().stream().collect(Collectors.toList());
}
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Parking findById(String id) {
    Parking parking = parkingMap.get(id);
        if(parking == null) {
            throw new ParkingNotFoundException(id);
        }
    return parking;
    }


    public Parking save(Parking parkingCreate) {
        var newID = getUUID();
        parkingCreate.setId(newID);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingMap.put(newID, parkingCreate);
        return parkingCreate;
    }

    public void delete(String id) {
        Parking parking = findById(id);
        parkingMap.remove(id);
    }

    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setColor(parkingCreate.getLicense());
        parking.setColor(parkingCreate.getModel());
        parking.setColor(parkingCreate.getState());
        parkingMap.replace(id, parking);
        return parking;
    }
}
