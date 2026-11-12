package cz.cvut.fit.tjv.musicalevent.client.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class MusicalEventDTO {
    private final Long musicalEventId;
    private final String town;
    private final String eventName;
    private final LocalDate date;
    private final List<Long> bandsId;
}
