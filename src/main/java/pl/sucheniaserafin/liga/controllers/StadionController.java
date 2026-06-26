package pl.sucheniaserafin.liga.controllers;

import pl.sucheniaserafin.liga.dto.StadionDTO;
import pl.sucheniaserafin.liga.repositories.StadionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/stadiony")
public class StadionController {

    @Autowired
    private StadionRepository stadionRepository;

    @GetMapping
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
}
