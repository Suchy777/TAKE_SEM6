package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.PilkarzDTO;
import pl.sucheniaserafin.liga.entities.Pilkarz;
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

    public PilkarzDTO getPilkarzById(Long id) {
        return pilkarzRepository.findById(id)
                .map(p -> new PilkarzDTO(p.getId(), p.getImie(), p.getNazwisko(), p.getKlub() != null ? p.getKlub().getNazwa() : "Brak"))
                .orElse(null);
    }

    public void deletePilkarz(Long id) {
        pilkarzRepository.deleteById(id);
    }

    public PilkarzDTO addPilkarz(PilkarzDTO dto) {
        Pilkarz p = new Pilkarz();
        p.setImie(dto.imie());
        p.setNazwisko(dto.nazwisko());
        Pilkarz zapisany = pilkarzRepository.save(p);
        return new PilkarzDTO(zapisany.getId(), zapisany.getImie(), zapisany.getNazwisko(), "Brak (Zaktualizuj klub PUTem)");
    }

    public PilkarzDTO updatePilkarz(Long id, PilkarzDTO dto) {
        return pilkarzRepository.findById(id).map(p -> {
            p.setImie(dto.imie());
            p.setNazwisko(dto.nazwisko());
            Pilkarz zaktualizowany = pilkarzRepository.save(p);
            return new PilkarzDTO(zaktualizowany.getId(), zaktualizowany.getImie(), zaktualizowany.getNazwisko(), zaktualizowany.getKlub() != null ? zaktualizowany.getKlub().getNazwa() : "Brak");
        }).orElse(null);
    }
}