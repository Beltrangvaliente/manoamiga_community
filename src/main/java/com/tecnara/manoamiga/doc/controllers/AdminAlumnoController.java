package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.AlumnoDto;
import com.tecnara.manoamiga.doc.dto.AlumnosTitulosDto;
import com.tecnara.manoamiga.doc.dto.CitaDeAlumnoDto;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.dto.DocumentoAlumnoDto;
import com.tecnara.manoamiga.doc.dto.MatriculaCursoDelAlumnoDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.AlumnoNecesidadFormacionEntity;
import com.tecnara.manoamiga.doc.entities.CentroAtencionEntity;
import com.tecnara.manoamiga.doc.entities.CitaEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoRequeridoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import com.tecnara.manoamiga.doc.entities.MatriculaEntity;
import com.tecnara.manoamiga.doc.entities.TituloEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoNecesidadFormacionRepository;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.ICentroAtencionRepository;
import com.tecnara.manoamiga.doc.repositories.ICitaRepository;
import com.tecnara.manoamiga.doc.repositories.ICursoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoRequeridoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoRepository;
import com.tecnara.manoamiga.doc.repositories.IMatriculaRepository;
import com.tecnara.manoamiga.doc.repositories.ITituloRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("doc_admin_alumno_controller")
@RequestMapping("/doc/admin/alumno")
public class AdminAlumnoController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new AlumnoEntity());
        } else {
            m.addAttribute("registro", repoAlumno.findById(id).get());
        }
        return "doc/admin/alumno_form";
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new AlumnoEntity());
        } else {
            m.addAttribute("registro", repoAlumno.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/alumno_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/alumno_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/alumno_help";
    }

    @PostMapping("/guardar")
    public String guardar(AlumnoEntity registro) {
        repoAlumno.save(registro);
        return "redirect:list";
    }

    @Autowired
    private IUsuarioRepository repoUsuario;
    
    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        AlumnoEntity ent=repoAlumno.getById(id);
        repoAlumno.deleteById(id);
        if (ent.getIdUsuario()!=null && repoUsuario.existsById(ent.getIdUsuario())){
            repoUsuario.deleteById(ent.getIdUsuario());
        }         
        return "redirect:list";
    }

    public AlumnoDto convertirDto(AlumnoEntity origen) {
        AlumnoDto nuevo = new AlumnoDto();
        nuevo.setId(origen.getId());
        nuevo.setNombre(origen.getNombre());
        nuevo.setApellido1(origen.getApellido1());
        nuevo.setApellido2(origen.getApellido2());
        nuevo.setTelefono(origen.getTelefono());
        return nuevo;
    }
    @Autowired
    private IGeneral general;

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {

        return repoAlumno.adminList(filtro);
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

    /*@Autowired
    private IAlumnoRepository repoAlumno;*/

    @Autowired
    private IMatriculaRepository repoMatricula;

    @Autowired
    private ICursoRepository repoCurso;

    @Autowired
    private ICitaRepository repoCitas;

    @Autowired
    private ICitaRepository repCitas;

    @Autowired
    private ICentroAtencionRepository repoCentroAtencion;
    @Autowired
    private IAlumnoNecesidadFormacionRepository repoNecesidad;

    public CitaDeAlumnoDto convertirDto(CitaEntity origen) {
        CitaDeAlumnoDto nuevo = new CitaDeAlumnoDto();
        //Datos de la cita
        nuevo.setId(origen.getId());
        nuevo.setHoraDeCita(origen.getHoraDeCita());
        nuevo.setFechaDeCita(utils.formatearFecha(origen.getFechaDeCita()));
        nuevo.setEstado(origen.getEstado());

        //Buscar en la base de datos el nombre del centro de atencion
        CentroAtencionEntity origen2 = repoCentroAtencion.findById(origen.getIdCentroDeAtencion()).orElse(new CentroAtencionEntity());
        nuevo.setNombreCentroDeAtencion(origen2.getNombre());
        return nuevo;
    }

    @GetMapping("list_data_cita")
    @ResponseBody
    public List<Map> listadoCita(Long idAlumno) {
        return repoAlumno.adminFormCitas(idAlumno);
    }

    @Autowired
    private ITituloRepository repoTitulo;

    public AlumnosTitulosDto convertirDto(TituloEntity origen) {
        AlumnosTitulosDto nuevo = new AlumnosTitulosDto();
        nuevo.setId(origen.getId());
        nuevo.setNivel(origen.getNivel());
        nuevo.setDuracion(origen.getDuracion());
        nuevo.setHomologacion(origen.getHomologacion());
        nuevo.setPaisExpedicion(origen.getPaisExpedicion());

        TituloEntity origen2 = repoTitulo.findById(origen.getIdAlumno()).orElse(new TituloEntity());
        nuevo.setTitulacion(origen2.getTitulacion());

        return nuevo;

    }

    @GetMapping("/list_data_titulo")
    @ResponseBody
    public List<Map> listadoTitulo(Long idAlumno) {
        return repoAlumno.adminFormTitulos(idAlumno);
    }

    @Autowired
    private IDocumentoSubidoRepository repoDocumentosSubido;

    @Autowired
    private IDocumentoRequeridoRepository repoDocumentosRequerido;
    @Autowired
    private IAlumnoNecesidadFormacionRepository repoAnf;

    //@Autowired
    //private IMatriculaRepository repoMatricula;
    public MatriculaCursoDelAlumnoDto convertirDto(MatriculaEntity origen) {

        MatriculaCursoDelAlumnoDto nuevo = new MatriculaCursoDelAlumnoDto();
        nuevo.setId(origen.getId());
        nuevo.setEstado(origen.getEstado());
        nuevo.setFechaMatricula(origen.getFechaMatricula());
        //nuevo.setReferencia(origen.get());

        //CursoEntity origen2=repoMatricula.findById(origen.getIdCurso()).orElse(new CursoEntity());
        //nuevo.setTipoDeDocumento(origen2.getTipoDeDocumento());
        //nuevo.setObligatorio( origen2.getObligatorio());
        return nuevo;
    }

    @GetMapping("/list_data_matricula")
    @ResponseBody
    public List<Map> listDataMatricula(Long idAlumno) {

        return repoAlumno.adminFormMatriculas(idAlumno);
    }

    @GetMapping("/list_data_documentos")
    @ResponseBody
    public List<Map> listDataDocumentos(Long idAlumno) {
        return repoAlumno.adminFormDocumentos(idAlumno);

    }
    
    @GetMapping("/list_data_documento_solicitado")
    @ResponseBody
    public List<Map> documentosSolicitadosForm(Long idAlumno) {
        return repoAlumno.adminFormDocumentosSolicitados (idAlumno);
        
    }
    
     @GetMapping("/list_data_alumno_formacion")
    @ResponseBody
    public List<Map> listDataAlumnoFormacion(Long idAlumno){
    return repoAnf.adminFormNecesidadFormacion(idAlumno);
    }
  
}
