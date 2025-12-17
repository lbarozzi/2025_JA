package it.eforhum.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eforhum.demo.entity.Utente;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Utente, Long> {
    
    Utente findByEmail(String email); 

    List<Utente> findByIsActiveTrueAndIsVerifiedTrue();
}
