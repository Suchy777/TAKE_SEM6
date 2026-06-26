package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.KlubDTO;
import pl.sucheniaserafin.liga.repositories.KlubRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KlubService {

    @Autowired
    private KlubRepository klubRepository;

    public List<KlubDTO> getAllKluby() {
        return StreamSupport.stream(klubRepository.findAll().spliterator(), false)
                .map(k -> new KlubDTO(k.getId(), k.getNazwa(), k.getKraj(), k.getMiasto()))
                .collect(Collectors.toList());
    }
}