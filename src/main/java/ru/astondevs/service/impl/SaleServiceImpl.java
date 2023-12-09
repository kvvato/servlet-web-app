package ru.astondevs.service.impl;

import ru.astondevs.dto.SaleDTO;
import ru.astondevs.mapper.SaleMapper;
import ru.astondevs.repository.SaleRepository;
import ru.astondevs.service.SaleService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SaleServiceImpl implements SaleService {
    private final SaleRepository repository;
    private final SaleMapper mapper;

    public SaleServiceImpl(SaleRepository repository, SaleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SaleDTO add(SaleDTO sale) {
        long id = repository.add(mapper.toEntity(sale));
        sale.setId(id);
        return sale;
    }

    @Override
    public SaleDTO get(long id) {
        return mapper.toDto(repository.findById(id));
    }

    @Override
    public List<SaleDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> getBySeller(long sellerId) {
        return repository.findSalesBySeller(sellerId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(SaleDTO sale) throws SQLException {
        repository.update(mapper.toEntity(sale));
    }

    @Override
    public void remove(long id) throws SQLException {
        repository.delete(id);
    }
}
