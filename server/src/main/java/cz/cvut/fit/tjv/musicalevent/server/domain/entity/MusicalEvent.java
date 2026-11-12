package cz.cvut.fit.tjv.musicalevent.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "musical_event")
public class MusicalEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "musical_event_id", nullable = false)
    private Long musicalEventId;

    @Column(name = "town")
    private String town;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "date")
    private LocalDate date;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "musicalEvents")
    private List<Band> bands = new ArrayList<>();

    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || getClass() != o.getClass()) return false;
        MusicalEvent musicalEvent = (MusicalEvent) o;
        return this.musicalEventId.equals(musicalEvent.getMusicalEventId());
    }
}
