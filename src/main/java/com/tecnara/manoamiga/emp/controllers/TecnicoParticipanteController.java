package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.ParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.repositories.IContratoRepository;
import com.tecnara.manoamiga.emp.repositories.IParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.IProyectoParticipanteRepository;
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
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import com.tecnara.manoamiga.emp.entities.DocumentoParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.ExperienciaEntity;

import com.tecnara.manoamiga.emp.repositories.IDocumentoParticipanteRepository;
import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.entities.FormacionPreviaEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoParticipanteEntity;
import com.tecnara.manoamiga.emp.repositories.IExperienciaRepository;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import com.tecnara.manoamiga.emp.repositories.IFormacionParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.IFormacionPreviaRepository;
import com.tecnara.manoamiga.emp.repositories.IInteresRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Controller
@RequestMapping("/emp/tecnico/participante")

public class TecnicoParticipanteController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IParticipanteRepository repoParticipante;

    @Autowired
    private IProyectoParticipanteRepository repoProyectoParticipante;

    @Autowired
    private IContratoRepository repoContrato;

    @Autowired
    private IGeneral general;

    @Autowired
    private IFicheroRepository repoFichero;

    @Autowired
    private IExperienciaRepository repoExperiencia;

    @Autowired
    private IFormacionPreviaRepository repoFormacionPrevia;

    @Autowired
    private IFormacionParticipanteRepository repoFormacionParticipante;

    @Autowired
    private IInteresRepository repoInteres;

    @Autowired
    private IDocumentoParticipanteRepository repoDocumentoParticipante;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ParticipanteEntity());
        } else {
            m.addAttribute("registro", repoParticipante.findById(id).get());
        }
        return "emp/tecnico/participante_form";

    }

    @GetMapping("/list")
    public String list(Model m) {
        return "emp/tecnico/participante_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDardeDesde, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDardeHasta, String contactado) {
        return repoParticipante.tecnicoList(filtro, fechaDardeDesde, fechaDardeHasta, contactado);
    }

    @GetMapping("/list_data_contratos") //porque no recibe muchos parámetros
    @ResponseBody //devuelve json
    public List<Map> listDataContratos(Long idParticipante) {
        return repoParticipante.tecnicoFormContratos(idParticipante);
    }
    //Estan los data del controlador

    @GetMapping("/list_data_experiencia")
    @ResponseBody
    public List<Map> listExperiencias(Long idParticipante) {
        return repoParticipante.tecnicoFormExperiencias(idParticipante);
    }

    @GetMapping("/list_data_formacion_previa")
    @ResponseBody
    public List<Map> listDataFormacionesPrevias(Long idParticipante) {
        return repoParticipante.tecnicoFormFormacionesPrevias(idParticipante);
    }

    @GetMapping("/list_data_formacion_participante")
    @ResponseBody
    public List<Map> listFormacionesParticipantes(Long idParticipante) {
        return repoParticipante.tecnicoFormFormacionesParticipantes(idParticipante);
    }

    @GetMapping("/list_data_intereses")
    @ResponseBody
    public List<Map> listIntereses(Long idParticipante) {
        return repoParticipante.tecnicoFormIntereses(idParticipante);
    }

    @GetMapping("/list_data_documentos_participante")
    @ResponseBody
    public List<DocumentoParticipanteEntity> listDataDocumentoParticipante(Long idParticipante) {
        return repoDocumentoParticipante.findByIdParticipante(idParticipante);
    }

    @PostMapping("/guardar")
    public String guardar(ParticipanteEntity registro) {
        repoParticipante.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoParticipante.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/aceptar_experiencia")
    @ResponseBody
    public String listAceptarExperiencias(Long id) {

        repoParticipante.aceptarExperiencias(id);

        return "ok";
    }

    @GetMapping("/rechazar_experiencia")
    @ResponseBody
    public String listRechazarExperiencias(Long id) {

        repoParticipante.rechazarExperiencias(id);
        return "ok";
    }

    @GetMapping("/aceptar_formacion_previa")
    @ResponseBody
    public String listAceptarFormacionesPrevias(Long id) {

        repoParticipante.aceptarFormacionesPrevias(id);
        return "ok";

    }

    @GetMapping("/rechazar_formacion_previa")
    @ResponseBody
    public String listRechazarFormacionesPrevias(Long id) {

        repoParticipante.rechazarFormacionesPrevias(id);

        return "ok";

    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoParticipante.findByNombreContainingOrApellido1ContainingOrApellido2Containing(filtro, filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), general.concat(x.getNombre(), x.getApellido1(), x.getApellido2())))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        ParticipanteEntity x = utils.findById(repoParticipante, id, ParticipanteEntity.class);
        return general.concat(x.getNombre(), x.getApellido1(), x.getApellido2());
    }

}
