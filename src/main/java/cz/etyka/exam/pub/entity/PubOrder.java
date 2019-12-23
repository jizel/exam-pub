package cz.etyka.exam.pub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties("hibernateLazyInitializer")
public class PubOrder {

    private @Id @GeneratedValue long id;
    private int amount;
    @NonNull @ManyToOne private User user;
    @NonNull @OneToOne private Drink drink;
    private BigDecimal price;

    public BigDecimal getPrice(){
        return this.drink.getPrice().multiply(BigDecimal.valueOf(this.amount));
    }

}
