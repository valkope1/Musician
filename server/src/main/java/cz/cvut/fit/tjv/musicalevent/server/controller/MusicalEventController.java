package cz.cvut.fit.tjv.musicalevent.server.controller;

import cz.cvut.fit.tjv.musicalevent.server.domain.converter.MusicalEventDTOConverter;
import cz.cvut.fit.tjv.musicalevent.server.domain.dto.MusicalEventDTO;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import cz.cvut.fit.tjv.musicalevent.server.service.MusicalEventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("musicalevents")
public class MusicalEventController {
    private final MusicalEventService musicalEventService;
    private final MusicalEventDTOConverter musicalEventDTOConverter;
    public MusicalEventController(MusicalEventService musicalEventService, MusicalEventDTOConverter musicalEventDTOConverter){
        this.musicalEventService = musicalEventService;
        this.musicalEventDTOConverter = musicalEventDTOConverter;
    }
    @GetMapping
    public List<MusicalEventDTO> getMusicalEvents(){
        return musicalEventService.getMusicalEvents().stream().map(musicalEventDTOConverter::toDTO).toList();
    }
    @GetMapping("/afterdate")
    public List<MusicalEventDTO> getMusicalEventsAfterDate(@RequestParam("date") LocalDate date){
        return musicalEventService.getBandsAfterDate(date).stream().map(musicalEventDTOConverter::toDTO).toList();
    }

    @GetMapping(path = "{id}")
    public MusicalEventDTO getMusicalEvent(@PathVariable("id") Long id){
        try {
            return musicalEventDTOConverter.toDTO(musicalEventService.getMusicalEventById(id));
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public MusicalEventDTO createMusicalEvent(@RequestBody MusicalEventDTO musicalEventDTO){
        MusicalEvent musicalEvent = musicalEventDTOConverter.toEntity(musicalEventDTO);
        try{
            return musicalEventDTOConverter.toDTO(musicalEventService.createMusicalEvent(musicalEvent));
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public MusicalEventDTO updateMusicalEvent(@RequestBody MusicalEventDTO musicalEventDTO){
        MusicalEvent musicalEvent = musicalEventDTOConverter.toEntity(musicalEventDTO);
        try{
            return musicalEventDTOConverter.toDTO(musicalEventService.updateMusicalEvent(musicalEvent));
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException d){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        try{
            musicalEventService.deleteMusicalEventById(id);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
