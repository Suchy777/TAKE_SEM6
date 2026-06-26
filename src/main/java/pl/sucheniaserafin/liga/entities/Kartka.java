package pl.sucheniaserafin.liga.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kartki")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Kartka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private KolorKartki kolor;

    @Column(nullable = false)
    private Integer minuta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mecz_id", nullable = false)
    private Mecz mecz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilkarz_id", nullable = false)
    private Pilkarz ukarany;
}

