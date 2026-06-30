package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.KartkaDTO;
import pl.sucheniaserafin.liga.dto.KartkaEventDTO;
import pl.sucheniaserafin.liga.entities.Kartka;
import pl.sucheniaserafin.liga.entities.KolorKartki;
import pl.sucheniaserafin.liga.entities.Mecz;
import pl.sucheniaserafin.liga.entities.Pilkarz;
import pl.sucheniaserafin.liga.repositories.KartkaRepository;
import pl.sucheniaserafin.liga.repositories.MeczRepository;
import pl.sucheniaserafin.liga.repositories.PilkarzRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KartkaService {

    @Autowired private KartkaRepository kartkaRepository;
    @Autowired private MeczRepository meczRepository;
    @Autowired private PilkarzRepository pilkarzRepository;

    private KartkaDTO mapToDTO(Kartka k) {
        String klub = k.getUkarany().getKlub() != null ? k.getUkarany().getKlub().getNazwa() : "Brak klubu";
        String mecz = k.getMecz().getGospodarz().getNazwa() + " vs " + k.getMecz().getGosc().getNazwa();
        return new KartkaDTO(k.getId(), k.getKolor().toString(), k.getMinuta(), k.getUkarany().getNazwisko(), klub, mecz);
    }

    public List<KartkaDTO> getAllKartki() {
        return StreamSupport.stream(kartkaRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public KartkaDTO getKartkaById(Long id) {
        return kartkaRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    public void deleteKartka(Long id) {
        kartkaRepository.deleteById(id);
    }

    public KartkaDTO updateKartka(Long id, KartkaDTO dto) {
        return kartkaRepository.findById(id).map(k -> {
            k.setMinuta(dto.minuta());
            if(dto.kolor() != null && !dto.kolor().isEmpty()){
                k.setKolor(KolorKartki.valueOf(dto.kolor().toUpperCase()));
            }
            return mapToDTO(kartkaRepository.save(k));
        }).orElse(null);
    }

    public KartkaDTO addKartka(KartkaEventDTO dto) {
        Mecz mecz = meczRepository.findById(dto.meczId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono meczu o id: " + dto.meczId()));
        Pilkarz pilkarz = pilkarzRepository.findById(dto.pilkarzId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono piłkarza o id: " + dto.pilkarzId()));

        Kartka kartka = new Kartka();
        kartka.setMecz(mecz);
        kartka.setUkarany(pilkarz);
        kartka.setMinuta(dto.minuta());
        kartka.setKolor(KolorKartki.valueOf(dto.kolor().toUpperCase()));

        return mapToDTO(kartkaRepository.save(kartka));
    }
}