package ru.astondevs.service.impl;

import ru.astondevs.dto.ProductDTO;
import ru.astondevs.mapper.ProductMapper;
import ru.astondevs.repository.ProductRepository;
import ru.astondevs.service.ProductService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO add(ProductDTO product) {
        long id = repository.add(mapper.toEntity(product));
        product.setId(id);
        return product;
    }

    @Override
    public ProductDTO get(long id) {
        return mapper.toDto(repository.findById(id));
    }

    @Override
    public List<ProductDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getSoldProductsBySeller(long sellerId) {
        return repository.findSoldProductsBySeller(sellerId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(ProductDTO product) throws SQLException {
        repository.update(mapper.toEntity(product));
    }

    @Override
    public void remove(long id) throws SQLException {
        repository.delete(id);
    }
}
