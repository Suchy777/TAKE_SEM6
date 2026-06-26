package pl.sucheniaserafin.liga.controllers;

import pl.sucheniaserafin.liga.dto.MeczDTO;
import pl.sucheniaserafin.liga.repositories.MeczRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/mecze")
public class MeczController {

    @Autowired
    private MeczRepository meczRepository;

    @GetMapping
    public List<MeczDTO> getAllMecze() {
        return StreamSupport.stream(meczRepository.findAll().spliterator(), false)
                .map(m -> new MeczDTO(
                        m.getId(),
                        m.getDataOdbycia() != null ? m.getDataOdbycia().toString() : "Brak daty",
                        m.getWynik(),
                        m.getGospodarz().getNazwa(),
                        m.getGosc().getNazwa(),
                        m.getStadion() != null ? m.getStadion().getNazwa() : "Brak stadionu"
                ))
                .collect(Collectors.toList());
    }
}
