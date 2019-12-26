package cz.etyka.exam.pub.util;

import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.entity.PubOrder;
import cz.etyka.exam.pub.entity.Role;
import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.repository.DrinkRepository;
import cz.etyka.exam.pub.repository.OrderRepository;
import cz.etyka.exam.pub.repository.RoleRepository;
import cz.etyka.exam.pub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

import static cz.etyka.exam.pub.entity.Role.RoleType.BARTENDER;
import static cz.etyka.exam.pub.entity.Role.RoleType.GUEST;
import static java.util.Collections.singletonList;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadEntities(DrinkRepository drinkRepository, RoleRepository roleRepository,
                                   UserRepository userRepository, OrderRepository orderRepository) {

        Drink beerIPA = Drink.builder().productName("IPA").isForAdult(true).price(BigDecimal.valueOf(40)).build();
        Drink beerAle = Drink.builder().productName("Light Ale").isForAdult(true).price(BigDecimal.valueOf(42)).build();
        Drink soda = Drink.builder().productName("Sparkling Water").isForAdult(false).price(BigDecimal.valueOf(20)).build();

        Role adminRole = Role.of(BARTENDER);
        Role userRole = Role.of(GUEST);

        User alcoholic = User.builder()
                .name("alcoholic")
                .password("1234")
                .isActive(true)
                .isAdult(true)
                .pocket(BigDecimal.valueOf(1000))
                .roles(singletonList(userRole))
                .build();
        User teenager = User.builder()
                .name("teenager")
                .password("4321")
                .isActive(true)
                .isAdult(false)
                .pocket(BigDecimal.valueOf(100))
                .roles(singletonList(userRole))
                .build();
        User bartender = User.builder()
                .name("bartender")
                .password("admin")
                .isActive(true)
                .isAdult(true)
                .pocket(BigDecimal.ZERO)
                .roles(singletonList(adminRole))
                .build();

        PubOrder order1 = PubOrder.builder().amount(2).drink(beerIPA).user(alcoholic).build();
        PubOrder order2 = PubOrder.builder().amount(2).drink(soda).user(teenager).build();

        return args -> {
            drinkRepository.save(beerIPA);
            drinkRepository.save(beerAle);
            drinkRepository.save(soda);

            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            userRepository.save(alcoholic);
            userRepository.save(bartender);
            userRepository.save(teenager);

            orderRepository.save(order1);
            orderRepository.save(order2);
        };
    }

}
