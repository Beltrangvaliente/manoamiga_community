package com.tecnara.manoamiga.act.services;

import com.tecnara.manoamiga.expo.services.*;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface IEmulationActService {
    
    public void generarDatosDePrueba(PasswordEncoder passEnc);
}
