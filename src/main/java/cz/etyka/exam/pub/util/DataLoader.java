package cz.etyka.exam.pub.util;

import cz.etyka.exam.pub.repository.UserRepository;
import cz.etyka.exam.pub.repository.DrinkRepository;
import cz.etyka.exam.pub.repository.OrderRepository;
import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.entity.PubOrder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadEntities(DrinkRepository drinkRepository, UserRepository userRepository, OrderRepository orderRepository) {

        Drink beerIPA = Drink.builder().id(1L).productName("IPA").isForAdult(true).price(BigDecimal.valueOf(40)).build();
        Drink beerAle = Drink.builder().id(2L).productName("Light Ale").isForAdult(true).price(BigDecimal.valueOf(42)).build();
        Drink soda = Drink.builder().id(3L).productName("Sparkling Water").isForAdult(false).price(BigDecimal.valueOf(20)).build();

        User user1 = User.builder().name("Alco Holic").isActive(true).isAdult(true).pocket(BigDecimal.valueOf(1000)).build();
        User user2 = User.builder().name("Table Sleeper").isActive(false).isAdult(true).pocket(BigDecimal.valueOf(500)).build();
        User user3 = User.builder().name("Curious Teenager").isActive(true).isAdult(false).pocket(BigDecimal.valueOf(100)).build();

        PubOrder order1 = PubOrder.builder().id(1L).amount(2).drink(beerIPA).user(user1).build();
        PubOrder order2 = PubOrder.builder().id(2L).amount(1).drink(beerAle).user(user2).build();
        PubOrder order3 = PubOrder.builder().id(3L).amount(2).drink(soda).user(user3).build();

        return args -> {
            drinkRepository.save(beerIPA);
            drinkRepository.save(beerAle);
            drinkRepository.save(soda);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            orderRepository.save(order1);
            orderRepository.save(order2);
            orderRepository.save(order3);
        };
    }
}
