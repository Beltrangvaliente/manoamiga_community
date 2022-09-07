package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.AlumnosUsuarioDto;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.AsistenciaEntity;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.entities.NecesidadFormacionEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.IAsistenciaRepository;
import com.tecnara.manoamiga.doc.repositories.ICursoRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/profesor/alumno")
public class ProfesorAlumnoController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IGeneral general;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @Autowired
    private IAsistenciaRepository repoAsistencia;

    @GetMapping("/list")
    public String list() {
        return "doc/profesor/alumno_list"; //HTML que queremos mostrar
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new AlumnoEntity());
        } else {
            m.addAttribute("registro", repoAlumno.profesorForm(id, general.getIdValidado()).get());
        }
        return "doc/profesor/alumno_form"; //HTML que queremos mostrar
    }

    @GetMapping("/show")
    public String show(Model m, Long id) {

        m.addAttribute("registro", repoAlumno.profesorShow(id, general.getIdValidado()).get());
        return "doc/profesor/alumno_show";
    }

    @GetMapping("/guardar")
    public String guardar(Long idAlumno, Long idCurso) {
        //Si el alumno tiene para ese curso y esa fecha. Si ya está. No hacer nada, return; 
        if (repoAsistencia.findByIdAlumnoAndIdCursoAndFecha(idAlumno, idCurso, general.getFechaActual()).isPresent()) {
            return "redirect:list";
        }
        AsistenciaEntity nuevo = new AsistenciaEntity();
        nuevo.setIdAlumno(idAlumno);
        nuevo.setIdCurso(idCurso);
        nuevo.setFecha(general.getFechaActual());
        nuevo.setHorario(general.getHoraActual());
        repoAsistencia.save(nuevo);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long idAlumno, Long idCurso) {
        repoAsistencia.deleteByIdAlumnoAndIdCursoAndFecha(idAlumno, idCurso, general.getFechaActual());
        return "redirect:list";
    }

    @GetMapping("/pasar_lista")
    public String pasar_lista(Model m) {
        m.addAttribute("idCursoInicial", repoAlumno.profesorPasarListaCursoInicial(general.getIdValidado()));
        //m.addAttribute("idProfesor", 1L);
        return "doc/profesor/alumno_pasar_lista"; //HTML que queremos mostrar
    }

//    @GetMapping("/pasar_lista2")
//    public String pasar_lista2() {
//        return "doc/profesor/alumno_pasar_lista2"; //HTML que queremos mostrar
//    }
    @Autowired
    ICursoRepository repoCurso;

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        return repoAlumno.profesorList(filtro, general.getIdValidado());
    }

    @GetMapping("/list_data_pasar_lista")
    @ResponseBody
    public List<Map> listDataPasarLista(Long idCurso) {
        return repoAlumno.profesorPasarLista(idCurso, general.getIdValidado());
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoAlumno.findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(filtro, filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre() + " " + x.getApellido1() + " " + x.getApellido2()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        AlumnoEntity x = utils.findById(repoAlumno, id, AlumnoEntity.class);
        return x.getNombre() + " " + x.getApellido1() + " " + x.getApellido2();
    }

    
    
}
