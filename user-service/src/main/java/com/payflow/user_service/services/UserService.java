package com.payflow.user_service.services;

import com.payflow.user_service.dtos.UserRequestDTO;
import com.payflow.user_service.dtos.UserResponseDTO;
import com.payflow.user_service.entities.User;
import com.payflow.user_service.entities.UserType;
import com.payflow.user_service.exceptions.InsufficientBalanceException;
import com.payflow.user_service.exceptions.MerchantNotAllowedException;
import com.payflow.user_service.exceptions.UserNotFoundException;
import com.payflow.user_service.mappers.UserMapper;
import com.payflow.user_service.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserResponseDTO saveUser(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        User saved = saveUserEntity(user);
        return UserMapper.toDto(saved);
    }

    public User saveUserEntity(User user) {
        repository.save(user);
        return user;
    }

    public UserResponseDTO getUserById(Long id) {
        User user = getUserEntityById(id);
        return UserMapper.toDto(user);
    }

    public User getUserEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
    }

    public List<UserResponseDTO> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public Boolean canTransfer(Long id, BigDecimal amount)  {
        User sender = getUserEntityById(id);

        if (sender.getType() == UserType.MERCHANT) {
            throw new MerchantNotAllowedException("Usuário do tipo lojista não esta autorizado a realizar uma transação.");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente.");
        }

        return true;
    }

    public void updateBalance(Long senderId, Long receiverId, BigDecimal amount) {
        User sender = getUserEntityById(senderId);
        User receiver = getUserEntityById(receiverId);

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        saveUserEntity(sender);
        saveUserEntity(receiver);
    }
}
