package cz.cvut.fit.tjv.musicalevent.server.repository;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.MusicalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MusicalEventRepository extends JpaRepository<MusicalEvent, Long> {
    @Query(value = "SELECT m FROM MusicalEvent m JOIN m.bands b WHERE b.formationDate >= :date")
    public List<MusicalEvent> findEventAfterBandFormationYear(@Param("date") LocalDate date);

}
