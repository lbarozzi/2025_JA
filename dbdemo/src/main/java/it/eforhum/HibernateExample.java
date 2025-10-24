package it.eforhum;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class HibernateExample {
    public static void main(String[] args) {
        // Ottieni SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        
        // Esempio di operazioni con Hibernate
        try (Session session = sessionFactory.openSession()) {
            // Mostra tutti gli utenti
            System.out.println("Lista degli utenti:");
            Query<User> query = session.createQuery(
                "from User u left join fetch u.phoneNumbers where u.firstName like :name ",
                 User.class);
            query.setParameter("name", "%eo%");
            List<User> users = query.list();
            
            Transaction transaction = session.beginTransaction();
            for (User user : users) {
                System.out.println(user);
                for (PhoneNumber pn : user.getPhoneNumbers()) {
                    System.out.println("phone  " + pn);
                }
                if (user.getPhoneNumbers().isEmpty()) {
                    var p = new PhoneNumber( user.getUserid(), "<nessuno>");
                    session.persist(p);
                    System.out.println("phone  " + p);
                }
            }
            transaction.commit();
            
            
            // Inserisci un nuovo utente
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci [Y/N]=");
            var cont=scanner.nextLine();
            
            if (cont.equalsIgnoreCase("y")) {
                System.out.print("Inserisci nome: ");
                String firstName = scanner.nextLine();
                System.out.print("Inserisci cognome: ");
                String lastName = scanner.nextLine();
                
                User newUser = new User(firstName, lastName);
                
                /*Transaction*/ transaction = session.beginTransaction();
                session.persist(newUser);
                transaction.commit();
                
                System.out.println("Nuovo utente creato con ID: " + newUser.getUserid());
            } 
            scanner.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Chiudi SessionFactory quando hai finito
            System.err.println("Closing...");
            HibernateUtil.shutdown();
        }
    }
}