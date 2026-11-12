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
@Table(name = "band")
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "band_id", nullable = false)
    private Long bandId;

    @Column(name = "name")
    private String name;

    @Column(name = "genre")
    private String genre;

    @Column(name = "formation_date")
    private LocalDate formationDate;

    @OneToMany(mappedBy = "band", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    private List<Musician> musicians = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "band_musicalEvents",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "musical_event_id"))
    private List<MusicalEvent> musicalEvents = new ArrayList<>();

    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || getClass() != o.getClass()) return false;
        Band band = (Band) o;
        return this.bandId.equals(band.getBandId());
    }
}
