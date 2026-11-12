package cz.cvut.fit.tjv.musicalevent.client.api_client;

import cz.cvut.fit.tjv.musicalevent.client.entity.MusicalEventDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class MusicalEventClient {
    private RestClient musicalEventRestClient;
    public MusicalEventClient(){
        this.musicalEventRestClient = RestClient.create("http://localhost:8080/musicalevents");
    }
    public void create(MusicalEventDTO musicalEventDTO){
        musicalEventRestClient.post()
                .body(musicalEventDTO)
                .retrieve()
                .toBodilessEntity();
    }

    public List<MusicalEventDTO> readAll(){
        return Arrays.asList(musicalEventRestClient.get()
                .retrieve()
                .toEntity(MusicalEventDTO[].class)
                .getBody());
    }

    public MusicalEventDTO readById(Long id){
        return musicalEventRestClient.get()
                .uri("/{id}", id)
                .retrieve()
                .toEntity(MusicalEventDTO.class)
                .getBody();
    }

    public void update(MusicalEventDTO musicalEventDTO){
        try{
            musicalEventRestClient.put()
                    .body(musicalEventDTO)
                    .retrieve()
                    .toBodilessEntity();
        }catch(HttpClientErrorException e){
            //todo
        }
    }

    public void delete(Long id){
        musicalEventRestClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public List<MusicalEventDTO> getAfterDate(LocalDate date){
        return Arrays.asList(musicalEventRestClient.get()
                        .uri("/afterdate?date={date}", date)
                .retrieve()
                .toEntity(MusicalEventDTO[].class)
                .getBody());
    }
}
