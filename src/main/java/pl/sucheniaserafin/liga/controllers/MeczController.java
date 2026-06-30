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

    @GetMapping("/{id}")
    public MeczDTO getMeczById(@PathVariable Long id) {
        return meczService.getMeczById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMecz(@PathVariable Long id) {
        meczService.deleteMecz(id);
    }

    @PostMapping
    public MeczDTO addMecz(@RequestBody pl.sucheniaserafin.liga.dto.MeczCreateDTO dto) {
        return meczService.addMecz(dto);
    }

    @PutMapping("/{id}")
    public MeczDTO updateMecz(@PathVariable Long id, @RequestBody pl.sucheniaserafin.liga.dto.MeczCreateDTO dto) {
        return meczService.updateMecz(id, dto);
    }
}