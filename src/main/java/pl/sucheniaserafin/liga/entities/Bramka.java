package pl.sucheniaserafin.liga.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bramki")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Bramka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer minuta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mecz_id", nullable = false)
    private Mecz mecz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilkarz_id", nullable = false)
    private Pilkarz strzelec;
}
