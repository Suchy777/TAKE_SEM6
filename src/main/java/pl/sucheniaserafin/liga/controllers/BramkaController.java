package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sucheniaserafin.liga.dto.BramkaDTO;
import pl.sucheniaserafin.liga.services.BramkaService;

import java.util.List;

@RestController
@RequestMapping("/api/bramki")
public class BramkaController {

    @Autowired
    private BramkaService bramkaService;

    @GetMapping
    public List<BramkaDTO> getAllBramki() {
        return bramkaService.getAllBramki();
    }
}