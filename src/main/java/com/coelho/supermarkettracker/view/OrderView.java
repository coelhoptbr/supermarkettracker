package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Const;
import com.coelho.supermarkettracker.domain.MinMaxEnum;
import com.coelho.supermarkettracker.domain.Order;
import com.coelho.supermarkettracker.domain.PagesEnum;
import com.coelho.supermarkettracker.domain.Product;
import com.coelho.supermarkettracker.domain.Shop;
import com.coelho.supermarkettracker.domain.UserInfo;
import com.coelho.supermarkettracker.service.OrderService;
import com.coelho.supermarkettracker.service.ProductService;
import com.coelho.supermarkettracker.service.ShopService;
import com.coelho.supermarkettracker.service.UserInfoService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import org.bson.types.ObjectId;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Optional;

@Route("/order")
public class OrderView  extends VerticalLayout implements HasDynamicTitle {

    private ComboBox<UserInfo> cbUserInfo = new ComboBox<>(Const.COMBOBOX_USERINFO_LABEL);
    private ComboBox<Product> cbProduct = new ComboBox<>(Const.COMBOBOX_PRODUCT_LABEL);
    private ComboBox<Shop> cbShop = new ComboBox<>(Const.COMBOBOX_SHOP_LABEL);
    private DatePicker dateOrder = new DatePicker(Const.DATEPICKER_ORDER_DATE_LABEL);
    private NumberField txtPrice = new NumberField(Const.NUMBERFIELD_PRICE_LABEL);
    private Checkbox ckbSpecialOffer = new Checkbox(Const.CHECKBOX_SPECIAL_OFFER_LABEL);
    private Label lblMoreExpensive = new Label(Const.MSG_NO_HISTORY_MORE_EXPENSIVE);
    private Label lblLessExpensive = new Label(Const.MSG_NO_HISTORY_LESS_EXPENSIVE);
    private GridCrud<Order> orderCrudGrid;
    private Dialog dlgInsertNewOrder;
    private ObjectId idOrderInEditMode;
    private final OrderService ordService;

    private String title = "Price Tracker - Orders";

    @Override
    public String getPageTitle() {
        return title;
    }

    public OrderView(OrderService ordService, UserInfoService uiService, ProductService prService,
                     ShopService shService) {
        this.ordService = ordService;

        // creating dialog popup
        dlgInsertNewOrder = new Dialog();
        add(dlgInsertNewOrder);

        // creating a horizontal layout inside the dialog popup for the toolbar
        HorizontalLayout hrzLayoutToolbar = new HorizontalLayout();
        add(hrzLayoutToolbar);

        HorizontalLayout hrzLayoutToolbar2 = new HorizontalLayout();
        add(hrzLayoutToolbar2);

        // creating a vertical layout inside the dialog popup for the data fields
        VerticalLayout vrtLayoutDlgNewOrder = new VerticalLayout();
        dlgInsertNewOrder.add(vrtLayoutDlgNewOrder);

            insertCbUserInfo(uiService, vrtLayoutDlgNewOrder);

            insertCbShop(shService, vrtLayoutDlgNewOrder);

            vrtLayoutDlgNewOrder.add(dateOrder);

            insertCbProduct(prService, vrtLayoutDlgNewOrder);

            txtPrice.setSuffixComponent(getCurrencySuffixDiv());

            vrtLayoutDlgNewOrder.add(txtPrice, ckbSpecialOffer);

        // creating a horizontal layout inside the dialog popup for the buttons
        HorizontalLayout hrzLayoutDlgNewOrder = new HorizontalLayout();
        dlgInsertNewOrder.add(hrzLayoutDlgNewOrder);

            Button btnSave = new Button(Const.SAVE, e-> this.insertOrder());

            Button btnClose = new Button(Const.CLOSE, e-> dlgInsertNewOrder.close());

            hrzLayoutDlgNewOrder.add(btnSave, btnClose);

        // creating a vertical layout inside the dialog popup for the summary
        VerticalLayout vrtLayoutSummary = new VerticalLayout();
        dlgInsertNewOrder.add(vrtLayoutSummary);

            vrtLayoutSummary.add(lblLessExpensive, lblMoreExpensive);

        // creating the button new order
        Button btnNewOrder = new Button(Const.ORDER_NEW, e -> openPopUpForInserting());
        // creating the button update order
        Button btnUpdateOrder = new Button(Const.ORDER_UPDATE, e -> updateOrder());
        // creating the button Download report
        Button btnDownloadReport = new Button(Const.REPORT, e -> downloadReport());

        hrzLayoutToolbar.add(btnNewOrder, btnUpdateOrder);
        hrzLayoutToolbar2.add(btnDownloadReport);

        ViewUtils.addButtonsMenu(hrzLayoutToolbar2, PagesEnum.ORDER);

        // grid
        add(new Label("List of " + Const.ORDERS));

        add(getOrderGrid(ordService));

        add(new Label("Build timestamp: " + ordService.getAppBuildTimeStamp()));

        setSizeFull();
    }

    private void openPopUpForInserting() {
        this.idOrderInEditMode = null;
        clearScreenForInsert();
        cbUserInfo.clear();
        cbShop.clear();
        dlgInsertNewOrder.open();
    }

    private GridCrud<Order> getOrderGrid(OrderService service) {
        orderCrudGrid = new GridCrud<Order>(Order.class, ordService);

        orderCrudGrid.getGrid().setColumns("date", "shop.name", "product.name", "price");

        orderCrudGrid.getGrid().getColumnByKey("shop.name").setHeader("Shop");
        orderCrudGrid.getGrid().getColumnByKey("product.name").setHeader("Product");

        orderCrudGrid.getGrid().setColumnReorderingAllowed(true);
        orderCrudGrid.getGrid().setVerticalScrollingEnabled(true);

        orderCrudGrid.getAddButton().setVisible(false);
        orderCrudGrid.getUpdateButton().setVisible(false);

        return orderCrudGrid;
    }

    private Div getCurrencySuffixDiv() {
        Div euroSuffix = new Div();
        euroSuffix.setText(ordService.getCurrency().getSymbol());
        return euroSuffix;
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

        if (this.idOrderInEditMode == null) {
            ordService.add(ord);
            clearScreenForInsert();
        } else {
            ord.setId(this.idOrderInEditMode);
            ordService.update(ord);
            dlgInsertNewOrder.close();
        }

        orderCrudGrid.getGrid().setItems(ordService.findAll());

        return ord;
    }

    private void updateOrder() {
         Optional<Order> selOrder = orderCrudGrid.getGrid().getSelectionModel().getFirstSelectedItem();
         if (selOrder.isPresent()) {

             Order order = selOrder.get();
             this.idOrderInEditMode = order.getId();
             cbUserInfo.setValue(order.getUserInfo());
             cbShop.setValue(order.getShop());
             cbProduct.setValue(order.getProduct());
             dateOrder.setValue(order.getDate());
             txtPrice.setValue(order.getPrice());
             ckbSpecialOffer.setValue(order.getIsOffer());

             dlgInsertNewOrder.open();
         } else {
             showErrorNotification("Select an Order to update on the list");
         }
    }

    private void showErrorNotification(String message) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(message));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    private void clearScreenForInsert() {
        cbProduct.clear();
        txtPrice.clear();
        ckbSpecialOffer.clear();
    }

    private void showMoreExpensive(Product prod) {
        Order order = ordService.getHighestLowestPrice(
                cbProduct.getValue(), null, MinMaxEnum.MAX);
        if (order != null) {
            lblMoreExpensive.setText("More expensive was in " + order.getShop().getName()
                    + " on " + order.getDate().toString() + " at a cost of "
                    + ordService.getCurrency().getSymbol() + " "  + order.getPrice().toString());
        } else {
            lblMoreExpensive.setText(Const.MSG_NO_HISTORY_MORE_EXPENSIVE);
        }
    }

    private void showLessExpensive(Product prod) {
        Order order = ordService.getHighestLowestPrice(
                cbProduct.getValue(), null, MinMaxEnum.MIN);
        if (order != null) {
            lblLessExpensive.setText("Less expensive was in " + order.getShop().getName()
                    + " on " + order.getDate().toString() + " at a cost of "
                    + ordService.getCurrency().getSymbol() + " " + order.getPrice().toString());
        } else {
            lblLessExpensive.setText(Const.MSG_NO_HISTORY_LESS_EXPENSIVE);
        }
    }

    private void downloadReport() {
        getUI().get().getPage().open("/avgpricereport/download", "_new");
    }
}
