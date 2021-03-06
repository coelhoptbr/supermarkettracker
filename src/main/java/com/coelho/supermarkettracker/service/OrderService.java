package com.coelho.supermarkettracker.service;

import com.coelho.supermarkettracker.config.CustomMongoCollection;
import com.coelho.supermarkettracker.domain.*;
import com.coelho.supermarkettracker.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:build.properties")
})
public class OrderService implements CrudListener<Order> {

    private final MongoTemplate mngTempl;

    private final OrderRepository repo;

    @Value("${app.build.time}")
    String appBuildTimeStamp;

    @Value("${currency.symbol}")
    String currencySymbol;

    public OrderService(OrderRepository repo, MongoTemplate mngTempl) {
        this.repo = repo;
        this.mngTempl = mngTempl;
    }

    public CurrencyEnum getCurrency() {
        return CurrencyEnum.getByName(currencySymbol);
    }

    public String getAppBuildTimeStamp() {
        return appBuildTimeStamp;
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

    public Order getHighestLowestPrice(Product prod, Shop shop, MinMaxEnum isMaxMin) {
        if (prod == null || prod.getId() == null) {
            return null;
        }

        Sort.Direction directionSort = isMaxMin.equals(MinMaxEnum.MAX) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Query query = new org.springframework.data.mongodb.core.query.Query()
                .limit(1)
                .with(Sort.by(directionSort, "price"));

        query.addCriteria(Criteria.where(CustomMongoCollection
                .getCollectionNameWithPrefix("product").concat("._id")).is(prod.getId()));

        if (shop != null && shop.getId() != null) {
            query.addCriteria(Criteria.where(CustomMongoCollection
                    .getCollectionNameWithPrefix("shop").concat("._id")).is(shop.getId()));
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -12);
        Date fromDate = c.getTime();
        Date toDate = new Date();

        query.addCriteria(Criteria.where("date").gte(fromDate).lte(toDate));

        List<Order> list = mngTempl.find(query, Order.class);

        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;

    }

    public Long getNumberOfOrders(Product product) {
        return repo.countByProduct(product);
    }
}
