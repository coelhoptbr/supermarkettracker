package com.coelho.supermarkettracker.service;

import com.coelho.supermarkettracker.domain.Shop;
import com.coelho.supermarkettracker.repo.ShopRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ShopService implements CrudListener<Shop> {

    private final ShopRepository repo;

    public ShopService(ShopRepository repo) {
        this.repo = repo;
    }

    @Override
    public Collection<Shop> findAll() {
        List<Shop> result = repo.findAll();
        result = result.stream().sorted(Comparator.comparing(Shop::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
        return result;
    }

    @Override
    public Shop add(Shop shop) {
        return repo.insert(shop);
    }

    @Override
    public Shop update(Shop shop) {
        return repo.save(shop);
    }

    @Override
    public void delete(Shop shop) {
        repo.delete(shop);
    }
}
