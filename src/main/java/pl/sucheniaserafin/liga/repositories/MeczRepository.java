package pl.sucheniaserafin.liga.repositories;


import pl.sucheniaserafin.liga.entities.Mecz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MeczRepository extends CrudRepository<Mecz, Long> {
    // Scenariusz 3: Filtrowanie po frekwencji
    List<Mecz> findByFrekwencjaGreaterThanEqual(Integer minFrekwencja);

    // Scenariusz 3: Wyszukiwanie meczów danego klubu (Gospodarz lub Gość)
    @EntityGraph(attributePaths = {"gospodarz", "gosc", "stadion"})
    List<Mecz> findByGospodarzIdOrGoscId(Long gospodarzId, Long goscId);
}
