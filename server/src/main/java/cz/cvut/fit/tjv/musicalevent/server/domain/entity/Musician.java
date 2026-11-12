package cz.cvut.fit.tjv.musicalevent.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "musician")
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "musician_id", nullable = false)
    private Long musicianId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "instrument")
    private String instrument;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "band_band_id")
    private Band band;

    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || getClass() != o.getClass()) return false;
        Musician musician = (Musician) o;
        return this.musicianId.equals(musician.getMusicianId());
    }
}
