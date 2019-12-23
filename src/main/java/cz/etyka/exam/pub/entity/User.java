package cz.etyka.exam.pub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties("hibernateLazyInitializer")
public class User {

    private @Id @GeneratedValue long id;
    @NonNull private String name;
    private boolean isActive;
    private boolean isAdult;
    @NonNull private BigDecimal pocket;

    public BigDecimal deduct(BigDecimal amount) {
        if (pocket.compareTo(amount) < 0 ){
            //TODO: Replace with custom RuntimeException
            throw new IllegalArgumentException("User " + name + " cannot afford to pay " + amount.toString() +
                    ". He only has got " + pocket.toString());
        }
        return this.pocket = pocket.subtract(amount);
    }
}
