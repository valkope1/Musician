package cz.cvut.fit.tjv.musicalevent.server.repository;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {
}
