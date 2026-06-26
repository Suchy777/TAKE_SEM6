package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.KartkaDTO;
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
}