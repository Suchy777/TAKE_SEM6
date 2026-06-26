package pl.sucheniaserafin.liga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sucheniaserafin.liga.entities.*;
import pl.sucheniaserafin.liga.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private KlubRepository klubRepository;
    @Autowired private StadionRepository stadionRepository;
    @Autowired private PilkarzRepository pilkarzRepository;
    @Autowired private MeczRepository meczRepository;
    @Autowired private BramkaRepository bramkaRepository;
    @Autowired private KartkaRepository kartkaRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. Tworzymy Stadiony
        Stadion narodowy = new Stadion(null, "Stadion Narodowy", "Polska", "Warszawa", LocalDate.of(2012, 1, 29), 58580, new ArrayList<>());
        Stadion silesia = new Stadion(null, "Stadion Śląski", "Polska", "Chorzów", LocalDate.of(1956, 7, 22), 54378, new ArrayList<>());
        stadionRepository.save(narodowy);
        stadionRepository.save(silesia);

        // 2. Tworzymy Kluby
        Klub legia = new Klub(null, "Legia Warszawa", "Polska", "Warszawa", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Klub ruch = new Klub(null, "Ruch Chorzów", "Polska", "Chorzów", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        klubRepository.save(legia);
        klubRepository.save(ruch);

        // 3. Tworzymy Piłkarzy
        Pilkarz p1 = new Pilkarz(null, "Artur", "Jędrzejczyk", LocalDate.of(1987, 11, 4), legia, new ArrayList<>(), new ArrayList<>());
        Pilkarz p2 = new Pilkarz(null, "Josue", "Peszkowski", LocalDate.of(1990, 9, 17), legia, new ArrayList<>(), new ArrayList<>());
        Pilkarz p3 = new Pilkarz(null, "Tomasz", "Foszmańczyk", LocalDate.of(1986, 1, 7), ruch, new ArrayList<>(), new ArrayList<>());
        pilkarzRepository.save(p1);
        pilkarzRepository.save(p2);
        pilkarzRepository.save(p3);

        // 4. Tworzymy Mecz
        Mecz mecz = new Mecz(null, LocalDateTime.now().minusDays(2), "2:1", 25000, legia, ruch, narodowy, new ArrayList<>(), new ArrayList<>());
        meczRepository.save(mecz);

        // 5. Tworzymy Bramki i Kartki do meczu
        Bramka b1 = new Bramka(null, 15, mecz, p2); // Gol Josue w 15 minucie
        Bramka b2 = new Bramka(null, 89, mecz, p1); // Gol Jędrzejczyka w 89 minucie
        bramkaRepository.save(b1);
        bramkaRepository.save(b2);

        Kartka k1 = new Kartka(null, KolorKartki.ZOLTA, 33, mecz, p3);
        Kartka k2 = new Kartka(null, KolorKartki.CZERWONA, 77, mecz, p1);
        kartkaRepository.save(k1);
        kartkaRepository.save(k2);
        
        System.out.println("✅ Przykładowe dane zostały załadowane do bazy H2!");
    }
}