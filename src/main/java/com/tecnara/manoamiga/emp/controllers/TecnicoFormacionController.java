
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.EmpresaEntity;
import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import com.tecnara.manoamiga.emp.repositories.IDocumentoFormacionRepository;
import com.tecnara.manoamiga.emp.repositories.IFormacionRepository;
 
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emp/tecnico/formacion")
public class TecnicoFormacionController extends TecnaraBaseController {
    
    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private IFormacionRepository repoFormacion;
    
    @Autowired
    private IDocumentoFormacionRepository repoDocuform;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new FormacionEntity());
        } else {
            m.addAttribute("registro", repoFormacion.findById(id).get());
        }
        return "emp/tecnico/formacion_form";

    }
    
    @PostMapping("guardar")
    public String guardar(FormacionEntity registro) {
        repoFormacion.save(registro);
        return "redirect:list";
    }
   
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoFormacion.deleteById(id);
        return "redirect:list";
    }
    

    @GetMapping("/list")
    public String list(Model m){
    return "emp/tecnico/formacion_list";
    }
    
   @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin, Long idEmpresa) {
        return repoFormacion.tecnicoList (filtro, fechaInicio, fechaFin, idEmpresa);  
        
    }
         
    
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoFormacion.findByNombreContaining(filtro)
                    .stream()
                    .map(x-> new ClaveValorDto(x.getId(),x.getNombre()))
                    .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id){
        FormacionEntity x= repoFormacion.findById(id).get();
        return x.getNombre();
    }
    @GetMapping("/help")
    public String help(Model m) {
        return "emp/tecnico/empresa_help";
    }
    
    @GetMapping("/list_data_participante")
    @ResponseBody
    public List<Map> listaDataParticipante(Long idFormacion){
        return repoFormacion.tecnicoFormParticipante(idFormacion);
    }  
    
   @GetMapping("/list_data_documento_formacion")
   @ResponseBody
   public List<Map> listadoDocumentosFormaciones(Long idFormacion){
   return repoFormacion.tecnicoFormDocumentosFormaciones(idFormacion);
        
    }
    @Autowired
    private IFicheroRepository repoFichero;

    @GetMapping(value = "/descargar",
            produces = MediaType.ALL_VALUE)
    public @ResponseBody
    byte[] descargar(Long id, HttpServletResponse response) {
        FicheroEntity fichero = repoFichero.findById(id).get();
        byte[] datos = fichero.getFichero();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fichero.getNombre() + "\""); ;

        return datos;
    }

   
    
}
