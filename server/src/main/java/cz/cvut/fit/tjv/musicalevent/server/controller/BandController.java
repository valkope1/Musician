package cz.cvut.fit.tjv.musicalevent.server.controller;

import cz.cvut.fit.tjv.musicalevent.server.domain.converter.BandDTOConverter;
import cz.cvut.fit.tjv.musicalevent.server.domain.dto.BandDTO;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.service.BandService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bands")
public class BandController {
    private final BandService bandService;
    private final BandDTOConverter bandDTOConverter;
    public BandController(BandService bandService, BandDTOConverter bandDTOConverter){
        this.bandService = bandService;
        this.bandDTOConverter = bandDTOConverter;
    }

    @GetMapping
    public List<BandDTO> getBands(){
        return bandService.getBands().stream().map(bandDTOConverter::toDTO).toList();
    }

    @GetMapping(path = "{id}")
    public BandDTO getBand(@PathVariable("id") Long id){
        try {
            return bandDTOConverter.toDTO(bandService.getBandById(id));
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public BandDTO createBand(@RequestBody BandDTO bandDTO){
        Band band = bandDTOConverter.toEntity(bandDTO);
        try{
            return bandDTOConverter.toDTO(bandService.createBand(band));
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public BandDTO updateBand(@RequestBody BandDTO bandDTO){
        Band band = bandDTOConverter.toEntity(bandDTO);
        try{
            return bandDTOConverter.toDTO(bandService.updateBand(band));
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException d){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        try{
            bandService.deleteBandById(id);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addtoevent")
    public void addBandToEvent(@RequestParam("bandId") Long bandId, @RequestParam("eventId") Long eventId){
        try{
            bandService.addBandToEvent(bandId, eventId);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/removefromevent")
    public void removeBandToEvent(@RequestParam("bandId") Long bandId, @RequestParam("eventId") Long eventId){
        try{
            bandService.removeBandFromEvent(bandId, eventId);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
