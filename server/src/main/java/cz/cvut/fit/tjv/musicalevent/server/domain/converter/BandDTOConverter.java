package cz.cvut.fit.tjv.musicalevent.server.domain.converter;

import cz.cvut.fit.tjv.musicalevent.server.domain.dto.BandDTO;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import cz.cvut.fit.tjv.musicalevent.server.service.MusicalEventService;
import cz.cvut.fit.tjv.musicalevent.server.service.MusicianService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BandDTOConverter implements DTOConverter<BandDTO, Band> {
    private MusicianService musicianService;
    private MusicalEventService musicalEventService;
    public BandDTOConverter(MusicianService musicianService, MusicalEventService musicalEventService){
        this.musicianService = musicianService;
        this.musicalEventService = musicalEventService;
    }
    @Override
    public BandDTO toDTO(Band e) {
        return new BandDTO(e.getBandId(), e.getName(), e.getGenre(), e.getFormationDate(),
                e.getMusicians().stream().map(Musician::getMusicianId).toList(),
                e.getMusicalEvents().stream().map(MusicalEvent::getMusicalEventId).toList());
    }

    @Override
    public Band toEntity(BandDTO bandDTO) {
        List<Musician> musicians = new ArrayList<>();
        Iterator<Long> iterator = bandDTO.getMusiciansId().iterator();
        while(iterator.hasNext()){
            musicians.add(musicianService.getMusicianById(iterator.next()));
        }
        List<MusicalEvent> musicalEvents = new ArrayList<>();
        iterator = bandDTO.getMusicalEventsId().iterator();
        while(iterator.hasNext()){
            musicalEvents.add(musicalEventService.getMusicalEventById(iterator.next()));
        }
        return new Band(bandDTO.getBandId(), bandDTO.getName(), bandDTO.getGenre(), bandDTO.getFormationDate(),
                musicians, musicalEvents);
    }
}
