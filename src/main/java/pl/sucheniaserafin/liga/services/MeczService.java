package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.MeczDTO;
import pl.sucheniaserafin.liga.repositories.MeczRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MeczService {

    @Autowired
    private MeczRepository meczRepository;

    public List<MeczDTO> getAllMecze() {
        return StreamSupport.stream(meczRepository.findAll().spliterator(), false)
                .map(m -> new MeczDTO(
                        m.getId(),
                        m.getDataOdbycia() != null ? m.getDataOdbycia().toString() : "Brak daty",
                        m.getWynik(),
                        m.getGospodarz().getNazwa(),
                        m.getGosc().getNazwa(),
                        m.getStadion() != null ? m.getStadion().getNazwa() : "Brak stadionu"
                ))
                .collect(Collectors.toList());
    }
}