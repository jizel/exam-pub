package cz.etyka.exam.pub.summary;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DrinkSummary {
    private long productId;
    private int amount;
    private BigDecimal price;
}
