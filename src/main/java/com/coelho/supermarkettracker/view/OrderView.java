package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Order;
import com.coelho.supermarkettracker.domain.Product;
import com.coelho.supermarkettracker.domain.Shop;
import com.coelho.supermarkettracker.domain.UserInfo;
import com.coelho.supermarkettracker.service.OrderService;
import com.coelho.supermarkettracker.service.ProductService;
import com.coelho.supermarkettracker.service.ShopService;
import com.coelho.supermarkettracker.service.UserInfoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/order")
public class OrderView  extends VerticalLayout {

    public OrderView(OrderService service, UserInfoService uiService, ProductService prService,
                     ShopService shService) {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Nova compra");

        VerticalLayout dialogLayout = new VerticalLayout();
        dialog.add(dialogLayout);

        ComboBox<UserInfo> cbUserInfo = new ComboBox<>("Select your user");
        cbUserInfo.setItems(uiService.findAll());
        cbUserInfo.setItemLabelGenerator(UserInfo::getFullName);
        dialogLayout.add(cbUserInfo);

        ComboBox<Product> cbProduct = new ComboBox<>("Select your product");
        cbProduct.setItems(prService.findAll());
        cbProduct.setItemLabelGenerator(Product::getName);
        dialogLayout.add(cbProduct);

        ComboBox<Shop> cbShop = new ComboBox<>("Select your shop");
        cbShop.setItems(shService.findAll());
        cbShop.setItemLabelGenerator(Shop::getName);
        dialogLayout.add(cbShop);

        Button button = new Button("Mostrar tela", e -> dialog.open());

        add(dialog, button);



        GridCrud<Order> crud = new GridCrud<Order>(Order.class, service);
        add(crud);
        setSizeFull();
    }

}
