package cz.cvut.fit.tjv.musicalevent.server.controller;

import cz.cvut.fit.tjv.musicalevent.server.domain.converter.MusicianDTOConverter;
import cz.cvut.fit.tjv.musicalevent.server.domain.dto.MusicianDTO;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import cz.cvut.fit.tjv.musicalevent.server.service.MusicianService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("musicians")
public class MusicianController {
    private final MusicianService musicianService;
    private final MusicianDTOConverter musicianDTOConverter;
    public MusicianController(MusicianService musicianService, MusicianDTOConverter musicianDTOConverter){
        this.musicianService = musicianService;
        this.musicianDTOConverter = musicianDTOConverter;
    }
    @GetMapping
    public List<MusicianDTO> getMusicians(){
        return musicianService.getMusicians().stream().map(musicianDTOConverter::toDTO).toList();
    }

    @GetMapping(path = "{id}")
    public MusicianDTO getMusician(@PathVariable("id") Long id){
        try{
            return musicianDTOConverter.toDTO(musicianService.getMusicianById(id));
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public MusicianDTO createMusician(@RequestBody MusicianDTO musicianDTO){
        Musician musician = musicianDTOConverter.toEntity(musicianDTO);
        try{
            return musicianDTOConverter.toDTO(musicianService.createMusician(musician));
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public MusicianDTO updateMusician(@RequestBody MusicianDTO musicianDTO){
        try{
            Musician musician = musicianDTOConverter.toEntity(musicianDTO);
            return musicianDTOConverter.toDTO(musicianService.updateMusician(musician));
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException d){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        try{
            musicianService.deleteMusicianById(id);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
