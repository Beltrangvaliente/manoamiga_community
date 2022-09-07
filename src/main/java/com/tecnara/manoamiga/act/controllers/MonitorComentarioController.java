/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.ComentarioDto;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.LeidoComentarioMonitorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.ILeidoComentarioMonitorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor/comentario")
public class MonitorComentarioController extends TecnaraBaseController{
    
    @Autowired
    private IGeneral general;

    @Autowired
    private IComentarioRepository repoComentario;//dn

    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/comentario_list";
    }
    
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;


   
    
    @Autowired
    private ILeidoComentarioMonitorRepository repoLeidoComentarioMonitor;
    

    public ComentarioDto convertDto(ComentarioEntity origen) {
        ComentarioDto nuevo = mapper.map(origen, ComentarioDto.class);
        if (origen.getIdBeneficiario()!=null){
                BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
                nuevo.setNombreCompletoBeneficiario(general.concat("Beneficiario-", beneficiario.getNombre(), beneficiario.getApellido1(),beneficiario.getApellido2()));
        }
        if (origen.getIdTutor()!=null){
                TutorEntity tutor = repoTutor.findById(origen.getIdTutor()).orElse(new TutorEntity());
                nuevo.setNombreCompletoTutor(general.concat("Tutor-", tutor.getNombre(), tutor.getApellido()));
        }
        if (repoLeidoComentarioMonitor.findByIdComentarioAndIdMonitor(origen.getId(), general.getIdValidado() ).isEmpty() ){
            nuevo.setLeido("No");
        }else{
            nuevo.setLeido("Si");
        }
        
        return nuevo;

    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<ComentarioDto> listData() {
        return repoComentario.findAll(PageRequest.of(0, 1001)).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());

    }

     @GetMapping("/list_data_leido")
     @ResponseBody
     public Optional<LeidoComentarioMonitorEntity> listDataLeido(Long idComentario) {
        return repoLeidoComentarioMonitor.findByIdComentarioAndIdMonitor(general.getIdValidado(),idComentario);
                
    }
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new ComentarioEntity());    
        }else{
            m.addAttribute("registro", repoComentario.findById(id).get());    
        }
        
        return "act/monitor/comentario_form";
    }

    @GetMapping("/show")
    public String show(Model m, Long id) {
        ComentarioEntity comentario = repoComentario.findById(id).orElse(new ComentarioEntity());
        m.addAttribute("comentario", comentario);
        if (comentario.getIdBeneficiario()!=null){
            m.addAttribute("beneficiario", repoBeneficiario.findById(comentario.getIdBeneficiario()).orElse(new BeneficiarioEntity()));    
        }else{
            m.addAttribute("beneficiario", new BeneficiarioEntity());
        }
        if (comentario.getIdTutor()!=null){
            m.addAttribute("tutor", repoTutor.findById(comentario.getIdTutor()).orElse(new TutorEntity()));
        }else{
            m.addAttribute("tutor", new TutorEntity());
        }
      
        return "act/monitor/comentario_show";
    }

    @PostMapping("/guardar")
    public String guardar(ComentarioEntity registro) {
        registro.setIdMonitor(general.getIdValidado());
        repoComentario.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoComentario.deleteById(id);
        return "redirect:list";

    }
    
    @GetMapping("/marcar_leido")
    public String marcar(Long idComentario){
        LeidoComentarioMonitorEntity nuevo=new LeidoComentarioMonitorEntity();
        nuevo.setIdComentario(idComentario);
        nuevo.setIdMonitor(general.getIdValidado());
        repoLeidoComentarioMonitor.save(nuevo);
        return "redirect:list";
    }
    
}
