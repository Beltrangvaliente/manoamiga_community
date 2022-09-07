package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.dto.TrabajadorDto;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;

import com.tecnara.manoamiga.expo.repositories.ITrabajadorRepository;
import java.util.List;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import com.tecnara.manoamiga.expo.repositories.ICoordinadorRepository;
import com.tecnara.manoamiga.expo.repositories.ITrabajadorRepository;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expo/coordinador/trabajador")
public class CoordinadorTrabajadorController extends TecnaraBaseController {

    @Autowired
    private ITrabajadorRepository repoTrabajador;
    
    @Autowired
    private ICoordinadorRepository repoCoordinador;
    
    @Autowired
    private IEmailService email;
    
 
    @GetMapping("/list")
    public String list(Model m) {
        return "expo/coordinador/trabajador_list";

    }

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IAreaRepository repoArea;

    @Autowired
    private IUsuarioRepository repoUsuario;

    public TrabajadorDto convertDto(TrabajadorEntity origen) {
        TrabajadorDto nuevo = mapper.map(origen, TrabajadorDto.class);

        if (origen.getIdArea()!=null){
            AreaEntity area = repoArea.findById(origen.getIdArea()).orElse(new AreaEntity());
            nuevo.setArea(area.getArea());
        }
        if(origen.getIdUsuario()!=null){
            UsuarioEntity Usuario = repoUsuario.findById(origen.getIdUsuario()).orElse(new UsuarioEntity());
            nuevo.setUsuario(Usuario.getUsuario());
        }
        return nuevo;
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<TrabajadorDto> listData() {
        return repoTrabajador.findAll().stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new TrabajadorEntity());
        } else {
            m.addAttribute("registro", repoTrabajador.findById(id).get());
        }
        return "expo/coordinador/trabajador_form";
    }

    @PostMapping("/guardar")
    public String guardar(TrabajadorEntity registro) {
        repoTrabajador.save(registro);
        return "redirect:list";

    }

    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        TrabajadorEntity ent=repoTrabajador.getById(id);
        repoTrabajador.deleteById(id);
        if (ent.getIdUsuario()!=null && repoUsuario.existsById(ent.getIdUsuario())){
            try{
                repoUsuario.deleteById(ent.getIdUsuario());
            }catch(Exception e){
            }
        }           
        return "redirect:list";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) { 
        return repoTrabajador.findByNombreTrabajadorContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombreTrabajador()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        TrabajadorEntity x = repoTrabajador.findById(id).orElse(new TrabajadorEntity());
        return x.getNombreTrabajador(); 
    }
    
    @GetMapping("/buscador_trabajador_id")
    @ResponseBody
    public String buscadorTrabajador (Long id){
    UsuarioEntity x=repoUsuario.findById(id).orElse(new UsuarioEntity());
    return x.getUsuario();
    
    }
    
    @GetMapping("/buscador_trabajador")
    @ResponseBody
    public List<ClaveValorDto>buscadorTrabajador(String filtro, Long idUsuario){
    return repoTrabajador.coordinadorTrabajadorFromBuscador(filtro, idUsuario)
            .stream()
            .map(x-> new ClaveValorDto(Long.parseLong(x.get("id")+""),x.get("usuario")+""))
            .collect(Collectors.toList());
     
    }
    

}
