package cz.cvut.fit.tjv.musicalevent.client.service;

import cz.cvut.fit.tjv.musicalevent.client.api_client.BandClient;
import cz.cvut.fit.tjv.musicalevent.client.entity.BandDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandService {
    private BandClient bandClient;
    public BandService(BandClient bandClient){
        this.bandClient = bandClient;
    }

    public void create(BandDTO bandDTO){
        bandClient.create(bandDTO);
    }

    public List<BandDTO> readAll(){
        return bandClient.readAll();
    }

    public BandDTO readById(Long id){
        return bandClient.readById(id);
    }

    public void update(BandDTO bandDTO){
        bandClient.update(bandDTO);
    }

    public void delete(Long id){
        bandClient.delete(id);
    }

    public void addToMusicalEvent(Long bandId, Long eventId){
        bandClient.addToMusicalEvent(bandId, eventId);
    }
    public void removeFromMusicalEvent(Long bandId, Long eventId){
        bandClient.removeFromMusicalEvent(bandId, eventId);
    }

}
