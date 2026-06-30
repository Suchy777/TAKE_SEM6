package pl.sucheniaserafin.liga.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "kluby")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Klub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nazwa;

    @Column(length = 50)
    private String kraj;

    @Column(length = 50)
    private String miasto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadion_id")
    private Stadion stadion;

    @OneToMany(mappedBy = "klub", cascade = CascadeType.ALL)
    private List<Pilkarz> pilkarze;

    @OneToMany(mappedBy = "gospodarz")
    private List<Mecz> meczeDomowe;

    @OneToMany(mappedBy = "gosc")
    private List<Mecz> meczeWyjazdowe;
}