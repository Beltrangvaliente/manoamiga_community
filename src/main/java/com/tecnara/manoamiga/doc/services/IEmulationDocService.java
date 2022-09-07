package com.tecnara.manoamiga.doc.services;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface IEmulationDocService {
    
    public void generarDatosDePrueba(PasswordEncoder passEnc);
}
