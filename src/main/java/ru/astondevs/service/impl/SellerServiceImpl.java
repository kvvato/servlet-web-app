package ru.astondevs.service.impl;

import ru.astondevs.dto.SellerDto;
import ru.astondevs.entity.Seller;
import ru.astondevs.mapper.SellerMapper;
import ru.astondevs.mapper.impl.SellerMapperImpl;
import ru.astondevs.repository.SellerRepository;
import ru.astondevs.repository.impl.SellerRepositoryImpl;
import ru.astondevs.service.SellerService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SellerServiceImpl implements SellerService {
    private final SellerMapper mapper = new SellerMapperImpl();
    private final SellerRepository repository;

    public SellerServiceImpl() {
        repository = new SellerRepositoryImpl();
    }

    public SellerServiceImpl(SellerRepository repository) {
        this.repository = repository;
    }

    @Override
    public SellerDto add(SellerDto sellerDto) {
        Seller seller = mapper.fromDto(sellerDto);
        seller = repository.add(seller);
        return mapper.toDto(seller);
    }

    @Override
    public SellerDto get(long id) {
        return mapper.toDto(repository.findById(id));
    }

    @Override
    public List<SellerDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(SellerDto seller) throws SQLException {
        repository.update(mapper.fromDto(seller));
    }

    @Override
    public void remove(long id) throws SQLException {
        repository.delete(id);
    }
}
