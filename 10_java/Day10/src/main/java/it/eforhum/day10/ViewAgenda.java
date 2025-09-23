package it.eforhum.day10;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ViewAgenda {
    private Agenda agenda;

    public ViewAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public void stampaAgendaMensile(int anno, int mese) {
        YearMonth yearMonth = YearMonth.of(anno, mese);
        LocalDate primoGiorno = yearMonth.atDay(1);
        final int SIZE = 130;
        
        System.out.println("\n" + "=".repeat(SIZE));
        System.out.println(String.format("AGENDA - %s %d", 
            primoGiorno.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN).toUpperCase(), 
            anno));
        System.out.println("=".repeat(SIZE));
        
        // Intestazione giorni della settimana
        System.out.printf("%-19s %-19s %-19s %-19s %-19s %-19s %-19s%n",
            "LUNEDÌ", "MARTEDÌ", "MERCOLEDÌ", "GIOVEDÌ", "VENERDÌ", "SABATO", "DOMENICA");
        System.out.println("-".repeat(SIZE));

        // Calcola il primo lunedì da mostrare
        LocalDate dataCorrente = primoGiorno;
        int giorniIndietro = (primoGiorno.getDayOfWeek().getValue() - 1) % 7;
        dataCorrente = dataCorrente.minusDays(giorniIndietro);

        // Stampa le settimane
        while (dataCorrente.getMonthValue() == mese || 
               dataCorrente.isBefore(primoGiorno.plusDays(7))) {
            
            stampaSettimana(dataCorrente, mese);
            dataCorrente = dataCorrente.plusWeeks(1);
            
            if (dataCorrente.getMonthValue() != mese && 
                dataCorrente.isAfter(yearMonth.atEndOfMonth())) {
                break;
            }
        }
        
        System.out.println("=".repeat(SIZE));
    }

    private void stampaSettimana(LocalDate inizioSettimana, int meseCorrente) {
        LocalDate[] giorni = new LocalDate[7];
        for (int i = 0; i < 7; i++) {
            giorni[i] = inizioSettimana.plusDays(i);
        }

        // Prima riga: numeri dei giorni
        for (int i = 0; i < 7; i++) {
            LocalDate giorno = giorni[i];
            if (giorno.getMonthValue() == meseCorrente) {
                System.out.printf("%-19s ", String.format("%2d", giorno.getDayOfMonth()));
            } else {
                System.out.printf("%-19s ", "  ");
            }
        }
        System.out.println();

        // Righe successive: appuntamenti (max 3 per cella)
        for (int riga = 0; riga < 3; riga++) {
            for (int i = 0; i < 7; i++) {
                LocalDate giorno = giorni[i];
                String contenuto = "";
                
                if (giorno.getMonthValue() == meseCorrente) {
                    List<Appuntamento> appuntamentiGiorno = agenda.getAppuntamentiPerGiorno(giorno);
                    if (riga < appuntamentiGiorno.size()) {
                        Appuntamento app = appuntamentiGiorno.get(riga);
                        String testo = app.getData().format(DateTimeFormatter.ofPattern("HH:mm")) + 
                                     " " + app.getArgomento();
                        contenuto = testo.length() > 17 ? testo.substring(0, 16) + "…" : testo;
                    }
                }
                
                System.out.printf("%-19s ", contenuto);
            }
            System.out.println();
        }
        
        System.out.println(); // Riga vuota tra settimane
    }
}