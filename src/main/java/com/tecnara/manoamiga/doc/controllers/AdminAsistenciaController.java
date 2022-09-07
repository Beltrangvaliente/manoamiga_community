
package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.entities.AsistenciaEntity;
import com.tecnara.manoamiga.doc.repositories.IAsistenciaRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc/admin/asistencia")
public class AdminAsistenciaController extends TecnaraBaseController{
    @Autowired
    private IAsistenciaRepository repoAsistencia;
    
    @GetMapping("/guardar")
    public String guardar(Long idAlumno){
        AsistenciaEntity nuevo=new AsistenciaEntity();
        nuevo.setIdAlumno(idAlumno);
        nuevo.setIdCurso(1L);
        nuevo.setFecha(new Date());
        nuevo.setHorario("12:00");
        repoAsistencia.save(nuevo);
        System.out.println("Esta llamando con " + idAlumno);
        return "ok";
    }
    
}
