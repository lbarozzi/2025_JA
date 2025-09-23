package it.eforhum.day10;

import java.time.LocalDateTime;

/**
 * Esempio di utilizzo dell'agenda
 */
public class App {
    public static void main(String[] args) {
        // Crea un'agenda di esempio
        Agenda agenda = new Agenda();
        var oggi = LocalDateTime.now();
        // Aggiungi alcuni appuntamenti con date e orari casuali nel mese corrente
        agenda.aggiungiAppuntamento(new Appuntamento(
            LocalDateTime.of(oggi.getYear(), oggi.getMonth(), (int)(Math.random()*28)+1, (int)(9+Math.random()*8), (int)(Math.random()*60)),
            60, "Riunione team"));
        agenda.aggiungiAppuntamento(new Appuntamento(
            LocalDateTime.of(oggi.getYear(), oggi.getMonth(), (int)(Math.random()*28)+1, (int)(9+Math.random()*8), (int)(Math.random()*60)),
            90, "Presentazione progetto"));
        agenda.aggiungiAppuntamento(new Appuntamento(
            LocalDateTime.of(oggi.getYear(), oggi.getMonth(), (int)(Math.random()*28)+1, (int)(9+Math.random()*8), (int)(Math.random()*60)),
            30, "Call cliente"));
        agenda.aggiungiAppuntamento(new Appuntamento(
            LocalDateTime.of(oggi.getYear(), oggi.getMonth(), (int)(Math.random()*28)+1, (int)(9+Math.random()*8), (int)(Math.random()*60)),
            45, "Code review"));
        agenda.aggiungiAppuntamento(new Appuntamento(
            LocalDateTime.of(oggi.getYear(), oggi.getMonth(), (int)(Math.random()*28)+1, (int)(9+Math.random()*8), (int)(Math.random()*60)),
            120, "Workshop Java"));

        // Crea la vista e stampa l'agenda del mese corrente
        ViewAgenda view = new ViewAgenda(agenda);
        view.stampaAgendaMensile(oggi.getYear(), oggi.getMonthValue());
        
        System.out.println("Hello World!");
    }
}
