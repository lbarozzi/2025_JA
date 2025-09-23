import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
     
    public static void main(String[] args) {
        var lista=new ArrayList<LocalDateTime>();
        for(int i=0;i<10;i++){
            lista.add(LocalDateTime.now().plusSeconds(i*((long)Math.random()*15+1)) );
        }   
        var th= new Chrono();
        th.setDaemon(true);
        th.setTimestamps(lista);
        th.start();

        System.out.println("Hello, World!");
        var zz= lista.stream().map(x->x.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).toList();
        for(var x:zz) System.out.println(x);
        System.out.println("Press Enter to exit");
        var t= new Scanner(System.in);
        t.nextLine();
        t.close();  
    }
}

class Chrono extends Thread {
        protected ArrayList<LocalDateTime> timestamps = new ArrayList<>();

        @Override
        public void run() {    
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (true) {
                for(LocalDateTime timestamp : timestamps) {
                    if (timestamp.isBefore(LocalDateTime.now())){
                        System.out.println("dued: " + formatter.format(timestamp));
                    }
                }

                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        
        public ArrayList<LocalDateTime> getTimestamps() {
            return this.timestamps;
        }
        public void setTimestamps(ArrayList<LocalDateTime> timestamps) {
            this.timestamps = timestamps;
        }
        
    }