package pl.sucheniaserafin.liga.dto;
import java.util.List;

public record MeczCreateDTO(
        String dataOdbycia, 
        String wynik, 
        Integer frekwencja, 
        Long gospodarzId, 
        Long goscId, 
        Long stadionId, 
        List<BramkaCreateDTO> bramki, 
        List<KartkaCreateDTO> kartki
) {}