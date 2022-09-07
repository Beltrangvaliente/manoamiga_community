package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.expo.controllers.*;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emp")
public class EmpController extends TecnaraBaseController {

    @GetMapping("/index")
    public String index() {
        String rol = general.getRole();
        if (rol.startsWith("ROLE_EMP_Tecnico")) {
            return "redirect:/emp/tecnico/index";
        }
        if (rol.startsWith("ROLE_EMP_Trabajador")) {
            return "redirect:/emp/trabajador/index";
        }
        return "redirect:/emp/public/usuario/login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:index";
    }

}
