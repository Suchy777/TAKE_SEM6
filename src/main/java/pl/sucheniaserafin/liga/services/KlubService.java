package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.KlubDTO;
import pl.sucheniaserafin.liga.entities.Klub;
import pl.sucheniaserafin.liga.repositories.KlubRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KlubService {

    @Autowired
    private KlubRepository klubRepository;

    public List<KlubDTO> getAllKluby() {
        return StreamSupport.stream(klubRepository.findAll().spliterator(), false)
                .map(k -> new KlubDTO(k.getId(), k.getNazwa(), k.getKraj(), k.getMiasto()))
                .collect(Collectors.toList());
    }

    public KlubDTO getKlubById(Long id) {
        return klubRepository.findById(id)
                .map(k -> new KlubDTO(k.getId(), k.getNazwa(), k.getKraj(), k.getMiasto()))
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
        Klub zapisany = klubRepository.save(klub);
        return new KlubDTO(zapisany.getId(), zapisany.getNazwa(), zapisany.getKraj(), zapisany.getMiasto());
    }

    public KlubDTO updateKlub(Long id, KlubDTO dto) {
        return klubRepository.findById(id).map(k -> {
            k.setNazwa(dto.nazwa());
            k.setKraj(dto.kraj());
            k.setMiasto(dto.miasto());
            Klub zaktualizowany = klubRepository.save(k);
            return new KlubDTO(zaktualizowany.getId(), zaktualizowany.getNazwa(), zaktualizowany.getKraj(), zaktualizowany.getMiasto());
        }).orElse(null);
    }
}