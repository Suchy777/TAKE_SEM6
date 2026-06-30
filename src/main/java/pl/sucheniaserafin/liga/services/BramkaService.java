package pl.sucheniaserafin.liga.services;

import jakarta.transaction.Transactional;
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

    private BramkaDTO mapToDTO(Bramka b) {
        String klub = b.getStrzelec().getKlub() != null ? b.getStrzelec().getKlub().getNazwa() : "Brak klubu";
        String mecz = b.getMecz().getGospodarz().getNazwa() + " vs " + b.getMecz().getGosc().getNazwa();
        return new BramkaDTO(b.getId(), b.getMinuta(), b.getStrzelec().getNazwisko(), klub, mecz);
    }

    public List<BramkaDTO> getAllBramki() {
        return StreamSupport.stream(bramkaRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BramkaDTO getBramkaById(Long id) {
        return bramkaRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    public void deleteBramka(Long id) {
        bramkaRepository.deleteById(id);
    }

    public BramkaDTO updateBramka(Long id, BramkaDTO dto) {
        return bramkaRepository.findById(id).map(b -> {
            b.setMinuta(dto.minuta());
            return mapToDTO(bramkaRepository.save(b));
        }).orElse(null);
    }

    @Transactional 
    public BramkaDTO addBramka(BramkaEventDTO dto) {
        Mecz mecz = meczRepository.findById(dto.meczId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono meczu o id: " + dto.meczId()));
        Pilkarz pilkarz = pilkarzRepository.findById(dto.pilkarzId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono piłkarza o id: " + dto.pilkarzId()));


        if (pilkarz.getKlub() != null) {
            String obecnyWynik = mecz.getWynik(); 
            if (obecnyWynik == null || obecnyWynik.isBlank()) {
                obecnyWynik = "0:0";
            }
            
            String[] czesci = obecnyWynik.split(":");
            int goleGospodarz = Integer.parseInt(czesci[0]);
            int goleGosc = Integer.parseInt(czesci[1]);

            if (pilkarz.getKlub().getId().equals(mecz.getGospodarz().getId())) {
                goleGospodarz++;
            } else if (pilkarz.getKlub().getId().equals(mecz.getGosc().getId())) {
                goleGosc++;
            } else {
                throw new RuntimeException("Piłkarz nie gra w żadnej z drużyn w tym meczu!");
            }
            
            mecz.setWynik(goleGospodarz + ":" + goleGosc);
            meczRepository.save(mecz);
        }

        Bramka bramka = new Bramka();
        bramka.setMecz(mecz);
        bramka.setStrzelec(pilkarz);
        bramka.setMinuta(dto.minuta());

        return mapToDTO(bramkaRepository.save(bramka));
    }
}