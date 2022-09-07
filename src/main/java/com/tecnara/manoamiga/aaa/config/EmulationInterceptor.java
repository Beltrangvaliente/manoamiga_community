package com.tecnara.manoamiga.aaa.config;

import com.tecnara.manoamiga.act.services.IEmulationActService;
import com.tecnara.manoamiga.doc.services.IEmulationDocService;
import com.tecnara.manoamiga.emp.services.IEmulationEmpService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.tecnara.manoamiga.expo.services.IEmulationExpoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@Component
public class EmulationInterceptor implements HandlerInterceptor {

    @Value(value = "${tecnara.datos-prueba}")
    private String datosPrueba;
    
    @Autowired
    private IEmulationActService emu1;
    
    @Autowired
    private IEmulationDocService emu2;

    @Autowired
    private IEmulationExpoService emu3;

    @Autowired
    private IEmulationEmpService emu4;
    
    @Autowired
    private SessionInfo sessionInfo;
    
    private PasswordEncoder passEnc=new BCryptPasswordEncoder();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("si".equalsIgnoreCase(datosPrueba)){
            emu1.generarDatosDePrueba(passEnc);
            emu2.generarDatosDePrueba(passEnc);
            emu3.generarDatosDePrueba(passEnc);
            emu4.generarDatosDePrueba(passEnc);
        }
        if (request.getParameter("theme")!=null){
            sessionInfo.setTheme(request.getParameter("theme"));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
}

