package ru.astondevs.mapper;

import org.junit.jupiter.api.Test;
import ru.astondevs.dto.ProductDto;
import ru.astondevs.entity.Product;
import ru.astondevs.mapper.impl.ProductMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.astondevs.TestUtils.PRODUCT;
import static ru.astondevs.TestUtils.PRODUCT_DTO;

class ProductMapperImplTest {
    private final ProductMapper productMapper = new ProductMapperImpl();

    @Test
    public void testToDto() {
        ProductDto actual = productMapper.toDto(PRODUCT);
        assertEquals(PRODUCT_DTO, actual);
    }

    @Test
    public void testToEntity() {
        Product actual = productMapper.fromDto(PRODUCT_DTO);
        assertEquals(PRODUCT, actual);
    }
}
