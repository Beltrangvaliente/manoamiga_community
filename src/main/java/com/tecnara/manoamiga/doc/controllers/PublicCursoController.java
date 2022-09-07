
package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.repositories.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc/public/curso")
public class PublicCursoController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private ICursoRepository repoCurso;

    @GetMapping("/list")
    public String list(Model m, String filtro, Long mes, String modalidad, String horario) {
        if (filtro == null) {
            filtro = "";
        }
        if (modalidad == null) {
            modalidad = "";
        }
        if (horario == null) {
            horario = "";
        }
        m.addAttribute("lista", repoCurso.publicList(filtro, mes, modalidad, horario));
        m.addAttribute("filtro", filtro);
        m.addAttribute("mes", mes);
        m.addAttribute("modalidad", modalidad);
        m.addAttribute("horario", horario);
        return "doc/public/curso_list"; //HTML que queremos mostrar
    }

           

    @GetMapping("/show")
    public String show(Model m, Long id) {
        m.addAttribute("registro", repoCurso.findById(id).get());
        return "doc/public/curso_show"; //HTML que queremos mostrar
    }

}
