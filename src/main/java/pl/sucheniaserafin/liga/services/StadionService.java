package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.StadionDTO;
import pl.sucheniaserafin.liga.entities.Stadion;
import pl.sucheniaserafin.liga.repositories.StadionRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service // Ta adnotacja to klucz! Mówi Springowi, że to warstwa logiki biznesowej
public class StadionService {

    @Autowired
    private StadionRepository stadionRepository;

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

    public StadionDTO getStadionById(Long id) {
        return stadionRepository.findById(id)
                .map(s -> new StadionDTO(s.getId(), s.getNazwa(), s.getKraj(), s.getMiasto(), s.getPojemnosc()))
                .orElse(null);
    }

    public void deleteStadion(Long id) {
        stadionRepository.deleteById(id);
    }

    public StadionDTO addStadion(StadionDTO nowyStadionDTO) {
        Stadion stadion = new Stadion();
        stadion.setNazwa(nowyStadionDTO.nazwa());
        stadion.setKraj(nowyStadionDTO.kraj());
        stadion.setMiasto(nowyStadionDTO.miasto());
        stadion.setPojemnosc(nowyStadionDTO.pojemnosc());

        Stadion zapisany = stadionRepository.save(stadion);

        return new StadionDTO(zapisany.getId(), zapisany.getNazwa(), zapisany.getKraj(), zapisany.getMiasto(), zapisany.getPojemnosc());
    }
}