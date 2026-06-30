package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.*;
import pl.sucheniaserafin.liga.entities.*;
import pl.sucheniaserafin.liga.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MeczService {

    @Autowired private MeczRepository meczRepository;
    @Autowired private KlubRepository klubRepository;
    @Autowired private StadionRepository stadionRepository;
    @Autowired private PilkarzRepository pilkarzRepository;

    public List<MeczDTO> getAllMecze() {
        return StreamSupport.stream(meczRepository.findAll().spliterator(), false)
                .map(m -> new MeczDTO(m.getId(), m.getDataOdbycia() != null ? m.getDataOdbycia().toString() : "Brak", m.getWynik(), m.getGospodarz().getNazwa(), m.getGosc().getNazwa(), m.getStadion() != null ? m.getStadion().getNazwa() : "Brak"))
                .collect(Collectors.toList());
    }

    public MeczDTO getMeczById(Long id) {
        return meczRepository.findById(id)
                .map(m -> new MeczDTO(m.getId(), m.getDataOdbycia() != null ? m.getDataOdbycia().toString() : "Brak", m.getWynik(), m.getGospodarz().getNazwa(), m.getGosc().getNazwa(), m.getStadion() != null ? m.getStadion().getNazwa() : "Brak"))
                .orElse(null);
    }

    public void deleteMecz(Long id) {
        meczRepository.deleteById(id);
    }

    public MeczDTO updateMeczWynik(Long id, MeczDTO dto) {
        return meczRepository.findById(id).map(m -> {
            m.setWynik(dto.wynik());
            Mecz zaktualizowany = meczRepository.save(m);
            return new MeczDTO(zaktualizowany.getId(), zaktualizowany.getDataOdbycia() != null ? zaktualizowany.getDataOdbycia().toString() : "Brak", zaktualizowany.getWynik(), zaktualizowany.getGospodarz().getNazwa(), zaktualizowany.getGosc().getNazwa(), zaktualizowany.getStadion() != null ? zaktualizowany.getStadion().getNazwa() : "Brak");
        }).orElse(null);
    }

    // TĄ METODĄ POKAZUJESZ KASKADOWOŚĆ NA ZALICZENIU!
    public MeczDTO addMecz(MeczCreateDTO dto) {
        Mecz mecz = new Mecz();
        if(dto.dataOdbycia() != null && !dto.dataOdbycia().isEmpty()) {
            mecz.setDataOdbycia(LocalDateTime.parse(dto.dataOdbycia()));
        }
        mecz.setWynik(dto.wynik());
        mecz.setFrekwencja(dto.frekwencja());

        // Szukamy powiązanych obiektów w bazie po ich ID
        mecz.setGospodarz(klubRepository.findById(dto.gospodarzId()).orElseThrow(() -> new RuntimeException("Brak gospodarza")));
        mecz.setGosc(klubRepository.findById(dto.goscId()).orElseThrow(() -> new RuntimeException("Brak gościa")));
        if (dto.stadionId() != null) {
            mecz.setStadion(stadionRepository.findById(dto.stadionId()).orElse(null));
        }

        // Dodawanie bramek (jeśli jakieś przesłano)
        List<Bramka> bramki = new ArrayList<>();
        if (dto.bramki() != null) {
            for (BramkaCreateDTO bDto : dto.bramki()) {
                Bramka b = new Bramka();
                b.setMinuta(bDto.minuta());
                b.setStrzelec(pilkarzRepository.findById(bDto.strzelecId()).orElseThrow());
                b.setMecz(mecz); // Bardzo ważne: powiązanie bramki z meczem!
                bramki.add(b);
            }
        }
        mecz.setBramki(bramki); // Przypinamy listę bramek do meczu

        // Dodawanie kartek (jeśli jakieś przesłano)
        List<Kartka> kartki = new ArrayList<>();
        if (dto.kartki() != null) {
            for (KartkaCreateDTO kDto : dto.kartki()) {
                Kartka k = new Kartka();
                k.setKolor(KolorKartki.valueOf(kDto.kolor().toUpperCase()));
                k.setMinuta(kDto.minuta());
                k.setUkarany(pilkarzRepository.findById(kDto.ukaranyId()).orElseThrow());
                k.setMecz(mecz);
                kartki.add(k);
            }
        }
        mecz.setKartki(kartki); // Przypinamy listę kartek do meczu

        // ZAPIS KASKADOWY - Zapisujemy TYLKO Mecz, a Spring sam dopisze bramki i kartki!
        Mecz zapisany = meczRepository.save(mecz);

        return new MeczDTO(zapisany.getId(), zapisany.getDataOdbycia() != null ? zapisany.getDataOdbycia().toString() : "Brak", zapisany.getWynik(), zapisany.getGospodarz().getNazwa(), zapisany.getGosc().getNazwa(), zapisany.getStadion() != null ? zapisany.getStadion().getNazwa() : "Brak");
    }
}