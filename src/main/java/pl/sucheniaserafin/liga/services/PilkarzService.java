package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.PilkarzDTO;
import pl.sucheniaserafin.liga.repositories.PilkarzRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PilkarzService {

    @Autowired
    private PilkarzRepository pilkarzRepository;

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

    public List<PilkarzDTO> szukajPilkarzy(String fragment) {
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