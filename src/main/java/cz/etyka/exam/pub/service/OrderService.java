package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.entity.PubOrder;
import cz.etyka.exam.pub.exception.NotAdultException;
import cz.etyka.exam.pub.exception.NotEnoughCashException;
import cz.etyka.exam.pub.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired private OrderRepository repository;
    @Autowired private UserService userService;
    @Autowired private DrinkService drinkService;

    /**
     * User buying product. Creates new order and deducts money from user's pocket.
     *
     * @param userId
     * @param productId
     * @param amount
     * @return Created PubOrder
     * @throws NotEnoughCashException, IllegalArgumentException
     */
    public PubOrder buy(long userId, long productId, int amount) throws NotEnoughCashException, NotAdultException {
        PubOrder newOrder = PubOrder.builder()
                .amount(amount)
                .drink(drinkService.getDrink(productId))
                .user(userService.getUser(userId))
                .build();

        return saveOrder(newOrder);

    }

    /**
     * Gets all drinks ordered by a specific user
     * @param userId
     * @return List of user's drinks
     */
    public List<Drink> getUsersDrinks(long userId) {

        return getOrders().stream()
                .filter(o -> o.getUser().getId() == userId)
                .map(o -> Collections.nCopies(o.getAmount(), o.getDrink()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Gets all orders of a specific drink
     * @param productId
     * @return List of orders
     */
    public List<PubOrder> getOrdersByDrink(long productId) {
        return getOrders().stream()
                .filter(o -> o.getDrink().getId() == productId)
                .collect(Collectors.toList());
    }

    private PubOrder saveOrder(PubOrder order) throws NotEnoughCashException, NotAdultException {
        if (!order.getUser().isAdult() && order.getDrink().isForAdult()) {
            throw new NotAdultException("User " + order.getUser().getName() + " is not and adult!");
        }
        userService.deductMoney(order.getUser().getId(), order.getPrice());

        return repository.save(order);
    }

    private List<PubOrder> getOrders() {
        return repository.findAll();
    }
}
