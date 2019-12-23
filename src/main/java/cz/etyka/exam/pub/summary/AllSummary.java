package cz.etyka.exam.pub.summary;

import cz.etyka.exam.pub.entity.Drink;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AllSummary {
    private Drink product;
    private int amount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

}
