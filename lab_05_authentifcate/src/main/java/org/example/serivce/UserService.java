package org.example.serivce;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

public class UserService {
    private BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
    private UserRepository userRepository = new UserRepository();

    public void addUser(User user) throws Exception {

        user.setHashPassword(
                bCrypt.encode(user.getHashPassword()));

        userRepository.addUser(user);
    }

    public User findByUsername(String username) throws Exception {
        return userRepository.findByUsername(username);
    }

    public User validateUser(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null){
            return null;
        }
        if (bCrypt.matches(password, user.getHashPassword())){
            return user;
        }else {
            return null;
        }
    }

}