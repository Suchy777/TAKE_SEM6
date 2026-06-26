package pl.sucheniaserafin.liga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    public KlubDTO getKlubById(@PathVariable Long id) {
        return klubService.getKlubById(id);
    }

    @PostMapping
    public KlubDTO addKlub(@RequestBody KlubDTO dto) {
        return klubService.addKlub(dto);
    }

    @PutMapping("/{id}")
    public KlubDTO updateKlub(@PathVariable Long id, @RequestBody KlubDTO dto) {
        return klubService.updateKlub(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteKlub(@PathVariable Long id) {
        klubService.deleteKlub(id);
    }
}