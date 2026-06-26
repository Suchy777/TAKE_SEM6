package pl.sucheniaserafin.liga.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mecze")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Mecz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataOdbycia;

    @Column(length = 10)
    private String wynik;

    private Integer frekwencja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gospodarz_id", nullable = false)
    private Klub gospodarz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gosc_id", nullable = false)
    private Klub gosc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadion_id")
    private Stadion stadion;

    // Scenariusz 2 - Zbiorcze zapisywanie (Kaskadowość z wykładu)
    @OneToMany(mappedBy = "mecz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bramka> bramki;

    @OneToMany(mappedBy = "mecz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kartka> kartki;
}

