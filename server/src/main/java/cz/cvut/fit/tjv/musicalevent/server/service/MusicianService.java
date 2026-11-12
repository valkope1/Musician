package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MusicianService {
    public Musician getMusicianById(Long id) throws EntityNotFoundException;
    public List<Musician> getMusicians();
    public Musician createMusician(Musician musician) throws IllegalArgumentException;
    public Musician updateMusician(Musician musician) throws EntityNotFoundException;
    public void deleteMusicianById(Long id) throws EntityNotFoundException;
}
