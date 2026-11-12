package cz.cvut.fit.tjv.musicalevent.server.service;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import cz.cvut.fit.tjv.musicalevent.server.repository.BandRepository;
import cz.cvut.fit.tjv.musicalevent.server.repository.MusicalEventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MusicalEventServiceImpl implements MusicalEventService{
    private final MusicalEventRepository musicalEventRepository;
    private final BandRepository bandRepository;
    public MusicalEventServiceImpl(MusicalEventRepository musicalEventRepository, BandRepository bandRepository){
        this.musicalEventRepository = musicalEventRepository;
        this.bandRepository = bandRepository;
    }
    @Override
    public MusicalEvent getMusicalEventById(Long id) throws EntityNotFoundException {
        Optional<MusicalEvent> musicalEvent = musicalEventRepository.findById(id);
        if(musicalEvent.isEmpty()){
            throw new EntityNotFoundException("Musical event with given id doesnt exist.");
        }
        return musicalEvent.get();
    }

    @Override
    public List<MusicalEvent> getMusicalEvents() {
        return musicalEventRepository.findAll();
    }

    @Override
    public MusicalEvent createMusicalEvent(MusicalEvent musicalEvent) throws IllegalArgumentException{
        if(musicalEventRepository.existsById(musicalEvent.getMusicalEventId())){
            throw new IllegalArgumentException("Musical event with given id already exist.");
        }
        validate(musicalEvent);
        return musicalEventRepository.save(musicalEvent);
    }

    @Override
    public MusicalEvent updateMusicalEvent(MusicalEvent musicalEvent) throws EntityNotFoundException {
        if(!musicalEventRepository.existsById(musicalEvent.getMusicalEventId())){
            throw new EntityNotFoundException("Musical event with given id doesnt exist.");
        }
        validate(musicalEvent);
        return musicalEventRepository.save(musicalEvent);
    }

    private void validate(MusicalEvent musicalEvent) {
        if(musicalEvent.getTown() == null || musicalEvent.getTown().isEmpty()){
            throw new IllegalArgumentException("empty town");
        }
        if(musicalEvent.getEventName() == null || musicalEvent.getEventName().isEmpty()){
            throw new IllegalArgumentException("empty name");
        }
        if(musicalEvent.getDate() == null){
            throw new IllegalArgumentException("empty date");
        }
        for(Band band : musicalEvent.getBands()){
            if(!bandRepository.existsById(band.getBandId())){
                throw new IllegalArgumentException("band with given id doesnt exist");
            }
        }
    }

    @Override
    public List<MusicalEvent> getBandsAfterDate(LocalDate date) {
        return musicalEventRepository.findEventAfterBandFormationYear(date);
    }

    @Override
    public void deleteMusicalEventById(Long id) throws EntityNotFoundException {
        Optional<MusicalEvent> optMusicalEvent = musicalEventRepository.findById(id);
        if(optMusicalEvent.isEmpty()){
            throw new EntityNotFoundException("Musical event with given id doesnt exist.");
        }
        MusicalEvent musicalEvent = optMusicalEvent.get();
        musicalEvent.getBands().forEach(band -> band.getMusicalEvents().remove(musicalEvent));
        musicalEvent.getBands().clear();
        musicalEventRepository.delete(musicalEvent);
    }
}
