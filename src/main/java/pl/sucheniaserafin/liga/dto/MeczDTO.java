package pl.sucheniaserafin.liga.dto;
import java.util.List;

public record MeczDTO(
        Long id,
        String dataOdbycia,
        String wynik,
        String gospodarzNazwa,
        String goscNazwa,
        String stadionNazwa,
        List<BramkaDTO> bramki, 
        List<KartkaDTO> kartki  
) {}