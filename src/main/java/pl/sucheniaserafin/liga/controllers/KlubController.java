package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sucheniaserafin.liga.dto.KlubDTO;
import pl.sucheniaserafin.liga.services.KlubService;

import java.util.List;

@RestController
@RequestMapping("/api/kluby")
public class KlubController {

    @Autowired
    private KlubService klubService;

    @GetMapping
    public List<KlubDTO> getAllKluby() {
        return klubService.getAllKluby();
    }
}