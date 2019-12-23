package cz.etyka.exam.pub;


import cz.etyka.exam.pub.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    @Autowired
    private DrinkRepository drinkRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
