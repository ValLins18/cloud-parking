package api.veiculos.service;

import api.veiculos.controller.dto.ParkingDTO;
import api.veiculos.model.Parking;
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

static{
    var id = getUUID();
    var id1 = getUUID();
    var id2 = getUUID();
    Parking parking = new Parking(id, "dms-111", "SC", "Celta", "preto");
    Parking parking1 = new Parking(id1, "dms-222", "SP", "Celta", "azul");
    Parking parking2 = new Parking(id2, "dms-333", "BA", "Celta", "vermelho");
    parkingMap.put(id, parking);
    parkingMap.put(id1, parking1);
    parkingMap.put(id2, parking2);
}

public List<Parking> findAll(){
    return parkingMap.values().stream().collect(Collectors.toList());
}
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Parking findById(String id) {
        return parkingMap.get(id);
    }


    public Parking save(Parking parkingCreate) {
        var newID = getUUID();
        parkingCreate.setId(newID);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingMap.put(newID, parkingCreate);
        return parkingCreate;
    }
}
