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
public class Drink {

    private @Id @GeneratedValue long id;
    @NonNull private String productName;
    private boolean isForAdult;
    @NonNull private BigDecimal price;
}
