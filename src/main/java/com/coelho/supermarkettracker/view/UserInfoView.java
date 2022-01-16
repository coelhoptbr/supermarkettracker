package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.Const;
import com.coelho.supermarkettracker.domain.PagesEnum;
import com.coelho.supermarkettracker.domain.UserInfo;
import com.coelho.supermarkettracker.service.UserInfoService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/userinfo")
public class UserInfoView extends VerticalLayout implements HasDynamicTitle {

    private String title = "Price Tracker - Users";

    @Override
    public String getPageTitle() {
        return title;
    }

    public UserInfoView(UserInfoService service) {
        HorizontalLayout hrzToolbar = new HorizontalLayout();
        add(hrzToolbar);
        ViewUtils.addButtonsMenu(hrzToolbar, PagesEnum.USERINFO);

        add(new Label("List of " + Const.USERINFO));

        GridCrud<UserInfo> crud = new GridCrud<UserInfo>(UserInfo.class, service);
        crud.getGrid().getColumnByKey("id").setVisible(false);
        add(crud);
        setSizeFull();
    }
}
