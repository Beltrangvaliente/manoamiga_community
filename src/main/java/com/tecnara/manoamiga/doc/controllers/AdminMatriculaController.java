package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.MatriculaDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.CentroEntity;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.entities.MatriculaEntity;
import com.tecnara.manoamiga.doc.entities.NotificacionEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.ICentroRepository;
import com.tecnara.manoamiga.doc.repositories.ICursoRepository; 
import com.tecnara.manoamiga.doc.repositories.IMatriculaRepository;
import com.tecnara.manoamiga.doc.repositories.INotificacionRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/matricula")
public class AdminMatriculaController extends TecnaraBaseController{

    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private IMatriculaRepository repoMatricula; 

    @Autowired
    private IAlumnoRepository repoAlumno;
    
    @Autowired
    private ICursoRepository repoCurso;

    @Autowired
    private ICentroRepository repoCentro;
    
    @Autowired
    private INotificacionRepository repoNotificacion;

    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new MatriculaEntity());
        } else {
            m.addAttribute("registro", repoMatricula.findById(id).get());
        }
        return "doc/admin/matricula_form"; //Es el html
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new MatriculaEntity());
        } else {
            m.addAttribute("registro", repoMatricula.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/matricula_form"; //Es el html
    }

    @GetMapping("/borrar") 
    public String borrar(Long id) {
        repoMatricula.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/matricula_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/matricula_help";
    }

    @PostMapping("/guardar")
    public String guardar(MatriculaEntity registro) { 
        if ( repoMatricula.findByIdCursoAndIdAlumnoAndIdNot(registro.getIdCurso(), registro.getIdAlumno(), registro.getId()==null?-1L:registro.getId() ).isPresent()){
            return "redirect:form?error=El alumno ya está matriculado";
        }
        repoMatricula.save(registro);
        return "redirect:list";
    }

    @GetMapping("/list_data_notificacion")
    @ResponseBody
    public List<Map> listadoNotificaciones(String filtro, 
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        
        return repoMatricula.adminPlazoFinalizado(filtro, fechaDesde, fechaHasta);
    }

    @GetMapping("/solicitudes")
    public String solicitudes(Model m) {
        return "doc/admin/matricula_solicitudes";
    }
    
    
    private MatriculaDto convertDto(MatriculaEntity origen){
        MatriculaDto nuevo=new MatriculaDto();
        nuevo.setId(origen.getId());
        nuevo.setEstado(origen.estado);

        
        if (origen.getIdAlumno()!=null){
            AlumnoEntity alumno=utils.findById(repoAlumno, origen.getIdAlumno(), AlumnoEntity.class);
            nuevo.setNombreAlumno( alumno.getNombre() + " " + alumno.getApellido1() + " " + alumno.getApellido2() );
        }
        
        if (origen.getIdCurso()!=null){
            CursoEntity curso=utils.findById(repoCurso, origen.getIdCurso(), CursoEntity.class);
            nuevo.setNombreCurso(curso.getNombreCurso() );
            nuevo.setReferenciaCurso(curso.getReferencia());

            if (curso.getIdCentro()!=null){
                CentroEntity centro=repoCentro.findById(curso.getIdCentro()).orElse(new CentroEntity());
                nuevo.setNombreCentro(centro.getNombre());
            }

        }


        nuevo.setFechaInscripcion( utils.formatearFecha( origen.getFechaMatricula() ) );
        nuevo.setFechaMatricula(utils.formatearFecha( origen.getFechaMatricula() ) );
        
        return nuevo;
    }
    
    @GetMapping("/solicitudes_data")
    @ResponseBody
    public List<MatriculaDto> solicitudesData() {
        List<MatriculaEntity> res= repoMatricula.findByEstado("Preinscripción");
        List<MatriculaDto> dtos=res.stream().map(x->convertDto(x)).collect(Collectors.toList());
        return dtos;

    }
    
    @Autowired
    private IGeneral general;
    
    @GetMapping("/confirmar")
    public String aceptar(Long id) {
        MatriculaEntity obj=repoMatricula.findById(id).get(); 
        obj.setEstado("Confirmada");
        repoMatricula.save(obj);
        
        NotificacionEntity notNueva=new NotificacionEntity();
        notNueva.setIdAlumno(obj.getIdAlumno());
        notNueva.setFechaNotificacion(general.getFechaActual()); 
        notNueva.setHoraNotificacion(general.getHoraActual());
        notNueva.setUrlLink("/doc/alumno/curso/form?id="+obj.getIdCurso());
        notNueva.setTextoNotificacion("Aceptada matrícula envie documentación");
        notNueva.setLeido("No");
        repoNotificacion.save(notNueva);
        
        return "redirect:solicitudes"; 
    }
    
    @GetMapping("/solicitar_documentacion")
    public String solicitarDocumentacion(Long id) {
        MatriculaEntity obj=repoMatricula.findById(id).get(); 
        obj.setEstado("Pendiente de documentacion");
        repoMatricula.save(obj);
        
        NotificacionEntity notNueva=new NotificacionEntity();
        notNueva.setIdAlumno(obj.getIdAlumno());
        notNueva.setFechaNotificacion(general.getFechaActual()); 
        notNueva.setHoraNotificacion(general.getHoraActual());
        notNueva.setUrlLink("/doc/alumno/curso/form?id="+obj.getIdCurso());
        notNueva.setTextoNotificacion("Aceptada matrícula envie documentación");
        notNueva.setLeido("No");
        repoNotificacion.save(notNueva);
        
        return "redirect:solicitudes"; 
    }
    
    
    
    @GetMapping("/rechazar")
    public String rechazarMatricula(Long id) {
        MatriculaEntity obj=repoMatricula.findById(id).get();
        obj.setEstado("Rechazada");
        repoMatricula.save(obj);
        
           
        NotificacionEntity notNueva=new NotificacionEntity();
        notNueva.setIdAlumno(obj.getIdAlumno());
        notNueva.setFechaNotificacion(general.getFechaActual()); 
        notNueva.setHoraNotificacion(general.getHoraActual());
        notNueva.setUrlLink("/doc/alumno/curso/form?id="+obj.getIdCurso());
        notNueva.setTextoNotificacion("Rechazada matricula ");
        notNueva.setLeido("No");
        repoNotificacion.save(notNueva);
        
        
        return "redirect:solicitudes";
    
    }    
}
