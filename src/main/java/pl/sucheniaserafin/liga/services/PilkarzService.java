package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.PilkarzDTO;
import pl.sucheniaserafin.liga.entities.Klub;
import pl.sucheniaserafin.liga.entities.Pilkarz;
import pl.sucheniaserafin.liga.repositories.KlubRepository;
import pl.sucheniaserafin.liga.repositories.PilkarzRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PilkarzService {

    @Autowired private PilkarzRepository pilkarzRepository;
    @Autowired private KlubRepository klubRepository;

    private PilkarzDTO mapToDTO(Pilkarz p) {
        Long klubId = p.getKlub() != null ? p.getKlub().getId() : null;
        String nazwaKlubu = p.getKlub() != null ? p.getKlub().getNazwa() : "Brak klubu";
        
        return new PilkarzDTO(p.getId(), p.getImie(), p.getNazwisko(), klubId, nazwaKlubu);
    }

    public List<PilkarzDTO> getAllPilkarze() {
        return StreamSupport.stream(pilkarzRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PilkarzDTO> szukajPilkarzy(String fragment) {
        return pilkarzRepository.findByNazwiskoContainingIgnoreCase(fragment).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PilkarzDTO getPilkarzById(Long id) {
        return pilkarzRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    public void deletePilkarz(Long id) {
        pilkarzRepository.deleteById(id);
    }

    public PilkarzDTO addPilkarz(PilkarzDTO dto) {
        Pilkarz pilkarz = new Pilkarz();
        pilkarz.setImie(dto.imie());
        pilkarz.setNazwisko(dto.nazwisko());

        if (dto.klubId() != null) {
            Klub klub = klubRepository.findById(dto.klubId())
                    .orElseThrow(() -> new RuntimeException("Nie znaleziono klubu o id: " + dto.klubId()));
            pilkarz.setKlub(klub);
        }

        Pilkarz zapisany = pilkarzRepository.save(pilkarz);
        return mapToDTO(zapisany);
    }

    public PilkarzDTO updatePilkarz(Long id, PilkarzDTO dto) {
        return pilkarzRepository.findById(id).map(p -> {
            p.setImie(dto.imie());
            p.setNazwisko(dto.nazwisko());
            
            if (dto.klubId() != null) {
                Klub klub = klubRepository.findById(dto.klubId())
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono klubu o id: " + dto.klubId()));
                p.setKlub(klub);
            } else {
                p.setKlub(null);
            }
            
            return mapToDTO(pilkarzRepository.save(p));
        }).orElse(null);
    }
}