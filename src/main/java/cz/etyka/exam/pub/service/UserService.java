package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.repository.UserRepository;
import cz.etyka.exam.pub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired private UserRepository repository;

    public Iterable<User> getUsers(){
        return repository.findAll();
    }

    public User getUser(long userId){
        return repository.getOne(userId);
    }

    void deductMoney(long userId, BigDecimal amount) {
        User user = repository.getOne(userId);
        user.deduct(amount);
        repository.save(user);
    }
}
