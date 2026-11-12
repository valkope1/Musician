package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface BandService {
    public Band getBandById(Long id) throws EntityNotFoundException;
    public List<Band> getBands();
    public Band createBand(Band band) throws IllegalArgumentException;
    public Band updateBand(Band band) throws EntityNotFoundException, IllegalArgumentException;
    public void deleteBandById(Long id) throws EntityNotFoundException;

    public void addBandToEvent(Long bandId, Long eventId) throws EntityNotFoundException;
    public void removeBandFromEvent(Long bandId, Long eventId) throws EntityNotFoundException;
}
