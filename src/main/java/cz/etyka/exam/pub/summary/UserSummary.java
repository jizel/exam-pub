package cz.etyka.exam.pub.summary;

import cz.etyka.exam.pub.entity.Drink;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserSummary {
    private long userId;
    private Iterable<Drink> drinks;
    private BigDecimal price;
}
