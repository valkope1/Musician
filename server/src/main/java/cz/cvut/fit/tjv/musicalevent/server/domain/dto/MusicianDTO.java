package cz.cvut.fit.tjv.musicalevent.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MusicianDTO {
    private final Long musicianId;
    private final String name;
    private final String surname;
    private final String instrument;
    private final LocalDate birthDate;
    private final Long bandId;
}
