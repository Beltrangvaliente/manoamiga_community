package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;

import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import com.tecnara.manoamiga.emp.repositories.IProyectoRepository;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.repositories.IFormacionParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.IFormacionRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.tecnara.manoamiga.emp.repositories.IParticipanteRepository;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/emp/tecnico/proyecto")
public class TecnicoProyectoController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IProyectoRepository repoProyecto;

    @Autowired
    private IParticipanteRepository repoParticipante;

    @Autowired
    private IFicheroRepository repoFichero;

    @Autowired
    private IFormacionParticipanteRepository repoForPar;
    
    @Autowired
    private IFormacionRepository repoform;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ProyectoEntity());
        } else {
            m.addAttribute("registro", repoProyecto.findById(id).get());
        }
        return "emp/tecnico/proyecto_form";
        

    }

    @GetMapping("/formsinmenu")
    public String forsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ProyectoEntity());
        } else {
            m.addAttribute("registro", repoProyecto.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "emp/tecnico/proyecto_form";
    }

    @GetMapping("list")
    public String list(Model m) {
        return "emp/tecnico/proyecto_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "emp/tecnico/proyecto_help";
    }

    @PostMapping("guardar")
    public String guardar(ProyectoEntity registro, MultipartFile imagen, String img) {
        if (imagen != null && imagen.isEmpty() == false) {
            try {
                FicheroEntity nueva = new FicheroEntity();
                nueva.setFichero(imagen.getBytes());
                FicheroEntity guardado = repoFichero.save(nueva);
                Long id = guardado.getId();
                registro.setIdImagen(id);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:form";
            }
        }
        if (img.length() > 10) {
            img = img.replace("data:image/jpeg;base64,", "");
            img = img.replace("data:image/png;base64,", "");
            FicheroEntity nueva = new FicheroEntity();
            nueva.setFichero(convertToImage(img));
            FicheroEntity guardado = repoFichero.save(nueva);
            repoFichero.flush();
            Long id = guardado.getId();
            registro.setIdImagen(id);
        }

        repoProyecto.save(registro);
        return "redirect:list";
    }

    public byte[] convertToImage(String base64) {
        try {
            byte[] decodedString = Base64.getDecoder().decode(base64.getBytes("UTF-8"));
            return decodedString;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoProyecto.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta, Long idEmpresa) {

        return repoProyecto.tecnicoList(filtro, fechaDesde, fechaHasta, idEmpresa);
    }

    @GetMapping("/list_data_formacion")
    @ResponseBody
    public List<Map> listadoFormacion(Long idProyecto) {
        return repoProyecto.tecnicoFormProyectoFormaciones(idProyecto);
    }

    @GetMapping("/list_data_participante")
    @ResponseBody
    public List<Map> listadoParticipante(Long idProyecto, String cursoActivo) {
        return repoProyecto.tecnicoFormProyectoParticipantes(idProyecto, cursoActivo);

    }
    
    @GetMapping("list_data_documentacion")
    @ResponseBody
    public List<Map> listDataDocumentacion(Long idProyecto) {
        return repoProyecto.publicListDocumentacion(idProyecto);
    }
    
    

    @GetMapping("/list_data_inscribir")
    @ResponseBody
    public List<Map> listInscribir(Long idFormacion) {
        return repoForPar.listProyecto(idFormacion);

    }

    @GetMapping("/list_data_desinscribir")
    @ResponseBody
    public List<Map> listDesinscribir(Long idFormacion) {
        return repoForPar.listFormacion(idFormacion);

    }

    @GetMapping("/inscribir_participante")//sql
    @ResponseBody
    @Transactional
    public String btnInscribir(Long idFormacion, Long idParticipante) {
        repoForPar.btnInscribir(idFormacion, idParticipante);

        return "ok";
    }

    @GetMapping("/desinscribir_participante")
    public String btnDesinscribir(Long id) {
        repoForPar.deleteById(id);
        return "redirect:list";
    }
     @GetMapping("/iniciar_curso")//sql
    @ResponseBody
    @Transactional
    public String iniciar( Long id) {
        repoform.iniciarCurso(id);

        return "ok";
    }
    @GetMapping("/finalizar_curso")//sql
    @ResponseBody
    @Transactional
    public String finalizar( Long id) {
        repoform.finalizarCurso(id);

        return "ok";
    }
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoProyecto.findByNombreContaining(filtro)
                .
                stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        ProyectoEntity x = repoProyecto.findById(id).get();
        return x.getNombre();
    }

    @GetMapping(value= "/descargar",
            produces = MediaType.ALL_VALUE)
    public @ResponseBody
    byte[] descargar(Long id, HttpServletResponse response){
        FicheroEntity fichero = repoFichero.findById(id).get();
        byte[] datos = fichero.getFichero();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fichero.getNombre() + "\"");
        
        return datos;
    }
}
