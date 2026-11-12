package cz.cvut.fit.tjv.musicalevent.client.service;

import cz.cvut.fit.tjv.musicalevent.client.api_client.MusicianClient;
import cz.cvut.fit.tjv.musicalevent.client.entity.MusicianDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicianService {
    private MusicianClient musicianClient;
    public MusicianService(MusicianClient musicianClient){
        this.musicianClient = musicianClient;
    }

    public void create(MusicianDTO musicianDTO){
        musicianClient.create(musicianDTO);

    }
    public List<MusicianDTO> readAll(){
        return musicianClient.readAll();
    }
    public MusicianDTO readById(Long id){
        return musicianClient.readById(id);
    }

    public void update(MusicianDTO musicianDTO){
        musicianClient.update(musicianDTO);

    }
    public void delete(Long id){
        musicianClient.delete(id);
    }
}
