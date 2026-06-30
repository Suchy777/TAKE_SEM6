package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.sucheniaserafin.liga.dto.StadionDTO;
import pl.sucheniaserafin.liga.services.StadionService;

import java.util.List;

@RestController
@RequestMapping("/api/stadiony")
public class StadionController {

    // Zamiast Repository, wstrzykujemy nasz nowy Service!
    @Autowired
    private StadionService stadionService;

    @GetMapping
    public List<StadionDTO> getAllStadiony() {
        return stadionService.getAllStadiony();
    }

    @GetMapping("/{id}")
    public StadionDTO getStadionById(@PathVariable Long id) {
        return stadionService.getStadionById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStadion(@PathVariable Long id) {
        stadionService.deleteStadion(id);
    }

    @PostMapping
    public StadionDTO addStadion(@RequestBody StadionDTO nowyStadionDTO) {
        return stadionService.addStadion(nowyStadionDTO);
    }

    @PutMapping("/{id}")
    public StadionDTO updateStadion(@PathVariable Long id, @RequestBody StadionDTO dto) {
        return stadionService.updateStadion(id, dto);
    }
}