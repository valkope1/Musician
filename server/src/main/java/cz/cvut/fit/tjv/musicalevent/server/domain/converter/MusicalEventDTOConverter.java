package cz.cvut.fit.tjv.musicalevent.server.domain.converter;

import cz.cvut.fit.tjv.musicalevent.server.domain.dto.MusicalEventDTO;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import cz.cvut.fit.tjv.musicalevent.server.service.BandService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MusicalEventDTOConverter implements DTOConverter<MusicalEventDTO, MusicalEvent> {
    private BandService bandService;
    public MusicalEventDTOConverter(BandService bandService){
        this.bandService = bandService;
    }
    @Override
    public MusicalEventDTO toDTO(MusicalEvent e) {
        return new MusicalEventDTO(e.getMusicalEventId(), e.getTown(), e.getEventName(), e.getDate(),
                e.getBands().stream().map(Band::getBandId).toList());
    }

    @Override
    public MusicalEvent toEntity(MusicalEventDTO musicalEventDTO) {
        List<Band> bands = new ArrayList<>();
        Iterator<Long> iterator = musicalEventDTO.getBandsId().iterator();
        while(iterator.hasNext()){
            bands.add(bandService.getBandById(iterator.next()));
        }
        return new MusicalEvent(musicalEventDTO.getMusicalEventId(), musicalEventDTO.getTown(), musicalEventDTO.getEventName(),
                musicalEventDTO.getDate(), bands);
    }
}
