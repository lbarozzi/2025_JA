package it.eforhum.day10;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appuntamento {
    private LocalDateTime data;
    private int durataMinuti;
    private String argomento;

    public Appuntamento(LocalDateTime data, int durataMinuti, String argomento) {
        this.data = data;
        this.durataMinuti = durataMinuti;
        this.argomento = argomento;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getDurataMinuti() {
        return durataMinuti;
    }

    public void setDurataMinuti(int durataMinuti) {
        this.durataMinuti = durataMinuti;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public LocalDateTime getDataFine() {
        return data.plusMinutes(durataMinuti);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return String.format("%s-%s %s", 
            data.format(formatter), 
            getDataFine().format(formatter), 
            argomento);
    }
}