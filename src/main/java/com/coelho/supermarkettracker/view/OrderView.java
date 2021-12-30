package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.MinMaxEnum;
import com.coelho.supermarkettracker.domain.Order;
import com.coelho.supermarkettracker.domain.Product;
import com.coelho.supermarkettracker.domain.Shop;
import com.coelho.supermarkettracker.domain.UserInfo;
import com.coelho.supermarkettracker.service.OrderService;
import com.coelho.supermarkettracker.service.ProductService;
import com.coelho.supermarkettracker.service.ShopService;
import com.coelho.supermarkettracker.service.UserInfoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/order")
public class OrderView  extends VerticalLayout {

    private ComboBox<UserInfo> cbUserInfo = new ComboBox<>("Select your user");
    private ComboBox<Product> cbProduct = new ComboBox<>("Select your product");
    private ComboBox<Shop> cbShop = new ComboBox<>("Select your shop");
    private DatePicker dateOrder = new DatePicker("Order date");
    private NumberField txtPrice = new NumberField("Price");
    private Checkbox ckbSpecialOffer = new Checkbox("Special offer");
    private GridCrud<Order> crud;
    private Label lblMoreExpensive = new Label("No history found for more expensive");
    private Label lblLessExpensive = new Label("No history found for less expensive");

    private final OrderService ordService;

    public OrderView(OrderService ordService, UserInfoService uiService, ProductService prService,
                     ShopService shService) {
        this.ordService = ordService;

        Dialog dlgInsertNewOrder = new Dialog();
        add(dlgInsertNewOrder);

        Button btnNewOrder = new Button("New Order", e -> dlgInsertNewOrder.open());
        add(btnNewOrder);

        // dialog composition
        VerticalLayout vrtLayoutDlgNewOrder = new VerticalLayout();
        dlgInsertNewOrder.add(vrtLayoutDlgNewOrder);

        HorizontalLayout hrzLayoutDlgNewOrder = new HorizontalLayout();
        dlgInsertNewOrder.add(hrzLayoutDlgNewOrder);

        VerticalLayout vrtLayoutSummary = new VerticalLayout();
        dlgInsertNewOrder.add(vrtLayoutSummary);

        vrtLayoutSummary.add(lblLessExpensive, lblMoreExpensive);

        insertCbUserInfo(uiService, vrtLayoutDlgNewOrder);
        insertCbShop(shService, vrtLayoutDlgNewOrder);

        vrtLayoutDlgNewOrder.add(dateOrder);

        insertCbProduct(prService, vrtLayoutDlgNewOrder);

        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        txtPrice.setSuffixComponent(euroSuffix);

        vrtLayoutDlgNewOrder.add(txtPrice, ckbSpecialOffer);


        Button btnSave = new Button("Save", e-> this.insertOrder());
        Button btnCancel = new Button("Cancel", e-> dlgInsertNewOrder.close());
        hrzLayoutDlgNewOrder.add(btnSave, btnCancel);


        crud = new GridCrud<Order>(Order.class, ordService);
        crud.getGrid().setColumns("date", "userInfo.fullName", "shop.name", "product.name", "price", "isOffer");
        crud.getGrid().setItems(ordService.findAll());
        crud.getAddButton().setVisible(false);
        crud.getUpdateButton().setVisible(false);

        add(crud);

        setSizeFull();
    }

    private Boolean insertCbUserInfo(UserInfoService service, VerticalLayout target) {
        cbUserInfo = new ComboBox<>("Select your user");
        cbUserInfo.setItems(service.findAll());
        cbUserInfo.setItemLabelGenerator(UserInfo::getFullName);
        target.add(cbUserInfo);
        return true;
    }

    private Boolean insertCbProduct(ProductService service, VerticalLayout target) {
        cbProduct = new ComboBox<>("Select your product");
        cbProduct.setItems(service.findAll());
        cbProduct.setItemLabelGenerator(Product::getName);

        cbProduct.addValueChangeListener(prodEvent -> {
            showLessExpensive(prodEvent.getValue());
            showMoreExpensive(prodEvent.getValue());
        });

        target.add(cbProduct);
        return true;
    }

    private Boolean insertCbShop(ShopService service, VerticalLayout target) {
        cbShop = new ComboBox<>("Select your shop");
        cbShop.setItems(service.findAll());
        cbShop.setItemLabelGenerator(Shop::getName);
        target.add(cbShop);
        return true;
    }

    private Order insertOrder() {
        Order ord = new Order();
        ord.setUserInfo(cbUserInfo.getValue());
        ord.setProduct(cbProduct.getValue());
        ord.setShop(cbShop.getValue());
        ord.setDate(dateOrder.getValue());
        ord.setPrice(txtPrice.getValue());
        ord.setIsOffer(ckbSpecialOffer.getValue());
        ordService.add(ord);

        clearScreen();

        crud.getGrid().setItems(ordService.findAll());

        return ord;
    }

    private void clearScreen() {
        cbProduct.clear();
        txtPrice.clear();
        ckbSpecialOffer.clear();
    }

    private void showMoreExpensive(Product prod) {
        Order order = ordService.getHighestLowestPrice(
                cbProduct.getValue(), null, MinMaxEnum.MAX);
        if (order != null) {
            lblMoreExpensive.setText("More expensive was in " + order.getShop().getName()
                    + " on " + order.getDate().toString() + " at a cost of " + order.getPrice().toString());
        }
    }

    private void showLessExpensive(Product prod) {
        Order order = ordService.getHighestLowestPrice(
                cbProduct.getValue(), null, MinMaxEnum.MIN);
        if (order != null) {
            lblLessExpensive.setText("Less expensive was in " + order.getShop().getName()
                    + " on " + order.getDate().toString() + " at a cost of " + order.getPrice().toString());
        }
    }
}
