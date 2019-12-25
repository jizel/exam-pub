package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.exception.NotEnoughCashException;
import cz.etyka.exam.pub.repository.UserRepository;
import cz.etyka.exam.pub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository repository;

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUser(long userId) throws IllegalArgumentException {
        return repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
    }

    void deductMoney(long userId, BigDecimal amount) throws NotEnoughCashException {
        User user = repository.getOne(userId);
        user.deduct(amount);
        repository.save(user);
    }
}
