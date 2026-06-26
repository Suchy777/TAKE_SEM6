package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sucheniaserafin.liga.dto.MeczDTO;
import pl.sucheniaserafin.liga.services.MeczService;

import java.util.List;

@RestController
@RequestMapping("/api/mecze")
public class MeczController {

    @Autowired
    private MeczService meczService;

    @GetMapping
    public List<MeczDTO> getAllMecze() {
        return meczService.getAllMecze();
    }
}