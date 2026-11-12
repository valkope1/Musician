package cz.cvut.fit.tjv.musicalevent.client.api_client;

import cz.cvut.fit.tjv.musicalevent.client.entity.BandDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class BandClient {
    private RestClient bandRestClient;
    public BandClient(){
        this.bandRestClient = RestClient.create("http://localhost:8080/bands");
    }
    public void create(BandDTO bandDTO){
        bandRestClient.post()
                .body(bandDTO)
                .retrieve()
                .toBodilessEntity();
    }

    public List<BandDTO> readAll(){
        return Arrays.asList(bandRestClient.get()
                .retrieve()
                .toEntity(BandDTO[].class)
                .getBody());
    }

    public BandDTO readById(Long id){
        return bandRestClient.get()
                .uri("/{id}", id)
                .retrieve()
                .toEntity(BandDTO.class)
                .getBody();
    }

    public void update(BandDTO bandDTO){
        try{
            bandRestClient.put()
                    .body(bandDTO)
                    .retrieve()
                    .toBodilessEntity();
        }catch(HttpClientErrorException e){
            //todo
        }
    }

    public void delete(Long id){
        bandRestClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public void addToMusicalEvent(Long bandId, Long eventId){
        bandRestClient.post()
                .uri("/addtoevent?bandId={bandId}&eventId={eventId}", bandId, eventId)
                .retrieve()
                .toBodilessEntity();
    }
    public void removeFromMusicalEvent(Long bandId, Long eventId){
        bandRestClient.post()
                .uri("/removefromevent?bandId={bandId}&eventId={eventId}", bandId, eventId)
                .retrieve()
                .toBodilessEntity();
    }
}
