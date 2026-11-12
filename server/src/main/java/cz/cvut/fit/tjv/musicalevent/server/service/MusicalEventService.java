package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MusicalEventService {
    public MusicalEvent getMusicalEventById(Long id) throws EntityNotFoundException;
    public List<MusicalEvent> getMusicalEvents();
    public MusicalEvent createMusicalEvent(MusicalEvent musicalEvent) throws IllegalArgumentException;
    public MusicalEvent updateMusicalEvent(MusicalEvent musicalEvent) throws EntityNotFoundException, IllegalArgumentException;
    public List<MusicalEvent> getBandsAfterDate(LocalDate date);
    public void deleteMusicalEventById(Long id) throws EntityNotFoundException;
}
