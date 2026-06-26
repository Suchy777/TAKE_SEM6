package pl.sucheniaserafin.liga.controllers;

import pl.sucheniaserafin.liga.dto.BramkaDTO;
import pl.sucheniaserafin.liga.repositories.BramkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/bramki")
public class BramkaController {

    @Autowired
    private BramkaRepository bramkaRepository;

    @GetMapping
    public List<BramkaDTO> getAllBramki() {
        return StreamSupport.stream(bramkaRepository.findAll().spliterator(), false)
                .map(b -> new BramkaDTO(
                        b.getId(),
                        b.getMinuta(),
                        b.getStrzelec().getNazwisko(),
                        b.getMecz().getGospodarz().getNazwa() + " vs " + b.getMecz().getGosc().getNazwa()
                ))
                .collect(Collectors.toList());
    }
}
