package cz.cvut.fit.tjv.musicalevent.client.api_client;

import cz.cvut.fit.tjv.musicalevent.client.entity.MusicianDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class MusicianClient {
    private RestClient musicianRestClient;
    public MusicianClient(){
        this.musicianRestClient = RestClient.create("http://localhost:8080/musicians");
    }

    public void create(MusicianDTO musicianDTO){
        musicianRestClient.post()
                .body(musicianDTO)
                        .retrieve()
                .toBodilessEntity();
    }

    public List<MusicianDTO> readAll(){
        return Arrays.asList(musicianRestClient.get()
                .retrieve()
                .toEntity(MusicianDTO[].class)
                .getBody());
    }

    public MusicianDTO readById(Long id){
        return musicianRestClient.get()
                .uri("/{id}", id)
                .retrieve()
                .toEntity(MusicianDTO.class)
                .getBody();
    }
    public void update(MusicianDTO musicianDTO){
        try {
            musicianRestClient.put()
                    .body(musicianDTO)
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException e){
            System.out.println("PROSIM");
        }
    }

    public void delete(Long id){
        musicianRestClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

}
