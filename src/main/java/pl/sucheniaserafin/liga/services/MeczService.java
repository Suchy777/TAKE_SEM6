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
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MeczDTO getMeczById(Long id) {
        return meczRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    // Pomocnicza metoda mapująca encję na DTO z uwzględnieniem list zdarzeń
    private MeczDTO mapToDTO(Mecz m) {
        // Formatter dla ładniejszej daty
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String sformatowanaData = m.getDataOdbycia() != null ? m.getDataOdbycia().format(formatter) : "Brak";

        return new MeczDTO(
                m.getId(),
                sformatowanaData,
                m.getWynik(),
                m.getGospodarz().getNazwa(),
                m.getGosc().getNazwa(),
                m.getStadion() != null ? m.getStadion().getNazwa() : "Brak",
                m.getBramki().stream().map(b -> new BramkaDTO(
                        b.getId(), 
                        b.getMinuta(), 
                        b.getStrzelec().getNazwisko(), 
                        b.getStrzelec().getKlub() != null ? b.getStrzelec().getKlub().getNazwa() : "Brak klubu",
                        m.getGospodarz().getNazwa() + " vs " + m.getGosc().getNazwa()
                )).collect(Collectors.toList()),
                m.getKartki().stream().map(k -> new KartkaDTO(
                        k.getId(), 
                        k.getKolor().toString(), 
                        k.getMinuta(), 
                        k.getUkarany().getNazwisko(), 
                        k.getUkarany().getKlub() != null ? k.getUkarany().getKlub().getNazwa() : "Brak klubu",
                        m.getGospodarz().getNazwa() + " vs " + m.getGosc().getNazwa()
                )).collect(Collectors.toList())
        );
    }

    public void deleteMecz(Long id) {
        meczRepository.deleteById(id);
    }

    public MeczDTO updateMecz(Long id, MeczCreateDTO dto) {
        return meczRepository.findById(id).map(m -> {
            if(dto.dataOdbycia() != null && !dto.dataOdbycia().isEmpty()) {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                m.setDataOdbycia(LocalDateTime.parse(dto.dataOdbycia(), formatter));
            }
            m.setWynik(dto.wynik());
            m.setFrekwencja(dto.frekwencja());
            
            if(dto.gospodarzId() != null) {
                m.setGospodarz(klubRepository.findById(dto.gospodarzId()).orElse(m.getGospodarz()));
            }
            if(dto.goscId() != null) {
                m.setGosc(klubRepository.findById(dto.goscId()).orElse(m.getGosc()));
            }
            if (dto.stadionId() != null) {
                m.setStadion(stadionRepository.findById(dto.stadionId()).orElse(m.getStadion()));
            }

            Mecz zaktualizowany = meczRepository.save(m);
            return mapToDTO(zaktualizowany);
        }).orElse(null);
    }

    public MeczDTO addMecz(MeczCreateDTO dto) {
        Mecz mecz = new Mecz();
        if(dto.dataOdbycia() != null && !dto.dataOdbycia().isEmpty()) {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            mecz.setDataOdbycia(LocalDateTime.parse(dto.dataOdbycia(), formatter));
        }
        mecz.setWynik(dto.wynik());
        mecz.setFrekwencja(dto.frekwencja());

        mecz.setGospodarz(klubRepository.findById(dto.gospodarzId()).orElseThrow(() -> new RuntimeException("Brak gospodarza")));
        mecz.setGosc(klubRepository.findById(dto.goscId()).orElseThrow(() -> new RuntimeException("Brak gościa")));
        if (dto.stadionId() != null) {
            mecz.setStadion(stadionRepository.findById(dto.stadionId()).orElse(null));
        }

        List<Bramka> bramki = new ArrayList<>();
        if (dto.bramki() != null) {
            for (BramkaCreateDTO bDto : dto.bramki()) {
                Bramka b = new Bramka();
                b.setMinuta(bDto.minuta());
                b.setStrzelec(pilkarzRepository.findById(bDto.strzelecId()).orElseThrow());
                b.setMecz(mecz);
                bramki.add(b);
            }
        }
        mecz.setBramki(bramki);

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
        mecz.setKartki(kartki);

        Mecz zapisany = meczRepository.save(mecz);
        return mapToDTO(zapisany);
    }
}