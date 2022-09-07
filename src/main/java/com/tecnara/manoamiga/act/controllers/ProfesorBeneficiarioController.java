/*

 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.ComentarioParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.FaltaAsistenciaParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.FaltaDto;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ClaseFormativaEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/profesor/beneficiario")

public class ProfesorBeneficiarioController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private IFaltaAsistenciaRepository repoFaltaAsistencia;

    @Autowired
    private IComentarioRepository repoComentario;

    @GetMapping("/list")
    public String list(Model m) {
        return "act/profesor/beneficiario_list";
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new BeneficiarioEntity());
        } else {
            m.addAttribute("registro", repoBeneficiario.findById(id).get());
        }

        return "act/profesor/beneficiario_form";
    }

    @GetMapping("/list_data")  //LLAMADA A LISTADO GENERAL 
    @ResponseBody
    public List<BeneficiarioEntity> listData(String filtro) {
        return repoBeneficiario.profesorList(general.getIdValidado(), filtro);

    }

    @Autowired
    private ModelMapper mapper;

    @GetMapping("list_data_comentarios")  //LLAMADA A LISTADO DE COMENTARIOS
    @ResponseBody
    public List<ComentarioEntity> listDataComentarios(Long idBeneficiario) {
        return repoComentario.findByIdBeneficiarioOrderByFechaComentarioDescHoraComentarioDesc(idBeneficiario);
    }
    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IMonitorRepository repoMonitor;
    @Autowired
    private IProfesorRepository repoProfesor;

    public ComentarioParaBeneficiarioDto convertDto(ComentarioEntity origen) {

        ComentarioParaBeneficiarioDto nuevo = mapper.map(origen, ComentarioParaBeneficiarioDto.class);

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setPersona(general.concat("Monitor - ", monitor.getNombre() + " ", monitor.getApellido1()));

        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setPersona(general.concat("Profesor - ", profesor.getNombre() + " ", profesor.getApellido1()));
        } else if (origen.getIdTutor() != null) {

            TutorEntity tutor = repoTutor.findById(origen.getIdTutor()).orElse(new TutorEntity());
            nuevo.setPersona(general.concat("Tutor - ", tutor.getNombre() + " ", tutor.getApellido()));
        }
        return nuevo;
    }

    @GetMapping("/list_data_c")
    @ResponseBody
    public List<ComentarioParaBeneficiarioDto> listDataC(Long idBeneficiario) {
        return repoComentario.findByIdBeneficiarioOrderByFechaComentarioDescHoraComentarioDesc(idBeneficiario).stream()
                .map(x -> convertDto(x)) 
                .collect(Collectors.toList());

    }

    @GetMapping("list_data_asistencia")  //LLAMADA A LISTADO DE FALTAS DE ASISTENCIA
    @ResponseBody
    public List<FaltaAsistenciaEntity> listDataFaltaAsistencia(Long idBeneficiario) {
        return repoFaltaAsistencia.findByIdBeneficiarioOrderByFechaFaltaAsistenciaDesc(idBeneficiario); 
    }

    public FaltaAsistenciaParaBeneficiarioDto convertDto(FaltaAsistenciaEntity origen) {

        FaltaAsistenciaParaBeneficiarioDto nuevo = mapper.map(origen, FaltaAsistenciaParaBeneficiarioDto.class);

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setEvaluador(general.concat("Monitor - ", monitor.getNombre() + " ", monitor.getApellido1()));

        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setEvaluador(general.concat("Profesor - ", profesor.getNombre() + " ", profesor.getApellido1()));
        }

        return nuevo;
    }

    @GetMapping("/list_data_falta")
    @ResponseBody
    public List<FaltaAsistenciaParaBeneficiarioDto> listDataFalta(Long idBeneficiario) {
        return repoFaltaAsistencia.findByIdBeneficiarioOrderByFechaFaltaAsistenciaDesc(idBeneficiario).stream().map(x -> convertDto(x)).collect(Collectors.toList());
    }
   

}
