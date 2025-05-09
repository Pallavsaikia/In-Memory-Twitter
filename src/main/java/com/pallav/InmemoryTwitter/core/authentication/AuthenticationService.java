package com.pallav.InmemoryTwitter.core.authentication;




import com.pallav.InmemoryTwitter.core.user.User;
import com.pallav.InmemoryTwitter.core.user.repo.UserRepository;
import com.pallav.InmemoryTwitter.exceptions.AuthenticationException;
import com.pallav.InmemoryTwitter.in_memory_db.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String email, String password) {
        // Check if username or email already exist
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already taken.");
        }

        // Create a new user with a generated ID
        String id = UUID.randomUUID().toString();
        User user = new User(id, username, email, password);

        // Save the user
        userRepository.save(user);

        return user;
    }

    public User login(String username, String password) {

        Set<User> userSet= userRepository.findByUsername(username);
        if(userSet.isEmpty()){
            throw new AuthenticationException("Wrong Credentials");
        }
        for (User user : userSet) {
            if(!Objects.equals(password, user.password())){
                throw new AuthenticationException("Wrong Credentials");
            }
        }
        return  userSet.iterator().next();
    }
}
