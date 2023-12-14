package ru.astondevs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerDto {
    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerDto sellerDto = (SellerDto) o;
        return id == sellerDto.id && Objects.equals(name, sellerDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
