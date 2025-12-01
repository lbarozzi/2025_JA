package it.eforhum;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class CatFactService {
    
    @Inject
    ObjectMapper objectMapper;
    
    private List<CatFact> catFacts;
    private Random random = new Random();

    public void loadCatFacts() {
        if (catFacts == null) {
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("catsfacts.json")) {
                CatFactsData data = objectMapper.readValue(is, CatFactsData.class);
                catFacts = data.data;
            } catch (Exception e) {
                throw new RuntimeException("Errore nel caricamento di catsfacts.json", e);
            }
        }
    }

    public CatFact getRandomFact() {
        loadCatFacts();
        return catFacts.get(random.nextInt(catFacts.size()));
    }

    public List<CatFact> getRandomFacts(int limit) {
        loadCatFacts();
        List<CatFact> allFacts = new ArrayList<>(catFacts);
        Collections.shuffle(allFacts);
        return allFacts.subList(0, Math.min(limit, allFacts.size()));
    }
}
