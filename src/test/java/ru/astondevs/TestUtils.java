package ru.astondevs;

import ru.astondevs.dto.ProductDto;
import ru.astondevs.dto.SaleDto;
import ru.astondevs.dto.SellerDto;
import ru.astondevs.entity.Product;
import ru.astondevs.entity.Sale;
import ru.astondevs.entity.Seller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

public class TestUtils {
    public static final LocalDateTime SALE_DATE = LocalDateTime.of(2023, Month.DECEMBER, 9, 16, 0);
    public static final Product PRODUCT = new Product(1L, "Ручка", new BigDecimal("20"));
    public static final ProductDto PRODUCT_DTO = new ProductDto(1L, "Ручка", new BigDecimal("20"));
    public static final Seller SELLER = new Seller(1L, "Иван");
    public static final SellerDto SELLER_DTO = new SellerDto(1L, "Иван");
    public static final Sale SALE = new Sale(1L, SALE_DATE, 1L, 1L, 2, new BigDecimal("20"), new BigDecimal("40"));
    public static final SaleDto SALE_DTO = new SaleDto(1L, "2023-12-09T16:00:00", 1L, 1L, 2, new BigDecimal("20"), new BigDecimal("40"));
}
