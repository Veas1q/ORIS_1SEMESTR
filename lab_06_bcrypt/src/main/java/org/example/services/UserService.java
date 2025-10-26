package org.example.services;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public void addUser(User user) throws SQLException, ClassNotFoundException {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        user.setHashPassword(bCrypt.encode("admin"));
        userRepository.addUser(user);
    }
}


