package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.dto.BeneficiarioDto;
import com.tecnara.manoamiga.act.dto.ComentarioDto;
import com.tecnara.manoamiga.act.dto.ComentarioMonitorDto;
import com.tecnara.manoamiga.act.dto.TutorDto;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/tutor")
public class AdminTutorController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new TutorEntity());
        } else {
            m.addAttribute("registro", repoTutor.findById(id).get());
        }
        return "act/admin/tutor_form";
    }
    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IComentarioRepository repoComentario;

    @GetMapping("/list")
    public String list(Model m) {
        m.addAttribute("nuevos", repoTutor.adminContarUsuariosPendientes());
        return "act/admin/tutor_list";
    }
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private IUsuarioRepository repoUser;

    public TutorDto convertDto(TutorEntity origen) {
        TutorDto nuevo = mapper.map(origen, TutorDto.class);
        if (origen.getIdUsuario() != null) {
            UsuarioEntity user = repoUser.findById(origen.getIdUsuario()).orElse(new UsuarioEntity());
            nuevo.setUsuario(user.getUsuario());
        }
        return nuevo;

    }

    @GetMapping("/help")
    public String help(Model m) {
        return "act/admin/tutor_help";
    }

    @PostMapping("/guardar")
    public String guardar(TutorEntity registro) {
        repoTutor.save(registro);
        return "redirect:list";

    }

    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        TutorEntity ent=repoTutor.getById(id);
        repoTutor.deleteById(id);
        if (ent!=null && ent.getIdUsuario()!=null){
            repoUsuario.deleteById(ent.getIdUsuario());
        }        
        return "redirect:list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<TutorDto> listData() {
        return repoTutor.findAll(Pageable.ofSize(1001)).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    //pestaña 2
    @Autowired
    private IMonitorRepository repoMonitor;

    public ComentarioMonitorDto convertDto(ComentarioEntity convert) {
        ComentarioMonitorDto nuevo = mapper.map(convert, ComentarioMonitorDto.class);

        MonitorEntity monitor = repoMonitor.findById(convert.getIdMonitor()).orElse(new MonitorEntity());
        nuevo.setNombreMonitor(monitor.getNombre());

        return nuevo;
    }

    @GetMapping("/list_data_comentario")
    @ResponseBody
    public List<ComentarioMonitorDto> listDataComentario(Long idTutor) {
        return repoComentario.findByIdTutor(idTutor).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    //pestaña1
    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private IUsuarioRepository repoUsuario;

    public BeneficiarioDto convertDto(BeneficiarioEntity origen) {
        BeneficiarioDto nuevo = mapper.map(origen, BeneficiarioDto.class);
        /*
        BeneficiarioDto nuevo = new BeneficiarioDto();
        nuevo.setId(origen.getId());
        nuevo.setNombre(origen.getNombre());
        nuevo.setTelefono(origen.getTelefono());
        nuevo.setApellido(origen.getApellido1() + " " + origen.getApellido2());*/

        if (origen.getIdUsuario()!=null){
            UsuarioEntity usuario = repoUsuario.findById(origen.getIdUsuario()).orElse(new UsuarioEntity());
            nuevo.setUsuario(usuario.getUsuario());
        }

        return nuevo;
    }

    @GetMapping("/list_data_beneficiario")
    @ResponseBody
    public List<BeneficiarioDto> listDataBeneficiario(Long idTutor) {
        return repoBeneficiario.findByIdTutor(idTutor).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoTutor.findByNombreContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), general.concat(x.getNombre(),x.getApellido())))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        TutorEntity x = repoTutor.findById(id).orElse(new TutorEntity());
         return general.concat(x.getNombre(),x.getApellido());
    }
    
     @GetMapping("/buscador_tutor")
    @ResponseBody
    public List<ClaveValorDto> buscador (String filtro, Long idUsuario){
        return repoTutor.adminTutorFromBuscador(filtro,idUsuario).stream()
                .map(x->new ClaveValorDto(Long.parseLong(x.get("id")+""), x.get("usuario")+""))
                .collect(Collectors.toList());
    }    
    
    @GetMapping("/buscador_tutor_id")
    @ResponseBody
    public String buscadorTutor(Long id){
        UsuarioEntity x= repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }
}
