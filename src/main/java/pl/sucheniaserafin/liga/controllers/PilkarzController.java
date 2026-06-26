package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sucheniaserafin.liga.dto.PilkarzDTO;
import pl.sucheniaserafin.liga.services.PilkarzService;

import java.util.List;

@RestController
@RequestMapping("/api/pilkarze")
public class PilkarzController {

    @Autowired
    private PilkarzService pilkarzService;

    @GetMapping
    public List<PilkarzDTO> getAllPilkarze() {
        return pilkarzService.getAllPilkarze();
    }

    @GetMapping("/szukaj")
    public List<PilkarzDTO> szukajPilkarzy(@RequestParam String fragment) {
        return pilkarzService.szukajPilkarzy(fragment);
    }

    @GetMapping("/{id}")
    public PilkarzDTO getPilkarzById(@PathVariable Long id) {
        return pilkarzService.getPilkarzById(id);
    }

    @PostMapping
    public PilkarzDTO addPilkarz(@RequestBody PilkarzDTO dto) {
        return pilkarzService.addPilkarz(dto);
    }

    @PutMapping("/{id}")
    public PilkarzDTO updatePilkarz(@PathVariable Long id, @RequestBody PilkarzDTO dto) {
        return pilkarzService.updatePilkarz(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePilkarz(@PathVariable Long id) {
        pilkarzService.deletePilkarz(id);
    }
}