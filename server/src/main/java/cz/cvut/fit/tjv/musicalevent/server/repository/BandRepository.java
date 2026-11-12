package cz.cvut.fit.tjv.musicalevent.server.repository;

import cz.cvut.fit.tjv.musicalevent.server.domain.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
}
