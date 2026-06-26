package pl.sucheniaserafin.liga.controllers;

import pl.sucheniaserafin.liga.dto.PilkarzDTO;
import pl.sucheniaserafin.liga.repositories.PilkarzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/pilkarze")
public class PilkarzController {

    @Autowired
    private PilkarzRepository pilkarzRepository;

    @GetMapping
    public List<PilkarzDTO> getAllPilkarze() {
        return StreamSupport.stream(pilkarzRepository.findAll().spliterator(), false)
                .map(p -> new PilkarzDTO(
                        p.getId(), 
                        p.getImie(), 
                        p.getNazwisko(), 
                        p.getKlub() != null ? p.getKlub().getNazwa() : "Brak klubu"
                ))
                .collect(Collectors.toList());
    }

    // Wyszukuje piłkarzy po fragmencie nazwiska
    @GetMapping("/szukaj")
    public List<PilkarzDTO> szukajPilkarzy(@RequestParam String fragment) {
        // Używamy naszej metody z PilkarzRepository!
        return pilkarzRepository.findByNazwiskoContainingIgnoreCase(fragment).stream()
                .map(p -> new PilkarzDTO(
                        p.getId(),
                        p.getImie(),
                        p.getNazwisko(),
                        p.getKlub() != null ? p.getKlub().getNazwa() : "Brak klubu"
                ))
                .collect(Collectors.toList());
    }
}
