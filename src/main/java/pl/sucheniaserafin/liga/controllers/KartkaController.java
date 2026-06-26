package pl.sucheniaserafin.liga.controllers;

import pl.sucheniaserafin.liga.dto.KartkaDTO;
import pl.sucheniaserafin.liga.repositories.KartkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/kartki")
public class KartkaController {

    @Autowired
    private KartkaRepository kartkaRepository;

    @GetMapping
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
