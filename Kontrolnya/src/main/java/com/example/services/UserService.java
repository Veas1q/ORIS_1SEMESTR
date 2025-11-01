package com.example.services;

import com.example.models.Player;
import com.example.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserRepository userRepository = new  UserRepository();

    public List<Player> findAll() throws SQLException {
        return userRepository.findAll();
    }

    public List<Player> findByNickname(String name) throws SQLException {
        return userRepository.findByNickname(name);
    }

    public void addPlayer(Player player) throws SQLException {
        userRepository.addPlayer(player);
    }

    public void updatePlayer(Player player, String oldNickname) throws SQLException {
        userRepository.updatePlayer(player, oldNickname);
    }
}
