package ru.astondevs.service.impl;

import ru.astondevs.dto.ProductDto;
import ru.astondevs.entity.Product;
import ru.astondevs.mapper.ProductMapper;
import ru.astondevs.mapper.impl.ProductMapperImpl;
import ru.astondevs.repository.ProductRepository;
import ru.astondevs.repository.impl.ProductRepositoryImpl;
import ru.astondevs.service.ProductService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = new ProductMapperImpl();
    private final ProductRepository repository;

    public ProductServiceImpl() {
        repository = new ProductRepositoryImpl();
    }

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDto add(ProductDto productDto) {
        Product product = mapper.fromDto(productDto);
        product = repository.add(product);
        return mapper.toDto(product);
    }

    @Override
    public ProductDto get(long id) {
        return mapper.toDto(repository.findById(id));
    }

    @Override
    public List<ProductDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getSoldProductsBySellerId(long sellerId) {
        return repository.findSoldProductsBySellerId(sellerId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(ProductDto product) throws SQLException {
        repository.update(mapper.fromDto(product));
    }

    @Override
    public void remove(long id) throws SQLException {
        repository.delete(id);
    }
}
