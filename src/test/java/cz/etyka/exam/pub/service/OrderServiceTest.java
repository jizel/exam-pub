package cz.etyka.exam.pub.service;

import cz.etyka.exam.pub.entity.Drink;
import cz.etyka.exam.pub.entity.PubOrder;
import cz.etyka.exam.pub.entity.Role;
import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.exception.NotAdultException;
import cz.etyka.exam.pub.repository.OrderRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static cz.etyka.exam.pub.entity.Role.RoleType.GUEST;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
class OrderServiceTest {

    @MockBean private UserService mockUserService;
    @MockBean private DrinkService mockDrinkService;
    @MockBean private OrderRepository mockOrderRepository;
    @InjectMocks OrderService service;
    private Drink beerIPA;
    private Drink soda;
    private User alcoholic;
    private User teenager;
    private PubOrder twoBeersOrder;
    private PubOrder sodaOrder;
    private List<PubOrder> orders;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        beerIPA = Drink.builder().id(1L).productName("IPA").isForAdult(true).price(BigDecimal.valueOf(40)).build();
        soda = Drink.builder().id(2L).productName("Sparkling Water").isForAdult(false).price(BigDecimal.valueOf(20)).build();

        alcoholic = User.builder()
                .id(1L)
                .name("testUser")
                .password("1234")
                .isActive(true)
                .isAdult(true)
                .pocket(BigDecimal.valueOf(100))
                .roles(singletonList(Role.of(GUEST)))
                .build();
        teenager = User.builder()
                .id(2L)
                .name("teenager")
                .password("4321")
                .isActive(true)
                .isAdult(false)
                .pocket(BigDecimal.valueOf(100))
                .roles(singletonList(Role.of(GUEST)))
                .build();

        twoBeersOrder = PubOrder.builder().id(1L).amount(2).drink(beerIPA).user(alcoholic).build();
        sodaOrder = PubOrder.builder().id(2L).amount(1).drink(soda).user(teenager).build();

        orders = Arrays.asList(twoBeersOrder, sodaOrder);

        when(mockDrinkService.getDrink(1L)).thenReturn(beerIPA);
        when(mockDrinkService.getDrink(2L)).thenReturn(soda);

        when(mockUserService.getUser(1L)).thenReturn(alcoholic);
        when(mockUserService.getUser(2L)).thenReturn(teenager);
    }

    @Test
    @DisplayName("Buy two beers legally test")
    @SneakyThrows
    void buyBeersTest() {
        when(mockOrderRepository.save(any())).thenReturn(twoBeersOrder);

        PubOrder order = service.buy(1L, 1L, 2);
        assertThat(order.getAmount(), equalTo(2));
        assertThat(order.getDrink(), equalTo(beerIPA));
        assertThat(order.getUser(), equalTo(alcoholic));
        assertThat(order.getPrice(), equalTo(BigDecimal.valueOf(80)));

    }

    @Test
    @DisplayName("Buy soda underage test")
    @SneakyThrows
    void buySodaTest() {
        when(mockOrderRepository.save(any())).thenReturn(sodaOrder);

        PubOrder order = service.buy(2L, 2L, 1);
        assertThat(order.getAmount(), equalTo(1));
        assertThat(order.getDrink(), equalTo(soda));
        assertThat(order.getUser(), equalTo(teenager));
        assertThat(order.getPrice(), equalTo(BigDecimal.valueOf(20)));

    }

    @Test
    @DisplayName("Underage attempt to buy a beer")
    void buyBeerUndarage() {
        when(mockOrderRepository.save(any())).thenReturn(twoBeersOrder);

        Exception thrownException = assertThrows(NotAdultException.class, () -> service.buy(2L, 1L, 2));
        String expectedMessage = "User teenager is not and adult!";
        assertTrue(thrownException.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Get all drinks for a user")
    void getUsersDrinksTest() {
        when(mockOrderRepository.findAll()).thenReturn(orders);
        when(mockOrderRepository.save(any())).thenReturn(twoBeersOrder);

        List<Drink> drinks = service.getUsersDrinks(1L);

        assertThat(drinks.size(), equalTo(2));
        assertThat(drinks.get(0), equalTo(beerIPA));
        assertThat(drinks.get(1), equalTo(beerIPA));
    }

    @Test
    @DisplayName("Get all orders of a drink")
    void getOrdersByDrinkTest() {
        when(mockOrderRepository.findAll()).thenReturn(orders);
        when(mockOrderRepository.save(any())).thenReturn(twoBeersOrder);

        List<PubOrder> orders = service.getOrdersByDrink(1L);

        assertThat(orders.size(), equalTo(1));
        assertThat(orders.get(0), equalTo(twoBeersOrder));
    }

    @Test
    @DisplayName("Get all orders of a drink")
    void getOrdersByDrinkInvalidIdTest() {
        when(mockOrderRepository.findAll()).thenReturn(emptyList());
        when(mockOrderRepository.save(any())).thenReturn(null);

        List<PubOrder> orders = service.getOrdersByDrink(5L);

        assertThat(orders.size(), equalTo(0));
    }

}
