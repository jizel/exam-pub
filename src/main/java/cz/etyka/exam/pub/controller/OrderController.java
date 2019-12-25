package cz.etyka.exam.pub.controller;

import cz.etyka.exam.pub.entity.PubOrder;
import cz.etyka.exam.pub.exception.NotEnoughCashException;
import cz.etyka.exam.pub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class OrderController {

    @Autowired private OrderService service;

    @PostMapping(value = "/buy")
    public PubOrder buyDrink(@RequestParam long userId, @RequestParam long productId, @RequestParam int amount) {
        try {
            return service.buy(userId, productId, amount);
        } catch (NotEnoughCashException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Unable to buy this item: " + e.getLocalizedMessage(), e);
        }
    }

}
