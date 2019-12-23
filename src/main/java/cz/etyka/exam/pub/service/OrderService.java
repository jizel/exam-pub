package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.repository.OrderRepository;
import cz.etyka.exam.pub.entity.PubOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static cz.etyka.exam.pub.util.Helpers.iterableToList;

@Service
public class OrderService {

    @Autowired private OrderRepository repository;
    @Autowired private UserService userService;
    @Autowired private DrinkService drinkService;

    public PubOrder buy(long userId, long productId, int amount){
        PubOrder newOrder = PubOrder.builder()
                .amount(amount)
                .drink(drinkService.getDrink(productId))
                .user(userService.getUser(userId))
                .build();

        PubOrder result =  saveOrder(newOrder);
        // Not a very nice side effect
        userService.deductMoney(userId, result.getPrice());
        return result;

    }

    public List<Drink> getUsersDrinks(long userId) {
        return iterableToList(getOrders()).stream()
                .filter(o -> o.getUser().getId() == userId)
                .map(PubOrder::getDrink)
                .collect(Collectors.toList());
    }

    public List<User> getDrinkers(long productId) {
        return getOrdersByDrink(productId).stream()
                .map(PubOrder::getUser)
                .collect(Collectors.toList());
    }

    public List<PubOrder> getOrdersByDrink(long productId) {
        return iterableToList(getOrders()).stream()
                .filter(o -> o.getDrink().getId() == productId)
                .collect(Collectors.toList());
    }

    private PubOrder saveOrder(PubOrder order){
        if (order.getUser().getPocket().compareTo(order.getPrice()) < 0 ){
            //TODO: Replace with custom RuntimeException
            throw new IllegalArgumentException("Insufficient cash in users pocket. User: " + order.getUser().getName());
        }
        if (!order.getUser().isAdult() && order.getDrink().isForAdult()){
            throw new IllegalArgumentException("User " + order.getUser().getName() + " is not and adult!");
        }
        return repository.save(order);
    }

    //TODO: Make private when api is finished
    public Iterable<PubOrder> getOrders(){
        return repository.findAll();
    }
}
