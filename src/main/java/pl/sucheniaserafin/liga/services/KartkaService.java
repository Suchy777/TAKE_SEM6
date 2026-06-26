package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.KartkaDTO;
import pl.sucheniaserafin.liga.entities.Kartka;
import pl.sucheniaserafin.liga.repositories.KartkaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KartkaService {

    @Autowired
    private KartkaRepository kartkaRepository;

    public List<KartkaDTO> getAllKartki() {
        return StreamSupport.stream(kartkaRepository.findAll().spliterator(), false)
                .map(k -> new KartkaDTO(
                        k.getId(),
                        k.getKolor().toString(),
                        k.getMinuta(),
                        k.getUkarany().getNazwisko(),
                        k.getMecz().getGospodarz().getNazwa() + " vs " + k.getMecz().getGosc().getNazwa()
                ))
                .collect(Collectors.toList());
    }

    public KartkaDTO getKartkaById(Long id) {
        return kartkaRepository.findById(id)
                .map(k -> new KartkaDTO(k.getId(), k.getKolor().toString(), k.getMinuta(), k.getUkarany().getNazwisko(), k.getMecz().getWynik()))
                .orElse(null);
    }

    public void deleteKartka(Long id) {
        kartkaRepository.deleteById(id);
    }

    public KartkaDTO updateKartka(Long id, KartkaDTO dto) {
        return kartkaRepository.findById(id).map(k -> {
            k.setMinuta(dto.minuta());
            Kartka zaktualizowana = kartkaRepository.save(k);
            return new KartkaDTO(zaktualizowana.getId(), zaktualizowana.getKolor().toString(), zaktualizowana.getMinuta(), zaktualizowana.getUkarany().getNazwisko(), zaktualizowana.getMecz().getWynik());
        }).orElse(null);
    }
}