package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.entities.CoordinadorEntity;
import com.tecnara.manoamiga.expo.repositories.ICoordinadorRepository;
import java.util.List;
import com.tecnara.manoamiga.expo.entities.CoordinadorEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.ICoordinadorRepository;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expo/coordinador/coordinador")
public class CoordinadorCoordinadorController extends TecnaraBaseController {

    @Autowired
    private ICoordinadorRepository repoCoordinador;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/coordinador/coordinador_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<CoordinadorEntity> listData() {
        return repoCoordinador.findAll();
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {

        if (id == null) {
            m.addAttribute("registro", new CoordinadorEntity());
        } else {
            m.addAttribute("registro", repoCoordinador.findById(id).get());
        }

        return "expo/coordinador/coordinador_form";
    }

    @PostMapping("/guardar")
    public String guardar(CoordinadorEntity registro) {
        repoCoordinador.save(registro);
        return "redirect:list";
    }
    
    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        CoordinadorEntity ent=repoCoordinador.getById(id);
        repoCoordinador.deleteById(id);
        if (ent.getIdUsuario()!=null && repoUsuario.existsById(ent.getIdUsuario())){
            repoUsuario.deleteById(ent.getIdUsuario());
        }           
        return "redirect:list";
    }


    @GetMapping("/buscador_coordinador_id")
    @ResponseBody
    public String buscadorCoordinador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }

    @GetMapping("/buscador_coordinador")
    @ResponseBody
    public List<ClaveValorDto> buscadorCoordinador(String filtro, Long idUsuario) {
        return repoCoordinador.coordinadorFromBuscador(filtro, idUsuario)
                .stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id") + ""), x.get("usuario") + ""))
                .collect(Collectors.toList());

    }

}
