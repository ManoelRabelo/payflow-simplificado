package com.payflow.user_service.services;

import com.payflow.user_service.entities.User;
import com.payflow.user_service.entities.UserType;
import com.payflow.user_service.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        repository.save(user);
        return user;
    }

    public User getUserById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Boolean canTransfer(Long id, BigDecimal amount) throws Exception {
        User sender = getUserById(id);

        if (sender.getType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo lojista não esta autorizado a realizar uma transação.");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente.");
        }

        return true;
    }

    public void updateBalance(Long senderId, Long receiverId, BigDecimal amount) throws Exception {
        User sender = getUserById(senderId);
        User receiver = getUserById(receiverId);

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance((sender).getBalance().add(amount));

        saveUser(sender);
        saveUser(receiver);

    }
}
