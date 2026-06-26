package pl.sucheniaserafin.liga.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pilkarze")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pilkarz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String imie;

    @Column(nullable = false, length = 50)
    private String nazwisko;

    private LocalDate dataUrodzenia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "klub_id")
    private Klub klub;

    @OneToMany(mappedBy = "strzelec", cascade = CascadeType.ALL)
    private List<Bramka> bramki;

    @OneToMany(mappedBy = "ukarany", cascade = CascadeType.ALL)
    private List<Kartka> kartki;
}
