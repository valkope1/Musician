package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class MusicianServiceImplUnitTest {
    @Autowired
    private MusicianServiceImpl musicianService;
    private Musician musician = new Musician();

    @BeforeEach
    void setup(){
        musician.setMusicianId(1L);
        musician.setName("name");
        musician.setSurname("surname");
        musician.setInstrument("instrument");
        musician.setBirthDate(LocalDate.now());
    }

    @Test
    void getMusicianWithNonExistId(){
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            musicianService.getMusicianById(musician.getMusicianId());
        });
    }

    @Test
    void createMusicinaWithNoName(){
        musician.setName("");
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            musicianService.createMusician(musician);
        });
    }

    @Test
    void deleteMusicianWithNonExistId(){
        Assertions.assertThrows(EntityNotFoundException.class, () ->{
            musicianService.deleteMusicianById(musician.getMusicianId());
        });
    }
    @Test
    void updateMusicianThatDoesntExist(){
        Assertions.assertThrows(EntityNotFoundException.class, () ->{
            musicianService.updateMusician(musician);
        });
    }

}
