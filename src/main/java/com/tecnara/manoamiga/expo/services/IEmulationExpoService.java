package com.tecnara.manoamiga.expo.services;

import org.springframework.security.crypto.password.PasswordEncoder;


public interface IEmulationExpoService {
    
    public void generarDatosDePrueba(PasswordEncoder passEnc);
}
