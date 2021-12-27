package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Product;
import com.coelho.supermarkettracker.service.ProductService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/products")
public class ProductView extends VerticalLayout {

    public ProductView(ProductService service) {
        GridCrud<Product> crud = new GridCrud<Product>(Product.class, service);
        add(crud);
        setSizeFull();
    }
}
