package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.repository.DrinkRepository;
import cz.etyka.exam.pub.entity.Drink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkService {

    @Autowired private DrinkRepository repository;

    public List<Drink> getMenu(){
        return repository.findAll();
    }
    Drink getDrink(long id) {return repository.getOne(id);}
}
