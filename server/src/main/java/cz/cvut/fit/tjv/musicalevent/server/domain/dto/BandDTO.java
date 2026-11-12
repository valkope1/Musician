package cz.cvut.fit.tjv.musicalevent.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Getter
@AllArgsConstructor
public class BandDTO {
    private final Long bandId;
    private final String name;
    private final String genre;
    private final LocalDate formationDate;
    private final List<Long> musiciansId;
    private final List<Long> musicalEventsId;
}
