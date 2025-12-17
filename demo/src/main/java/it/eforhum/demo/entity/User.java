package it.eforhum.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    //@NotEmpty  
    String name;

    String surname;
    //@Email
    String email;
    String password;
    
    Boolean isValid;
    Boolean isActive;
    Boolean isVerified;
}
