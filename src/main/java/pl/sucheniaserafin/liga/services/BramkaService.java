package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.BramkaDTO;
import pl.sucheniaserafin.liga.repositories.BramkaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BramkaService {

    @Autowired
    private BramkaRepository bramkaRepository;

    public List<BramkaDTO> getAllBramki() {
        return StreamSupport.stream(bramkaRepository.findAll().spliterator(), false)
                .map(b -> new BramkaDTO(
                        b.getId(),
                        b.getMinuta(),
                        b.getStrzelec().getNazwisko(),
                        b.getMecz().getGospodarz().getNazwa() + " vs " + b.getMecz().getGosc().getNazwa()
                ))
                .collect(Collectors.toList());
    }
}