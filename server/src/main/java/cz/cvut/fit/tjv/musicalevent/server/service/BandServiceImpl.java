package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import cz.cvut.fit.tjv.musicalevent.server.repository.BandRepository;
import cz.cvut.fit.tjv.musicalevent.server.repository.MusicalEventRepository;
import cz.cvut.fit.tjv.musicalevent.server.repository.MusicianRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BandServiceImpl implements BandService{
    private final BandRepository bandRepository;
    private final MusicianRepository musicianRepository;
    private final MusicalEventRepository musicalEventRepository;
    public BandServiceImpl(BandRepository bandRepository, MusicalEventRepository musicalEventRepository, MusicianRepository musicianRepository){
        this.bandRepository = bandRepository;
        this.musicalEventRepository = musicalEventRepository;
        this.musicianRepository = musicianRepository;
    }
    @Override
    public Band getBandById(Long id) throws EntityNotFoundException {
        Optional<Band> band = bandRepository.findById(id);
        if(band.isEmpty()){
            throw new EntityNotFoundException("Band with given id doesnt exist.");
        }
        return band.get();
    }

    @Override
    public List<Band> getBands() {
        return bandRepository.findAll();
    }

    @Override
    public Band createBand(Band band) throws IllegalArgumentException {
        if(bandRepository.existsById(band.getBandId())){
            throw new IllegalArgumentException("Band with given id already exist.");
        }
        validate(band);
        return bandRepository.save(band);
    }

    @Override
    public Band updateBand(Band band) throws EntityNotFoundException {
        if(!bandRepository.existsById(band.getBandId())){
            throw new EntityNotFoundException("Band with given id doesnt exist.");
        }
        validate(band);
        return bandRepository.save(band);
    }

    private void validate(Band band) {
        if(band.getName() == null || band.getName().isEmpty()){
            throw new IllegalArgumentException("empty name");
        }
        if(band.getGenre() == null || band.getGenre().isEmpty()){
            throw new IllegalArgumentException("empty genre");
        }
        if(band.getFormationDate()  == null){
            throw new IllegalArgumentException("date is empty");
        }
        for(Musician mus : band.getMusicians()){
            if(!musicianRepository.existsById(mus.getMusicianId())){
                throw new IllegalArgumentException("doesnt exist");
            }
        }
        for(MusicalEvent musEv : band.getMusicalEvents()){
            if(!musicalEventRepository.existsById(musEv.getMusicalEventId())){
                throw new IllegalArgumentException("doesnt exist");
            }
        }
    }

    @Override
    public void deleteBandById(Long id) throws EntityNotFoundException {
        if(!bandRepository.existsById(id)){
            throw new EntityNotFoundException("Band with given id doesnt exist.");
        }
        bandRepository.deleteById(id);
    }

    @Override
    public void addBandToEvent(Long bandId, Long eventId) throws EntityNotFoundException {
        Optional<Band> optBand = bandRepository.findById(bandId);
        Optional<MusicalEvent> optMusicalEvent = musicalEventRepository.findById(eventId);
        if(optBand.isEmpty() || optMusicalEvent.isEmpty()){
            throw new EntityNotFoundException("doesnt exist");
        }
        Band band = optBand.get();
        MusicalEvent musicalEvent = optMusicalEvent.get();
        if(!band.getMusicalEvents().contains(musicalEvent)){
            band.getMusicalEvents().add(musicalEvent);
            bandRepository.save(band);
        }
    }

    @Override
    public void removeBandFromEvent(Long bandId, Long eventId) throws EntityNotFoundException {
        Optional<Band> optBand = bandRepository.findById(bandId);
        Optional<MusicalEvent> optMusicalEvent = musicalEventRepository.findById(eventId);
        if(optBand.isEmpty() || optMusicalEvent.isEmpty()){
            throw new EntityNotFoundException("doesnt exist");
        }
        Band band = optBand.get();
        MusicalEvent musicalEvent = optMusicalEvent.get();
        if(band.getMusicalEvents().contains(musicalEvent)) {
            band.getMusicalEvents().remove(musicalEvent);
            bandRepository.save(band);
        }
    }
}
