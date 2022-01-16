package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Const;
import com.coelho.supermarkettracker.domain.PagesEnum;
import com.coelho.supermarkettracker.domain.Product;
import com.coelho.supermarkettracker.service.ProductService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/products")
public class ProductView extends VerticalLayout {

    public ProductView(ProductService service) {
        HorizontalLayout hrzToolbar = new HorizontalLayout();
        add(hrzToolbar);
        ViewUtils.addButtonsMenu(hrzToolbar, PagesEnum.PRODUCTS);

        add(new Label("List of " + Const.PRODUCTS));

        GridCrud<Product> crud = new GridCrud<Product>(Product.class, service);
        crud.getGrid().getColumnByKey("id").setVisible(false);
        add(crud);
        setSizeFull();
    }
}
