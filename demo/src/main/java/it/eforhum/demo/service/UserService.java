package it.eforhum.demo.service;
import it.eforhum.demo.entity.User;

public interface UserService {
    User createUser(User user);
    User findUserById(Long id);
    User findUserByEmail(String email);
}
