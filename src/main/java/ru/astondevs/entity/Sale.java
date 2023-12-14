package ru.astondevs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    private long id;
    private LocalDateTime date;
    private long seller;
    private long product;
    private int count;
    private BigDecimal price;
    private BigDecimal sum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return id == sale.id && seller == sale.seller && product == sale.product && count == sale.count && sale.price.compareTo(price) == 0 && sale.sum.compareTo(sum) == 0 && Objects.equals(date, sale.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, seller, product, count, price, sum);
    }
}
