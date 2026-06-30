package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.BramkaDTO;
import pl.sucheniaserafin.liga.dto.BramkaEventDTO;
import pl.sucheniaserafin.liga.entities.Bramka;
import pl.sucheniaserafin.liga.entities.Mecz;
import pl.sucheniaserafin.liga.entities.Pilkarz;
import pl.sucheniaserafin.liga.repositories.BramkaRepository;
import pl.sucheniaserafin.liga.repositories.MeczRepository;
import pl.sucheniaserafin.liga.repositories.PilkarzRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BramkaService {

    @Autowired private BramkaRepository bramkaRepository;
    @Autowired private MeczRepository meczRepository;
    @Autowired private PilkarzRepository pilkarzRepository;

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

    public BramkaDTO getBramkaById(Long id) {
        return bramkaRepository.findById(id)
                .map(b -> new BramkaDTO(b.getId(), b.getMinuta(), b.getStrzelec().getNazwisko(), b.getMecz().getGospodarz().getNazwa() + " vs " + b.getMecz().getGosc().getNazwa()))
                .orElse(null);
    }

    public void deleteBramka(Long id) {
        bramkaRepository.deleteById(id);
    }

    public BramkaDTO updateBramka(Long id, BramkaDTO dto) {
        return bramkaRepository.findById(id).map(b -> {
            b.setMinuta(dto.minuta());
            Bramka zaktualizowana = bramkaRepository.save(b);
            return new BramkaDTO(zaktualizowana.getId(), zaktualizowana.getMinuta(), zaktualizowana.getStrzelec().getNazwisko(), zaktualizowana.getMecz().getGospodarz().getNazwa() + " vs " + zaktualizowana.getMecz().getGosc().getNazwa());
        }).orElse(null);
    }

    public BramkaDTO addBramka(BramkaEventDTO dto) {
        // Wyszukiwanie istniejącego meczu i piłkarza w bazie
        Mecz mecz = meczRepository.findById(dto.meczId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono meczu o id: " + dto.meczId()));
        Pilkarz pilkarz = pilkarzRepository.findById(dto.pilkarzId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono piłkarza o id: " + dto.pilkarzId()));

        Bramka bramka = new Bramka();
        bramka.setMecz(mecz);
        bramka.setStrzelec(pilkarz);
        bramka.setMinuta(dto.minuta());

        Bramka zapisana = bramkaRepository.save(bramka);

        return new BramkaDTO(zapisana.getId(), zapisana.getMinuta(), zapisana.getStrzelec().getNazwisko(), zapisana.getMecz().getGospodarz().getNazwa() + " vs " + zapisana.getMecz().getGosc().getNazwa());
    }
}