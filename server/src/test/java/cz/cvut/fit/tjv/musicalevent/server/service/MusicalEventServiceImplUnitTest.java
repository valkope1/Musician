package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class MusicalEventServiceImplUnitTest {
    @Autowired
    private MusicalEventServiceImpl musicalEventService;
    private MusicalEvent musicalEvent = new MusicalEvent();

    @BeforeEach
    void setup(){
        musicalEvent.setMusicalEventId(1L);
        musicalEvent.setEventName("name");
        musicalEvent.setTown("town");
        musicalEvent.setDate(LocalDate.now());
    }

    @Test
    void createMusicalEventWithNoName(){
        musicalEvent.setEventName("");
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            musicalEventService.createMusicalEvent(musicalEvent);
        });
    }
    @Test
    void getMusicalEventWithInvalidId(){
        musicalEventService.createMusicalEvent(musicalEvent);
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
                musicalEventService.getMusicalEventById(93L);
        }
        );
    }
    @Test
    void deleteMusicalEventWithInvalidId(){
        musicalEventService.createMusicalEvent(musicalEvent);
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
                    musicalEventService.deleteMusicalEventById(93L);
                }
        );
    }
    @Test
    void updateMusicalEventWithInvalidId(){
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
                    musicalEventService.updateMusicalEvent(musicalEvent);
                }
        );
    }

}
