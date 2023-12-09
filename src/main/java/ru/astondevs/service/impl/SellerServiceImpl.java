package ru.astondevs.service.impl;

import ru.astondevs.dto.SellerDTO;
import ru.astondevs.mapper.SellerMapper;
import ru.astondevs.repository.SellerRepository;
import ru.astondevs.service.SellerService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SellerServiceImpl implements SellerService {
    private final SellerRepository repository;
    private final SellerMapper mapper;

    public SellerServiceImpl(SellerRepository repository, SellerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SellerDTO add(SellerDTO seller) {
        long id = repository.add(mapper.toEntity(seller));
        seller.setId(id);
        return seller;
    }

    @Override
    public SellerDTO get(long id) {
        return mapper.toDto(repository.findById(id));
    }

    @Override
    public List<SellerDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(SellerDTO seller) throws SQLException {
        repository.update(mapper.toEntity(seller));
    }

    @Override
    public void remove(long id) throws SQLException {
        repository.delete(id);
    }
}
