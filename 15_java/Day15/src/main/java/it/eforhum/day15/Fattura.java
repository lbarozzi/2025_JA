package it.eforhum.day15;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

// OCA
import static java.lang.Math.random;

public class Fattura implements Comparable<Fattura> {
    static int progressivo=0;
    String cliente;
    double importo;
    LocalDate data;
    int numero;
    List<String> voci;

    public Fattura(String cliente, double importo, LocalDate data, Optional<List<String>> voci) {
        this.cliente = cliente;
        this.importo = importo;
        this.data = data;
        this.numero = ++progressivo;
        //Optional USE
        if(voci.isPresent())
            this.voci = voci.get();
        else
            this.voci = new ArrayList<String>();
        

    }

    public Fattura(){
        this("",0.0,LocalDate.MIN,null);
    }

    @Override
    public String toString() {
        return String.format("Fattura %d/%s per il cliente %s di importo %.2f con voci %s",
                                    numero, data.getYear() , cliente, importo, voci.toString());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Fattura fattura = (Fattura) obj;
        return ((numero == fattura.numero)&&(data.equals(fattura.data))&&(cliente.equals(fattura.cliente)));
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(numero) + data.hashCode() + cliente.hashCode();
    }
    @Override
    public int compareTo(Fattura o) {
        int cmp = Integer.compare(this.data.getYear(), o.data.getYear());
        if(cmp==0) 
            cmp = Integer.compare(this.numero, o.numero);
        return cmp;
    }

    public void addVoce(String voce){
        voci.add(voce);
    }   
    public double getImporto(){
        return importo;
    }
    public String getCliente(){
        return cliente;
    }
    public String getId(){
        return String.format("%3d/%4d", numero, data.getYear());
    }

    //Fabbrica
    public static Fattura Casuale(){
        final String[] clienti={"Pippo","Pluto","Paperino","Topolino","Alice","Biancaneve","Cenerentola","Ariel","Belle","Jasmine"};
        final String[] voci={"Servizi","Prodotti","Consulenza","Manodopera","Materiale","Spese di viaggio","Spese di vitto e alloggio"};
        int cas = (int)(random()*100);
        int deltaAnno= LocalDate.now().getDayOfYear()-1;
        return new Fattura(
            clienti[(int)(random()*clienti.length)],
            random()*10000,
            LocalDate.now().minusDays((int)(random()*deltaAnno)),
            Optional.ofNullable(
                    Stream.generate(()->random())
                          .limit((int)(random()*5))
                          .map( (d)->voci[(int)(d*voci.length)] )
                          .distinct()
                          .toList()   
                    )
        );
    }
}
