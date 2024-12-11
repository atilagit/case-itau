package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário com id "+ id + " não encontrado."));
    }

    public User findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).orElseThrow(() -> new UserNotFoundException("Usuário com a conta " + accountNumber + " não encontrado."));
    }

    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}