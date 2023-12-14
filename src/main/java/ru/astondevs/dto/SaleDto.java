package ru.astondevs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDto {
    private long id;
    private String date;
    private long seller;
    private long product;
    private int count;
    private BigDecimal price;
    private BigDecimal sum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDto saleDto = (SaleDto) o;
        return id == saleDto.id && seller == saleDto.seller && product == saleDto.product && count == saleDto.count && saleDto.price.compareTo(price) == 0 && saleDto.sum.compareTo(sum) == 0 && Objects.equals(date, saleDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, seller, product, count, price, sum);
    }
}
