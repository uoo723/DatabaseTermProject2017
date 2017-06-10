package com.sangwoo.possystem.data.mapper;

import com.sangwoo.possystem.data.model.DMenu;
import com.sangwoo.possystem.domain.model.Menu;

public class MenuMapper {

    public static Menu toMenu(DMenu dMenu) {
        int id = dMenu.id().intValue();
        String name = dMenu.name();
        int price = dMenu.price().intValue();

        return new Menu(id, name, price);
    }
}
