package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.emp.entities.TrabajadorEntity;
import com.tecnara.manoamiga.emp.repositories.ITrabajadorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("trabajador_emp")
@RequestMapping("/emp/trabajador")
public class TrabajadorController extends TecnaraBaseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ITrabajadorRepository repoTrabajador;
    
    @Autowired
    private IUsuarioRepository repoUsuario;

    @GetMapping("/index")
    public String index() {
        return "emp/trabajador/index";
    }

    @GetMapping("/buscador_trabajador_id")
    @ResponseBody
    public String buscadorTrabajador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }

    @GetMapping("/buscador_trabajador")
    @ResponseBody
    public List<ClaveValorDto> buscadorTrabajador(String filtro, Long id) {
        return repoTrabajador.tecnicoFromBuscador(filtro, id)
                .stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id") + ""), x.get("usuario") + ""))
                .collect(Collectors.toList());

    }
}
