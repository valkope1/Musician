package cz.cvut.fit.tjv.musicalevent.server.repository;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class MusicalEventRepositoryTest {
    @Autowired
    private MusicalEventRepository musicalEventRepository;
    @Autowired
    private BandRepository bandRepository;

    @Test
    void findEventAfterBandFormationYearTest(){
        MusicalEvent musicalEvent = new MusicalEvent(1L, "town", "name", LocalDate.now(), new ArrayList<>());
        Band band = new Band(1L, "name",  "genre", LocalDate.now(), new ArrayList<>(), new ArrayList<>());
        band.getMusicalEvents().add(musicalEvent);
        musicalEvent.getBands().add(band);
        bandRepository.save(band);
        musicalEventRepository.save(musicalEvent);
        List<MusicalEvent> ret = musicalEventRepository.findEventAfterBandFormationYear(LocalDate.of(2020, 1, 1));
        Assertions.assertTrue(ret.contains(musicalEvent));
        Assertions.assertEquals(1, ret.size());
    }

    @Test
    void findEventAfterBandFormationYearTest2(){
        MusicalEvent musicalEvent = new MusicalEvent(1L, "town", "name", LocalDate.now(), new ArrayList<>());
        Band band = new Band(1L, "name",  "genre", LocalDate.now(), new ArrayList<>(), new ArrayList<>());
        band.getMusicalEvents().add(musicalEvent);
        musicalEvent.getBands().add(band);
        bandRepository.save(band);
        musicalEventRepository.save(musicalEvent);
        List<MusicalEvent> ret = musicalEventRepository.findEventAfterBandFormationYear(LocalDate.of(2025, 1, 1));
        Assertions.assertFalse(ret.contains(musicalEvent));
        Assertions.assertEquals(0, ret.size());
    }
}
