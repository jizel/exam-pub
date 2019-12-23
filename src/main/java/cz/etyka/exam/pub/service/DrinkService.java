package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.repository.DrinkRepository;
import cz.etyka.exam.pub.entity.Drink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkService {

    @Autowired private DrinkRepository repository;

    public Iterable<Drink> getMenu(){
        return repository.findAll();
    }
    public Drink getDrink(long id) {return repository.getOne(id);}
}
