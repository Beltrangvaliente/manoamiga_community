package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.AdministradorEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IAdministradorRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/falta_asistencia")
public class AdminFaltaAsistenciaController extends TecnaraBaseController{
    
    @Autowired
    private IFaltaAsistenciaRepository repoAsistencia;
    
    @GetMapping("/form") 
    public String form(Model m) {

        m.addAttribute("registro", new FaltaAsistenciaEntity());
        return "act/admin/falta_asistencia_form"; 
    }

    @GetMapping("/list")
    public String list(Model m){
        return "act/admin/falta_asistencia_list";    
    }
    
     @GetMapping("/help")
    public String help(Model m){
        return "act/admin/falta_asistencia_help";
    }
    
    @GetMapping("list_data")
    @ResponseBody
    public List<FaltaAsistenciaEntity> listData(){
        FaltaAsistenciaEntity fal1=new FaltaAsistenciaEntity();
        fal1.id=1L;
        fal1.motivo="Falta por enfermedad";
        
        FaltaAsistenciaEntity fal2=new FaltaAsistenciaEntity();
        fal2.id=2L;
        fal2.motivo="Falta por enfermedad";
        
        FaltaAsistenciaEntity fal3=new FaltaAsistenciaEntity();
        fal3.id=3L;
        fal3.motivo="Falta por enfermedad";
        

        List<FaltaAsistenciaEntity> lista=List.of(fal1, fal2, fal3);
        return lista;
        
    }
    
    @PostMapping("/guardar")
    public String guardar(FaltaAsistenciaEntity registro) {
        repoAsistencia.save(registro);
        return "redirect:list";
    }
    
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoAsistencia.deleteById(id);
        return "redirect:list";
    } 
    
    
}
