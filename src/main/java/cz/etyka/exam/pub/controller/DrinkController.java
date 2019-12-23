package cz.etyka.exam.pub.controller;

import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class DrinkController {

    @Autowired private DrinkService service;

    @RequestMapping(value = "/drink-menu", method = GET)
    public Iterable<Drink> getMenu() {

        return service.getMenu();
    }

}
