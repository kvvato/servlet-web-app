package ru.astondevs.service.impl;

import ru.astondevs.dto.SaleDto;
import ru.astondevs.entity.Sale;
import ru.astondevs.mapper.SaleMapper;
import ru.astondevs.mapper.impl.SaleMapperImpl;
import ru.astondevs.repository.SaleRepository;
import ru.astondevs.repository.impl.SaleRepositoryImpl;
import ru.astondevs.service.SaleService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SaleServiceImpl implements SaleService {
    private final SaleMapper mapper = new SaleMapperImpl();
    private final SaleRepository repository;

    public SaleServiceImpl() {
        repository = new SaleRepositoryImpl();
    }

    public SaleServiceImpl(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public SaleDto add(SaleDto saleDto) {
        Sale sale = mapper.fromDto(saleDto);
        sale = repository.add(sale);
        return mapper.toDto(sale);
    }

    @Override
    public SaleDto get(long id) {
        return mapper.toDto(repository.findById(id));
    }

    @Override
    public List<SaleDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDto> getBySellerId(long sellerId) {
        return repository.findSalesBySellerId(sellerId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(SaleDto sale) throws SQLException {
        repository.update(mapper.fromDto(sale));
    }

    @Override
    public void remove(long id) throws SQLException {
        repository.delete(id);
    }
}
