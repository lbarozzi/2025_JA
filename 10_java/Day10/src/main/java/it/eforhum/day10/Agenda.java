package it.eforhum.day10;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {
    private List<Appuntamento> appuntamenti;

    public Agenda() {
        this.appuntamenti = new ArrayList<>();
    }

    public void aggiungiAppuntamento(Appuntamento appuntamento) {
        appuntamenti.add(appuntamento);
    }

    public void rimuoviAppuntamento(Appuntamento appuntamento) {
        appuntamenti.remove(appuntamento);
    }

    public List<Appuntamento> getAppuntamenti() {
        return new ArrayList<>(appuntamenti);
    }

    public List<Appuntamento> getAppuntamentiPerGiorno(LocalDate giorno) {
        return appuntamenti.stream()
            .filter(app -> app.getData().toLocalDate().equals(giorno))
            .sorted((a, b) -> a.getData().compareTo(b.getData()))
            .collect(Collectors.toList());
    }

    public List<Appuntamento> getAppuntamentiPerMese(int anno, int mese) {
        return appuntamenti.stream()
            .filter(app -> app.getData().getYear() == anno && 
                          app.getData().getMonthValue() == mese)
            .sorted((a, b) -> a.getData().compareTo(b.getData()))
            .collect(Collectors.toList());
    }
}