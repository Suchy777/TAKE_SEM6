package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.KlubDTO;
import pl.sucheniaserafin.liga.entities.Klub;
import pl.sucheniaserafin.liga.entities.Stadion;
import pl.sucheniaserafin.liga.repositories.KlubRepository;
import pl.sucheniaserafin.liga.repositories.StadionRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KlubService {

    @Autowired private KlubRepository klubRepository;
    @Autowired private StadionRepository stadionRepository; 

    private KlubDTO mapToDTO(Klub k) {
        Long stadionId = k.getStadion() != null ? k.getStadion().getId() : null;
        String nazwaStadionu = k.getStadion() != null ? k.getStadion().getNazwa() : "Brak przypisanego stadionu";
        
        return new KlubDTO(k.getId(), k.getNazwa(), k.getKraj(), k.getMiasto(), stadionId, nazwaStadionu);
    }

    public List<KlubDTO> getAllKluby() {
        return StreamSupport.stream(klubRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public KlubDTO getKlubById(Long id) {
        return klubRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public void deleteKlub(Long id) {
        klubRepository.deleteById(id);
    }

    public KlubDTO addKlub(KlubDTO dto) {
        Klub klub = new Klub();
        klub.setNazwa(dto.nazwa());
        klub.setKraj(dto.kraj());
        klub.setMiasto(dto.miasto());
        
        if (dto.stadionId() != null) {
            Stadion stadion = stadionRepository.findById(dto.stadionId())
                    .orElseThrow(() -> new RuntimeException("Nie znaleziono stadionu o id: " + dto.stadionId()));
            klub.setStadion(stadion);
        }

        Klub zapisany = klubRepository.save(klub);
        return mapToDTO(zapisany);
    }

    public KlubDTO updateKlub(Long id, KlubDTO dto) {
        return klubRepository.findById(id).map(k -> {
            k.setNazwa(dto.nazwa());
            k.setKraj(dto.kraj());
            k.setMiasto(dto.miasto());
            
            if (dto.stadionId() != null) {
                Stadion stadion = stadionRepository.findById(dto.stadionId())
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono stadionu o id: " + dto.stadionId()));
                k.setStadion(stadion);
            } else {
                k.setStadion(null);
            }
            
            Klub zaktualizowany = klubRepository.save(k);
            return mapToDTO(zaktualizowany);
        }).orElse(null);
    }
}