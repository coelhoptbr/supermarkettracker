package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Shop;
import com.coelho.supermarkettracker.service.ShopService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/shops")
public class ShopView extends VerticalLayout {

    public ShopView(ShopService service) {
        GridCrud<Shop> crud = new GridCrud<Shop>(Shop.class, service);
        add(crud);
        setSizeFull();
    }
}
