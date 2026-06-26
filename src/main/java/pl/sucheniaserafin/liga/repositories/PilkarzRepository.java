package pl.sucheniaserafin.liga.repositories;

import pl.sucheniaserafin.liga.entities.Pilkarz;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PilkarzRepository extends CrudRepository<Pilkarz, Long> {
    // Scenariusz 3: Wyszukiwanie piłkarzy po fragmencie nazwiska
    List<Pilkarz> findByNazwiskoContainingIgnoreCase(String fragment);
}
