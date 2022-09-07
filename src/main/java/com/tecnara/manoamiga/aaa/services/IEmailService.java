package com.tecnara.manoamiga.aaa.services;

import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;


public interface IEmailService {

    public void enviarEmail(EmailInfoDto info);
    public void resetConfiguration();
    
}
