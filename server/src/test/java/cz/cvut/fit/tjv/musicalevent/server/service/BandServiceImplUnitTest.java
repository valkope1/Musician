package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import cz.cvut.fit.tjv.musicalevent.server.repository.BandRepository;
import cz.cvut.fit.tjv.musicalevent.server.repository.MusicalEventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BandServiceImplUnitTest {
    @Autowired
    private BandServiceImpl bandService;
    @MockBean
    private BandRepository bandRepository;
    @MockBean
    private MusicalEventRepository musicalEventRepository;
    Band band;
    MusicalEvent musicalEvent;

    @BeforeEach
    void setUp(){
        band = new Band();
        musicalEvent = new MusicalEvent();
        band.setName("test");
        band.setGenre("test");
        band.setFormationDate(LocalDate.now());
        musicalEvent.setEventName("test");
        musicalEvent.setDate(LocalDate.now());
        musicalEvent.setTown("test");
    }

    @Test
    void addBandToEventTest(){
        Mockito.when(bandRepository.findById(band.getBandId())).thenReturn(Optional.of(band));
        Mockito.when(musicalEventRepository.findById(musicalEvent.getMusicalEventId())).thenReturn(Optional.of(musicalEvent));
        Assertions.assertFalse(band.getMusicalEvents().contains(musicalEvent));
        bandService.addBandToEvent(band.getBandId(), musicalEvent.getMusicalEventId());
        Assertions.assertTrue(band.getMusicalEvents().contains(musicalEvent));
    }

    @Test
    void addBandToNonExistEventTest(){
        Mockito.when(bandRepository.findById(band.getBandId())).thenReturn(Optional.of(band));
        Mockito.when(musicalEventRepository.findById(musicalEvent.getMusicalEventId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
        bandService.addBandToEvent(band.getBandId(), musicalEvent.getMusicalEventId());
        });
    }
    @Test
    void addNonExistBandToEventTest(){
        Mockito.when(bandRepository.findById(band.getBandId())).thenReturn(Optional.empty());
        Mockito.when(musicalEventRepository.findById(musicalEvent.getMusicalEventId())).thenReturn(Optional.of(musicalEvent));
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            bandService.addBandToEvent(band.getBandId(), musicalEvent.getMusicalEventId());
        });
    }

    @Test
    void removeBandFromEventTest(){
        Mockito.when(bandRepository.findById(band.getBandId())).thenReturn(Optional.of(band));
        Mockito.when(musicalEventRepository.findById(musicalEvent.getMusicalEventId())).thenReturn(Optional.of(musicalEvent));
        List<MusicalEvent> tmp = new ArrayList<>();
        tmp.add(musicalEvent);
        band.setMusicalEvents(tmp);
        Assertions.assertTrue(band.getMusicalEvents().contains(musicalEvent));
        bandService.removeBandFromEvent(band.getBandId(), musicalEvent.getMusicalEventId());
        Assertions.assertFalse(band.getMusicalEvents().contains(musicalEvent));
    }

    @Test
    void removeBandToNonExistEventTest(){
        Mockito.when(bandRepository.findById(band.getBandId())).thenReturn(Optional.of(band));
        Mockito.when(musicalEventRepository.findById(musicalEvent.getMusicalEventId())).thenReturn(Optional.empty());
        List<MusicalEvent> tmp = new ArrayList<>();
        tmp.add(musicalEvent);
        band.setMusicalEvents(tmp);
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            bandService.removeBandFromEvent(band.getBandId(), musicalEvent.getMusicalEventId());
        });
    }
    @Test
    void removeNonExistBandToEventTest(){
        Mockito.when(bandRepository.findById(band.getBandId())).thenReturn(Optional.empty());
        Mockito.when(musicalEventRepository.findById(musicalEvent.getMusicalEventId())).thenReturn(Optional.of(musicalEvent));
        List<MusicalEvent> tmp = new ArrayList<>();
        tmp.add(musicalEvent);
        band.setMusicalEvents(tmp);
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            bandService.removeBandFromEvent(band.getBandId(), musicalEvent.getMusicalEventId());
        });
    }


    @Test
    void deleteNonExistBand(){
        Mockito.when(bandRepository.findById(band.getBandId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () ->{
            bandService.deleteBandById(band.getBandId());
        });
    }

    @Test
    void createBandWithNoName(){
        band.setName("");
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            bandService.createBand(band);
        });
    }

    @Test
    void getBandThatDoesntExist(){
        Assertions.assertThrows(EntityNotFoundException.class, () ->{
             bandService.getBandById(band.getBandId());
        });
    }
    @Test
    void updateBandThatDoesntExist(){
        Assertions.assertThrows(EntityNotFoundException.class, () ->{
            bandService.updateBand(band);
        });
    }
}
