package pl.sucheniaserafin.liga.controllers;

import pl.sucheniaserafin.liga.dto.StadionDTO;
import pl.sucheniaserafin.liga.entities.Stadion;
import pl.sucheniaserafin.liga.repositories.StadionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/stadiony")
public class StadionController {

    @Autowired
    private StadionRepository stadionRepository;

    @GetMapping
    public List<StadionDTO> getAllStadiony() {
        return StreamSupport.stream(stadionRepository.findAll().spliterator(), false)
                .map(s -> new StadionDTO(
                        s.getId(),
                        s.getNazwa(),
                        s.getKraj(),
                        s.getMiasto(),
                        s.getPojemnosc()
                ))
                .collect(Collectors.toList());
    }

    // Pobiera jeden konkretny stadion po jego ID, używając findById()
    @GetMapping("/{id}")
    public StadionDTO getStadionById(@PathVariable Long id) {
        return stadionRepository.findById(id)
                .map(s -> new StadionDTO(s.getId(), s.getNazwa(), s.getKraj(), s.getMiasto(), s.getPojemnosc()))
                .orElse(null); // Zwraca null, jeśli nie znajdzie stadionu
    }

    // Usuwa stadion o podanym ID, używając deleteById()
    @DeleteMapping("/{id}")
    public void deleteStadion(@PathVariable Long id) {
        stadionRepository.deleteById(id);
    }

    // Dodaje nowy stadion do bazy używając save()
    @PostMapping
    public StadionDTO addStadion(@RequestBody StadionDTO nowyStadionDTO) {
        // Zamieniamy DTO z przeglądarki na prawdziwą encję bazy danych
        Stadion stadion = new Stadion();
        stadion.setNazwa(nowyStadionDTO.nazwa());
        stadion.setKraj(nowyStadionDTO.kraj());
        stadion.setMiasto(nowyStadionDTO.miasto());
        stadion.setPojemnosc(nowyStadionDTO.pojemnosc());

        // Zapisujemy do bazy za pomocą save()!
        Stadion zapisany = stadionRepository.save(stadion);

        // Zwracamy zapisany obiekt z powrotem jako DTO
        return new StadionDTO(zapisany.getId(), zapisany.getNazwa(), zapisany.getKraj(), zapisany.getMiasto(), zapisany.getPojemnosc());
    }
}
