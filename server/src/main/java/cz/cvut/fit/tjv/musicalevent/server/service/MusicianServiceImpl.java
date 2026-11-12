package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import cz.cvut.fit.tjv.musicalevent.server.repository.BandRepository;
import cz.cvut.fit.tjv.musicalevent.server.repository.MusicianRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MusicianServiceImpl implements MusicianService{
    private MusicianRepository musicianRepository;
    private BandRepository bandRepository;

    public MusicianServiceImpl(MusicianRepository musicianRepository, BandRepository bandRepository){
        this.musicianRepository = musicianRepository;
        this.bandRepository = bandRepository;
    }
    @Override
    public Musician getMusicianById(Long id) throws EntityNotFoundException {
        Optional<Musician> musician = musicianRepository.findById(id);
        if(musician.isEmpty()){
            throw new EntityNotFoundException("Musician with given id doesnt exist.");
        }
        return musician.get();
    }

    @Override
    public List<Musician> getMusicians() {
        return musicianRepository.findAll();
    }

    @Override
    public Musician createMusician(Musician musician) throws IllegalArgumentException{
        validate(musician);
        if(musicianRepository.existsById(musician.getMusicianId())){
            throw new IllegalArgumentException("musician already exist");
        }
        return musicianRepository.save(musician);
    }

    @Override
    public Musician updateMusician(Musician musician) throws EntityNotFoundException{
        if(!musicianRepository.existsById(musician.getMusicianId())){
            throw new EntityNotFoundException("musician with given id doesnt exist.");
        }
        validate(musician);
        return musicianRepository.save(musician);
    }

    private void validate(Musician musician) {
        if(musician.getName() == null || musician.getName().isEmpty()){
            throw new IllegalArgumentException("empty name");
        }
        if(musician.getSurname() == null || musician.getSurname().isEmpty()){
            throw new IllegalArgumentException("empty surname");
        }
        if(musician.getInstrument() == null || musician.getInstrument().isEmpty()){
            throw new IllegalArgumentException("empty instrument");
        }
        if(musician.getBirthDate() == null){
            throw new IllegalArgumentException("empty birthdate");
        }
        if(musician.getBand() == null || !bandRepository.existsById(musician.getBand().getBandId())){
            throw new IllegalArgumentException("invalid band");
        }
    }

    @Override
    public void deleteMusicianById(Long id) throws EntityNotFoundException{
        if(!musicianRepository.existsById(id)){
            throw new EntityNotFoundException("musician with given id doesnt exist.");
        }
        musicianRepository.deleteById(id);
    }
}
