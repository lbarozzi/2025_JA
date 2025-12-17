package it.eforhum.demo.service;
import it.eforhum.demo.entity.Utente;

public interface UserService {
    Utente createUser(Utente user);
    Utente findUserById(Long id);
    Utente findUserByEmail(String email);
}
