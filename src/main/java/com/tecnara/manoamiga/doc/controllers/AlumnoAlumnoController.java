package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/alumno/alumno")
public class AlumnoAlumnoController extends TecnaraBaseController{
    
    @Autowired
    private IGeneral general;
    
    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @GetMapping("/form")
    public String form(Model m) {
        m.addAttribute("registro", repoAlumno.findById(general.getIdValidado()).get());
        return "doc/alumno/alumno_form";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoAlumno.findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(filtro, filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre() + " " + x.getApellido1() + " " + x.getApellido2()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        AlumnoEntity x= utils.findById(repoAlumno, id, AlumnoEntity.class);
        return x.getNombre() + " " + x.getApellido1() + " " + x.getApellido2();
    }      
    
    @PostMapping("/guardar")
    public String guardar(AlumnoEntity alumno) {
        if (alumno.getId()!=general.getIdValidado()){ 
            return "redirect:form?error=No es posible guardar";
        }
        repoAlumno.save(alumno);
        return "redirect:form?confirmacion=Datos modificados correctamente";
    }
    
/*
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoCentroAtencionRepositoty.findByNombreContainingIgnoreCase(filtro)
                .stream().map(x -> new ClaveValorDto(x.getId(), x.getNombre())).collect(Collectors.toList());
    }*/
    
    @Autowired
    private IUsuarioRepository repoUsuario;
    
   
    @Autowired
    private PasswordEncoder passwordEncoder;

    
   @PostMapping("/recuperar_password_guardar")
    public String guardarPassword(String passwordreg) {
       //Hacer un hidden en el formulario con el codigoVerificacion(esto no hacia falta
       //y lo hemos eliminado porque el usuario ya estaba validado.
       UsuarioEntity usuario = repoUsuario.findById(general.getIdUsuario()).get();
       usuario.setPassword(passwordEncoder.encode(passwordreg));
       repoUsuario.save(usuario);
       return "doc/alumno/alumno_recuperar_password_cambiada_show";

     }



    @GetMapping("/restablecer_password_form") 
    public String recuperarPasswordForm(Model m) {
        return "doc/alumno/alumno_recuperar_password_form";
      }

}
    


