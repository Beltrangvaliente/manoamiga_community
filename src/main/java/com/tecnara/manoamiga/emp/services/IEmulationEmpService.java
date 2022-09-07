
package com.tecnara.manoamiga.emp.services;

import org.springframework.security.crypto.password.PasswordEncoder;


public interface IEmulationEmpService {
    public void generarDatosDePrueba(PasswordEncoder passEnc);
}
