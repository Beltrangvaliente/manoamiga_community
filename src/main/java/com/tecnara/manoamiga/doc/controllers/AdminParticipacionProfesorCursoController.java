package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.entities.ParticipacionProfesorCursoEntity;
import com.tecnara.manoamiga.doc.entities.ProfesorEntity;
import com.tecnara.manoamiga.doc.repositories.IParticipacionProfesorCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc/admin/participacion_profesor_curso")
public class AdminParticipacionProfesorCursoController extends TecnaraBaseController{
  
    @Autowired

 private IParticipacionProfesorCursoRepository repoParticipacionProfesorCurso;
    
    @Autowired
    private IProfesorRepository repoProfesor;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ParticipacionProfesorCursoEntity());
        } else {
            m.addAttribute("registro", repoParticipacionProfesorCurso.findById(id).get());
        }
        return "doc/admin/participacion_profesor_curso_form"; //Es el html
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ParticipacionProfesorCursoEntity());
        } else {
            m.addAttribute("registro", repoParticipacionProfesorCurso.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/participacion_profesor_curso_form"; //Es el html
    }
    
    
    
    @PostMapping("/guardar")
    public String guardar(Model m, ParticipacionProfesorCursoEntity registro) {
        if (repoParticipacionProfesorCurso.findByIdProfesorAndIdCurso(registro.getIdProfesor(), registro.getIdCurso()).isPresent()){
            return "redirect:form?error=El profesor ya existe";
        }
        repoParticipacionProfesorCurso.save(registro);
        return "redirect:list";
    }
    
    
     @GetMapping("/borrar")
    public String borrar(Long id){
        repoParticipacionProfesorCurso.deleteById(id);
        return "redirect:list";   
    }
    
    

}
