package pl.sucheniaserafin.liga.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "stadiony")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Stadion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nazwa;

    @Column(length = 50)
    private String kraj;

    @Column(length = 50)
    private String miasto;

    private LocalDate dataOtwarcia;
    private Integer pojemnosc;

    @OneToMany(mappedBy = "stadion")
    private List<Mecz> mecze;
}

