package cz.etyka.exam.pub.controller;

import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.entity.PubOrder;
import cz.etyka.exam.pub.service.DrinkService;
import cz.etyka.exam.pub.service.OrderService;
import cz.etyka.exam.pub.service.UserService;
import cz.etyka.exam.pub.summary.AllSummary;
import cz.etyka.exam.pub.summary.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static cz.etyka.exam.pub.util.Helpers.iterableToList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private DrinkService drinkService;

    // TODO: Doesn't take order amount into account when returning user's drinks
    @RequestMapping(value = "/user", method = GET)
    public Iterable<UserSummary> getUserSummary() {
        return iterableToList(userService.getUsers()).stream()
//                .filter(User::isActive) // Filter inactive users?
                .map(u -> {
                    List<Drink> usersDrinks = orderService.getUsersDrinks(u.getId());
                    BigDecimal finalPrice = usersDrinks.stream().map(Drink::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

                    return UserSummary.builder()
                            .userId(u.getId())
                            .drinks(usersDrinks)
                            .price(finalPrice)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/all", method = GET)
    public Iterable<AllSummary> getAllSummary() {
        return iterableToList(drinkService.getMenu()).stream()
                .map(d -> {
                    List<PubOrder> orders = orderService.getOrdersByDrink(d.getId());
                    int totalAmount = orders.stream().map(PubOrder::getAmount).reduce(0, Integer::sum);
                    BigDecimal totalPrice = d.getPrice().multiply(BigDecimal.valueOf(totalAmount));

                    return AllSummary.builder()
                            .product(d)
                            .amount(totalAmount)
                            .unitPrice(d.getPrice())
                            .totalPrice(totalPrice)
                            .build();
                })
                .collect(Collectors.toList());
    }


}
