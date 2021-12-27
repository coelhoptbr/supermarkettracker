package com.coelho.supermarkettracker.service;

import com.coelho.supermarkettracker.domain.Product;
import com.coelho.supermarkettracker.repo.ProductRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Repository
public class ProductService implements CrudListener<Product> {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Collection<Product> findAll() {
        return repo.findAll();
    }

    @Override
    public Product add(Product product) {
        return repo.insert(product);
    }

    @Override
    public Product update(Product product) {
        return repo.save(product);
    }

    @Override
    public void delete(Product product) {
        repo.delete(product);
    }
}
