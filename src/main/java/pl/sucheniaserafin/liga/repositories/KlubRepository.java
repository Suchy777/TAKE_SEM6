package pl.sucheniaserafin.liga.repositories;

import pl.sucheniaserafin.liga.entities.Klub;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface KlubRepository extends CrudRepository<Klub, Long> {
    // Zapytanie do Scenariusza 3
    List<Klub> findByMiasto(String miasto);
    List<Klub> findByKraj(String kraj);
}
