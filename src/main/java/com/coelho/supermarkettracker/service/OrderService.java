package com.coelho.supermarkettracker.service;

import com.coelho.supermarkettracker.domain.Order;
import com.coelho.supermarkettracker.repo.OrderRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Repository
public class OrderService implements CrudListener<Order> {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }


    @Override
    public Collection<Order> findAll() {
        return repo.findAll();
    }

    @Override
    public Order add(Order order) {
        return repo.insert(order);
    }

    @Override
    public Order update(Order order) {
        return repo.save(order);
    }

    @Override
    public void delete(Order order) {
        repo.delete(order);
    }
}
