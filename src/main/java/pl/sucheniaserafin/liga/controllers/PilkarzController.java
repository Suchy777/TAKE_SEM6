package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}