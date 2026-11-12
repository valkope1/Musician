package cz.cvut.fit.tjv.musicalevent.client.service;

import cz.cvut.fit.tjv.musicalevent.client.api_client.MusicalEventClient;
import cz.cvut.fit.tjv.musicalevent.client.entity.MusicalEventDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicalEventService {
    private MusicalEventClient musicalEventClient;
    public MusicalEventService(MusicalEventClient musicalEventClient){
        this.musicalEventClient = musicalEventClient;
    }
    public void create(MusicalEventDTO musicalEventDTO){
        musicalEventClient.create(musicalEventDTO);
    }

    public List<MusicalEventDTO> readAll(){
        return musicalEventClient.readAll();
    }

    public MusicalEventDTO readById(Long id){
        return musicalEventClient.readById(id);
    }

    public void update(MusicalEventDTO musicalEventDTO){
        musicalEventClient.update(musicalEventDTO);
    }

    public void delete(Long id){
        musicalEventClient.delete(id);
    }

    public List<Long> getAllId(){
        List<MusicalEventDTO> tmp = readAll();
        return tmp.stream().map(MusicalEventDTO::getMusicalEventId).collect(Collectors.toList());
    }

    public List<MusicalEventDTO> getAfterDate(LocalDate date){
        return musicalEventClient.getAfterDate(date);
    }
}
