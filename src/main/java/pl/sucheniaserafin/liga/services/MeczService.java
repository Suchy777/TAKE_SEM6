package pl.sucheniaserafin.liga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sucheniaserafin.liga.dto.MeczDTO;
import pl.sucheniaserafin.liga.entities.Mecz;
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

    public MeczDTO getMeczById(Long id) {
        return meczRepository.findById(id)
                .map(m -> new MeczDTO(m.getId(), m.getDataOdbycia() != null ? m.getDataOdbycia().toString() : "Brak", m.getWynik(), m.getGospodarz().getNazwa(), m.getGosc().getNazwa(), m.getStadion() != null ? m.getStadion().getNazwa() : "Brak"))
                .orElse(null);
    }

    public void deleteMecz(Long id) {
        meczRepository.deleteById(id);
    }

    // Do uproszczenia tworzenia nowych meczów bez wyszukiwania relacji, pozwalamy zaktualizować wynik
    public MeczDTO updateMeczWynik(Long id, MeczDTO dto) {
        return meczRepository.findById(id).map(m -> {
            m.setWynik(dto.wynik());
            Mecz zaktualizowany = meczRepository.save(m);
            return new MeczDTO(zaktualizowany.getId(), zaktualizowany.getDataOdbycia() != null ? zaktualizowany.getDataOdbycia().toString() : "Brak", zaktualizowany.getWynik(), zaktualizowany.getGospodarz().getNazwa(), zaktualizowany.getGosc().getNazwa(), zaktualizowany.getStadion() != null ? zaktualizowany.getStadion().getNazwa() : "Brak");
        }).orElse(null);
    }
}