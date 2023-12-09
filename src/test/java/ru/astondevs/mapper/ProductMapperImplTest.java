package ru.astondevs.mapper;

import org.junit.jupiter.api.Test;
import ru.astondevs.dto.ProductDTO;
import ru.astondevs.entity.Product;
import ru.astondevs.mapper.impl.ProductMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperImplTest {
    private final Product product = new Product(1L, "Ручка", 20.0);
    private final ProductDTO productDTO = new ProductDTO(1L, "Ручка", 20.0);
    private final ProductMapper productMapper = new ProductMapperImpl();

    @Test
    public void testToDto() {
        ProductDTO actual = productMapper.toDto(product);
        assertEquals(productDTO, actual);
    }

    @Test
    public void testToEntity() {
        Product actual = productMapper.toEntity(productDTO);
        assertEquals(product, actual);
    }
}
