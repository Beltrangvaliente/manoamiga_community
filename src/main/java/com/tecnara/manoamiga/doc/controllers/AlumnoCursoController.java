package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import com.tecnara.manoamiga.doc.entities.MatriculaEntity;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.doc.repositories.ICursoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoRepository;
import com.tecnara.manoamiga.doc.repositories.IMatriculaRepository;
import com.tecnara.manoamiga.doc.services.IDocumentoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/doc/alumno/curso")
public class AlumnoCursoController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;
    
    @Autowired
    private ICursoRepository repoCurso;
    
    @Autowired
    private IMatriculaRepository repoMatricula;
    
    @Autowired
    private IDocumentoSubidoRepository repoDocuSubi;
    
    @Autowired
    private IDocumentoService docuService;
    

    @GetMapping("/list")
    public String list() {
        return "doc/alumno/curso_list"; //HTML que queremos mostrar
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData() {
        return repoCurso.alumnoList(general.getIdValidado());
    }

    @GetMapping("/show")
    public String show(Model m, Long id) {
         m.addAttribute("registro", repoCurso.findById(id).get());
        return "doc/alumno/curso_show";
    }

    @GetMapping("/asistencia")
    public String asistencia(Model m) {
        m.addAttribute("idAlumno", general.getIdValidado());
        return "doc/alumno/curso_asistencia"; //HTML que queremos mostrar
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        if (repoMatricula.findByIdAndIdAlumnoAndEstado(id, general.getIdValidado(), "Preinscripcion").isEmpty()) {
            return "redirect:list?error=No se puede borrar";
        }
        repoMatricula.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/preinscripcion")
    public String preinscripcion(Long idCurso, Model model) { 
        model.addAttribute("registro",repoCurso.findById(idCurso).get());
        List<MatriculaEntity> res=repoMatricula.findByIdCursoAndIdAlumno(idCurso, general.getIdValidado());
        if (res.size()==0){
            model.addAttribute("inscrito", "no");
        }else{
            model.addAttribute("inscrito", "si");
        }
        return "doc/alumno/curso_preinscripcion_form"; //HTML que queremos mostrar
    }

    @GetMapping("/list_data_documentos_requeridos")
    @ResponseBody
    public List<Map> listDataDocumentosRequeridos(Long idCurso) {
        return repoCurso.alumnoShowDocumentoRequeridos(idCurso);

    }

    @GetMapping("/preinscripcion_registrar")
    @Transactional
    public String registro(Long idCurso) {
        repoCurso.registro(idCurso, general.getIdValidado());
        return "redirect:/doc/alumno/curso/list";
    }
    
    @PostMapping("/subir_fichero")
    public String subirFicheros(MultipartFile fichero1, MultipartFile fichero2, MultipartFile fichero3){
        
        return "redirect:list";
    }
    

    
     @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        m.addAttribute("sinmenu", true);
        return "doc/alumno/subir_documento_form"; //Esto es el html
    }
    
    
}
