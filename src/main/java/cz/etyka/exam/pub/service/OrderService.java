package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.entity.PubOrder;
import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.exception.NotEnoughCashException;
import cz.etyka.exam.pub.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static cz.etyka.exam.pub.util.Helpers.iterableToList;

@Service
public class OrderService {

    @Autowired private OrderRepository repository;
    @Autowired private UserService userService;
    @Autowired private DrinkService drinkService;

    public PubOrder buy(long userId, long productId, int amount) throws NotEnoughCashException {
        PubOrder newOrder = PubOrder.builder()
                .amount(amount)
                .drink(drinkService.getDrink(productId))
                .user(userService.getUser(userId))
                .build();

        return saveOrder(newOrder);

    }

    public List<Drink> getUsersDrinks(long userId) {

        return iterableToList(getOrders()).stream()
                .filter(o -> o.getUser().getId() == userId)
                .map(o -> Collections.nCopies(o.getAmount(), o.getDrink()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<PubOrder> getOrdersByDrink(long productId) {
        return iterableToList(getOrders()).stream()
                .filter(o -> o.getDrink().getId() == productId)
                .collect(Collectors.toList());
    }

    private PubOrder saveOrder(PubOrder order) throws NotEnoughCashException, IllegalArgumentException {
        if (!order.getUser().isAdult() && order.getDrink().isForAdult()) {
            throw new IllegalArgumentException("User " + order.getUser().getName() + " is not and adult!");
        }
        userService.deductMoney(order.getUser().getId(), order.getPrice());

        return repository.save(order);
    }

    private Iterable<PubOrder> getOrders() {
        return repository.findAll();
    }
}
