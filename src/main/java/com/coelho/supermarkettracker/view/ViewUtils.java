package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Const;
import com.coelho.supermarkettracker.domain.PagesEnum;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class ViewUtils {

    public static void addButtonsMenu(HorizontalLayout hrz, PagesEnum page) {

        if (!page.equals(PagesEnum.ORDER)) {
            Button btnOrder = new Button(Const.ORDERS, event -> UI.getCurrent().navigate("order"));
            hrz.add(btnOrder);
        }

        if (!page.equals(PagesEnum.SHOPS)) {
            Button btnShop = new Button(Const.SHOPS, event -> UI.getCurrent().navigate("shops"));
            hrz.add(btnShop);
        }

        if (!page.equals(PagesEnum.PRODUCTS)) {
            Button btnProduct = new Button(Const.PRODUCTS, event -> UI.getCurrent().navigate("products"));
            hrz.add(btnProduct);
        }
    }

}
