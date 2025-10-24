package it.eforhum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
/**
 * Hello world!
 * https://pastebin.com/61mm8U6c
 */
public class App {
    public static void main(String[] args) {
        //* OK
        HibernateExample();
        //System.exit(0);
        //*/
        /*
        System.out.println("Hello World!");
    
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eforhum", 
        "root", "4nt4n1")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("userid"));
                System.out.println("First Name: " + rs.getString("firstName"));
                System.out.println("Last Name: " + rs.getString("lastName"));
            }
            //Insert new User
            var t = new Scanner(System.in);
            System.out.print("Enter first name: ");
            String first = t.nextLine();
            System.out.print("Enter last name: ");
            String last = t.nextLine();
            
            int rowsAffected = stmt.executeUpdate("INSERT INTO users (firstName, lastName) VALUES ('"
                + first + "', '" + last + "')", Statement.RETURN_GENERATED_KEYS);
            var keys = stmt.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
            System.out.println( String.format("Rows affected: %d, New ID: %d", rowsAffected, id));
            keys.close();
            rs.close();
            stmt.close();
            conn.close();
            t.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //*/
    }
    public static void HibernateExample() {
        // Ottieni SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        
        // Esempio di operazioni con Hibernate
        try (Session session = sessionFactory.openSession()) {
            // Mostra tutti gli utenti
            System.out.println("Lista degli utenti:");
            //Query<User> query = session.createQuery("from User", User.class);
            Query<User> query = session.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.phoneNumbers", User.class);
            List<User> users = query.list();
            
            for (User user : users) {
                System.out.println(user);
                for (PhoneNumber pn : user.getPhoneNumbers()) {
                    System.out.println("phone  " + pn);
                } 
                
            }
            
            // Inserisci un nuovo utente
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci nome: ");
            String firstName = scanner.nextLine();
            System.out.print("Inserisci cognome: ");
            String lastName = scanner.nextLine();
            
            User newUser = new User(firstName, lastName);
            
            Transaction transaction = session.beginTransaction();
            session.persist(newUser);
            transaction.commit();
            
            System.out.println("Nuovo utente creato con ID: " + newUser.getUserid());
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

//record User(int userid, String firstName, String lastName) {}

//record PhoneNumber(int phoneid, int userid, String phoneNumber) {}
