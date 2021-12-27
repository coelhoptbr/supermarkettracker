package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Order;
import com.coelho.supermarkettracker.service.OrderService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/order")
public class OrderView  extends VerticalLayout {

    public OrderView(OrderService service) {
        GridCrud<Order> crud = new GridCrud<Order>(Order.class, service);
        add(crud);
        setSizeFull();
    }

}
