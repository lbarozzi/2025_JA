package it.eforhum.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

// OCA
import static java.lang.Math.random;
import static java.lang.Math.PI;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Consumer; 

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        int a=5;
        int b=10;

        List<String> Lista = new ArrayList<String>();
        Lista.add("Ciao");
        Lista.add("pippo");
        Lista.add("pluto");
        Lista.add("paperino");  
        Lista.add("alice");
        Lista.add("topolino");  
        Lista.add("arancia");
        Lista.add("banana");
        Lista.add("kiwi");
        Lista.add("ananas");

        Function<String, String> myL = (String t) -> String.format("Elemento in lista: %s", t);
        Consumer<String> myOut = (String t) -> System.out.println(String.format("Elemento in lista: %s", t));
        Lista.forEach(
            //            (i)-> System.out.println(myL.apply(i))
            myOut
            );

        var ModLis=Lista.stream()
            .peek(System.err::println)
            .filter( (String t)->t.startsWith("a"))
            .map( (String t)->t.toUpperCase() )
            .distinct()
            .sorted()
            .peek(System.err::println)
            .toList();
            //.collect(Collectors.toList());

        ModLis.stream().forEach(System.out::println);

        //ModLis.stream().forEach(System.out::println);
        
        //Fatture
        var Contabilita= Stream.generate(Fattura::Casuale)
                                .limit(10)
                                .collect(
                                    HashMap::new,
                                    (m,f)->m.put(f.getId(), f),
                                    HashMap::putAll
                                );

                                   

    Contabilita.values().stream()
            .sorted()
            .forEach(System.out::println);
    }  
}
