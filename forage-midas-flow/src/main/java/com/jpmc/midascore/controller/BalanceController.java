package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.model.User;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam String userId) {
        User user = userRepository.findById(userId).orElse(null);
        double amount = (user != null) ? user.getBalance() : 0.0;
        return new Balance(amount);
    }
} 