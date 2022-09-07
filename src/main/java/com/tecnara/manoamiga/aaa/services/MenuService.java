package com.tecnara.manoamiga.aaa.services;

import org.springframework.stereotype.Service;


@Service
public class MenuService implements IMenuService{

    @Override
    public int getNotificaciones() {
        return 1;
    }

    
}
