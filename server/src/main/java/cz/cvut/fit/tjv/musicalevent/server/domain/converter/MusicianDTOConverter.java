package cz.cvut.fit.tjv.musicalevent.server.domain.converter;

import cz.cvut.fit.tjv.musicalevent.server.domain.dto.MusicianDTO;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import cz.cvut.fit.tjv.musicalevent.server.service.BandService;
import org.springframework.stereotype.Component;

@Component
public class MusicianDTOConverter implements DTOConverter<MusicianDTO, Musician> {
    private BandService bandService;
    public MusicianDTOConverter(BandService bandService){
        this.bandService = bandService;
    }
    @Override
    public MusicianDTO toDTO(Musician e) {
        return new MusicianDTO(e.getMusicianId(), e.getName(), e.getSurname(), e.getInstrument(), e.getBirthDate(), e.getBand().getBandId());
    }

    @Override
    public Musician toEntity(MusicianDTO musicianDTO) {
        return new Musician(musicianDTO.getMusicianId(), musicianDTO.getName(), musicianDTO.getSurname(), musicianDTO.getInstrument(),
                musicianDTO.getBirthDate(), bandService.getBandById(musicianDTO.getBandId()));
    }
}
