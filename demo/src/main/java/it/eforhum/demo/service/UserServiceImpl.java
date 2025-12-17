package it.eforhum.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eforhum.demo.entity.User;
import it.eforhum.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private PasswdService passwdService;

    @Override
    public User createUser(User user) {
        user.setPassword(passwdService.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    
}
