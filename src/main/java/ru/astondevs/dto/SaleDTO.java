package ru.astondevs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private long id;
    private String date;
    private long seller;
    private long product;
    private int count;
    private double price;
    private double sum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDTO saleDTO = (SaleDTO) o;
        return id == saleDTO.id && seller == saleDTO.seller && product == saleDTO.product && count == saleDTO.count && Double.compare(saleDTO.price, price) == 0 && Double.compare(saleDTO.sum, sum) == 0 && Objects.equals(date, saleDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, seller, product, count, price, sum);
    }
}
