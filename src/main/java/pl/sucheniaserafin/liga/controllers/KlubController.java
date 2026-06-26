package pl.sucheniaserafin.liga.controllers;

import pl.sucheniaserafin.liga.dto.KlubDTO;
import pl.sucheniaserafin.liga.repositories.KlubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/kluby")
public class KlubController {

    @Autowired
    private KlubRepository klubRepository;

    @GetMapping
    public List<KlubDTO> getAllKluby() {
        return StreamSupport.stream(klubRepository.findAll().spliterator(), false)
                .map(k -> new KlubDTO(k.getId(), k.getNazwa(), k.getKraj(), k.getMiasto()))
                .collect(Collectors.toList());
    }
}
