package com.coelho.supermarkettracker.view;

import com.coelho.supermarkettracker.domain.UserInfo;
import com.coelho.supermarkettracker.service.UserInfoService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("/userinfo")
public class UserInfoView extends VerticalLayout {

    public UserInfoView(UserInfoService service) {
        GridCrud<UserInfo> crud = new GridCrud<UserInfo>(UserInfo.class, service);
        add(crud);
        setSizeFull();
    }
}
