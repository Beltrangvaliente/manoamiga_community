package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.AlumnoDelCursoDto;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.dto.CursoDto;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/curso")
public class AdminCursoController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private ICursoRepository repoCurso; //dao - Data Access Object (=repository)

    @Autowired
    private ICentroRepository repoCentro;

    @Autowired
    private IMatriculaRepository repoMatricula;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @Autowired
    private IGeneral general;
    
    @Autowired
    private INotificacionRepository repoNotificacion;

    //public CursoDto convertirDto(CursoEntity origen){
    //CursoDto nuevo=new CursoDto();
    //nuevo.setId(x.);
    //}
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new CursoEntity());
        } else {
            m.addAttribute("registro", repoCurso.findById(id).get());
        }
        return "doc/admin/curso_form"; //Esto es el html
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/curso_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/curso_help";
    }

    @PostMapping("/guardar")
    public String guardar(CursoEntity registro) {
        repoCurso.save(registro);
        return "redirect:list";
    }

    public CursoDto convertDto(CursoEntity x) {
        CursoDto nuevo = new CursoDto();
        nuevo.setId(x.getId());
        nuevo.setNombreCurso(x.getNombreCurso());
        nuevo.setModalidad(x.getModalidad());
        nuevo.setNumeroHoras(x.getNumeroHoras());
        nuevo.setNumeroAlumnos(x.getNumeroAlumnos());
        nuevo.setHorario(x.getHorario());
        nuevo.setPrecio(x.getPrecio());
        nuevo.setFechaLimiteInscripcion(utils.formatearFecha(x.getFechaLimiteInscripcion()));
        nuevo.setFechaInicio(utils.formatearFecha(x.getFechaInicio()));
        nuevo.setFechaFin(utils.formatearFecha(x.getFechaFin()));

        CentroEntity y = utils.findById(repoCentro, x.getIdCentro(), CentroEntity.class);
        nuevo.setNombreCentro(y.getNombre());

        return nuevo;
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listDataCursos(String filtro) {
        return repoCurso.cursoList(filtro);
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoCurso.findByNombreCursoContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombreCurso()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        CursoEntity x = utils.findById(repoCurso, id, CursoEntity.class);
        return x.getNombreCurso();
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new CursoEntity());
        } else {
            m.addAttribute("registro", repoCurso.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/curso_form"; //Esto es el html
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoCurso.deleteById(id);
        return "redirect:list";

    }
    

    
    
    @GetMapping("/list_data_curso")
    @ResponseBody
    public List listDataCurso(Long idAlumno
    ) {
        List<CursoEntity> res = repoCurso.findByIdCentro(idAlumno);
        return res;
    }

    public AlumnoDelCursoDto convertirDto(MatriculaEntity origen) {
        AlumnoDelCursoDto nuevo = new AlumnoDelCursoDto();
        nuevo.setId(origen.getId());
        nuevo.setEstadoMatricula(origen.getEstado());

        AlumnoEntity origen2 = repoAlumno.findById(origen.getIdAlumno()).orElse(new AlumnoEntity());
        nuevo.setNombre(origen2.getNombre());
        nuevo.setApellido1(origen2.getApellido1());
        nuevo.setApellido2(origen2.getApellido2());
        nuevo.setTelefono(origen2.getTelefono());
        nuevo.setFechaNacimiento(utils.formatearFecha(origen2.getFechaNacimiento()));

        return nuevo;
    }

    @GetMapping("/list_data_usuarios_apuntados")
    @ResponseBody
    public List<Map> listDataUsuariosApuntados(Long idCurso) {
        return repoCurso.adminCursoFormMatriculas(idCurso);

    }

    @GetMapping("/list_data_participacion_profesor_curso")
    @ResponseBody
    public List<Map> listDataParticipacionProfesorCurso() {
        return repoCurso.adminCursoFormProfesores(general.getIdValidado());
    }

    @GetMapping("/list_data_documentos")
    @ResponseBody
    public List<Map> listDataDocumentos(Long idCurso) {

        return repoCurso.adminFormDocumentoRequeridos(idCurso);
    }
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
        
        return "redirect:list"; 
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
        
        return "redirect:list"; 
    }
    
    
    
    @GetMapping("/rechazar")
    public String rechazarEvento(Long id) {
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
        
        
        return "redirect:list";
    
    }    

}
