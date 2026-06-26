package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sucheniaserafin.liga.dto.KartkaDTO;
import pl.sucheniaserafin.liga.services.KartkaService;

import java.util.List;

@RestController
@RequestMapping("/api/kartki")
public class KartkaController {

    @Autowired
    private KartkaService kartkaService;

    @GetMapping
    public List<KartkaDTO> getAllKartki() {
        return kartkaService.getAllKartki();
    }

    @GetMapping("/{id}")
    public KartkaDTO getKartkaById(@PathVariable Long id) {
        return kartkaService.getKartkaById(id);
    }

    @PutMapping("/{id}")
    public KartkaDTO updateKartka(@PathVariable Long id, @RequestBody KartkaDTO dto) {
        return kartkaService.updateKartka(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteKartka(@PathVariable Long id) {
        kartkaService.deleteKartka(id);
    }
}