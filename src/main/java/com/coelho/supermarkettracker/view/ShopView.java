package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Const;
import com.coelho.supermarkettracker.domain.PagesEnum;
import com.coelho.supermarkettracker.domain.Shop;
import com.coelho.supermarkettracker.service.ShopService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/shops")
public class ShopView extends VerticalLayout implements HasDynamicTitle {

    private String title = "Price Tracker - Shops";

    @Override
    public String getPageTitle() {
        return title;
    }

    public ShopView(ShopService service) {
        HorizontalLayout hrzToolbar = new HorizontalLayout();
        add(hrzToolbar);
        ViewUtils.addButtonsMenu(hrzToolbar, PagesEnum.SHOPS);

        add(new Label("List of " + Const.SHOPS));

        GridCrud<Shop> crud = new GridCrud<Shop>(Shop.class, service);
        crud.getGrid().getColumnByKey("id").setVisible(false);
        add(crud);
        setSizeFull();
    }
}
