/*

 */
package com.tecnara.manoamiga.act.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.repositories.IHorarioRepository;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import java.util.List;
import org.hibernate.criterion.Projections;
import static org.hibernate.criterion.Projections.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/horario")
public class AdminHorarioController extends TecnaraBaseController{

   @Autowired
    private IHorarioRepository repoHorario;


    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/horario_list";
    }
    
    @GetMapping ("/form")
    public String form(Model m, Long id ){
        if (id==null){
        m.addAttribute("registro", new HorarioEntity());
        }else{
          m.addAttribute("registro",repoHorario.findById(id).get());
        }
        return "act/admin/horario_form";
    }
    
    @GetMapping("/list_data")
    @ResponseBody
    public List<HorarioEntity> listData() {
        return repoHorario.findAll();

}
     @PostMapping("/guardar")
    public String guardar(HorarioEntity registro) {
        repoHorario.save(registro);
        return "redirect:list";
}
      @GetMapping("/borrar")
    public String borrar(Long id) {
        repoHorario.deleteById(id);
        return "redirect:list";
}
}