package cz.etyka.exam.pub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.etyka.exam.pub.exception.NotEnoughCashException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties("hibernateLazyInitializer")
public class User {

    @Id
    @GeneratedValue
    private long id;
    @NonNull private String name;
    private boolean isActive;
    private boolean isAdult;
    @NonNull private BigDecimal pocket;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    /**
     * Deducts money from user's pocket
     *
     * @param amount amount of money to be deducted
     * @return Pocket money after deduction
     * @throws NotEnoughCashException
     */
    public BigDecimal deduct(BigDecimal amount) throws NotEnoughCashException {
        if (pocket.compareTo(amount) < 0) {
            throw new NotEnoughCashException("User " + name + " cannot afford to pay " + amount.toString() +
                    ". He only has got " + pocket.toString());
        }
        return this.pocket = pocket.subtract(amount);
    }
}
