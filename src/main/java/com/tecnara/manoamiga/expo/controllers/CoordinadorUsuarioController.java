package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.repositories.ICoordinadorRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expo/coordinador/usuario")
public class CoordinadorUsuarioController extends TecnaraBaseController{

    @Autowired
    private IUsuarioRepository repoUsuario;
    
    @Autowired
    private IEmailService email;
    

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoUsuario.findByUsuarioContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getUsuario()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }
    
   @GetMapping("/form")
    public String form(Model m, Long id, String rol) {
        if (id == null) {
            UsuarioEntity usuario= new UsuarioEntity();
            if (rol!=null){
                usuario.setRol(rol);    
            }else{
                usuario.setRol("ROLE_EXPO_Coordinador");    
            }
            m.addAttribute("registro", usuario);
        } else {
            m.addAttribute("registro", repoUsuario.findById(id).get());
        }
        return "expo/coordinador/usuario_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/coordinador/usuario_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "expo/coordinador/usuario_help";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String usuario, String estado, String rol) {
        return repoUsuario.adminListData(estado, usuario, rol);
    }

    @PostMapping("/guardar")
    public String guardar(UsuarioEntity registro, String enviarEmail) {
        if (registro.getId() == null) {
            registro.setCodigoVerificacion(UUID.randomUUID().toString());
        } else {
            UsuarioEntity lectura=repoUsuario.findById(registro.getId()).get();
            registro.setCodigoVerificacion(lectura.getCodigoVerificacion());
            registro.setRol(lectura.getRol());
            registro.setPassword(lectura.getPassword());
        }
        repoUsuario.save(registro);
        if ("si".equalsIgnoreCase(enviarEmail)){
            EmailInfoDto info = new EmailInfoDto();
            info.setCodigoUsuario(registro.getCodigoVerificacion());
            info.setTituloEmail("Requerimiento de cambio de contraseña");
            info.setFicheroEmail("expo/usuario_guardar_password_email");
            info.setEmail(registro.getEmail());
            this.email.enviarEmail(info);
        }        
        
        return "redirect:list";
    }
    
    @PostMapping("/guardar_ajax")
    @ResponseBody
    public Long guardarAjax(UsuarioEntity registro, String enviarEmail) {
        if (registro.getId() == null) {
            registro.setCodigoVerificacion(UUID.randomUUID().toString());
        } else {
            UsuarioEntity lectura=repoUsuario.findById(registro.getId()).get();
            registro.setCodigoVerificacion(lectura.getCodigoVerificacion());
            registro.setRol(lectura.getRol());
            registro.setPassword(lectura.getPassword());
        }
        repoUsuario.save(registro);
        if ("si".equalsIgnoreCase(enviarEmail)){
            EmailInfoDto info = new EmailInfoDto();
            info.setCodigoUsuario(registro.getCodigoVerificacion());
            info.setTituloEmail("Requerimiento de cambio de contraseña");
            info.setFicheroEmail("expo/usuario_guardar_password_email");
            info.setEmail(registro.getEmail());
            this.email.enviarEmail(info);
        }        
        return registro.getId();
    }    

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoUsuario.deleteById(id);
        return "redirect:list";
    }
    
    @GetMapping("/activar_usuario")
    public String activarUsuario(Long idUsuario) {
        repoUsuario.usuarioPendiente(idUsuario);
        Optional<UsuarioEntity> opt = repoUsuario.findById(idUsuario);
        UsuarioEntity usu = opt.get();
        EmailInfoDto info = new EmailInfoDto();
        info.setTituloEmail("Usuario Activado");
        info.setFicheroEmail("/expo/usuario_activado");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        return "redirect:list";

    }

    //TeamB /expo/coordinador/usuario/form Al crear un usuario 
    @GetMapping("/reestablecer_contrasenya")
    public String reestablecerPasswordEnviarEmail(Model m, Long id) {
        Optional<UsuarioEntity> optUsu = repoUsuario.findById(id);
        if (optUsu.isEmpty()) {
            return "redirect:recuperar_password?error=Usuario no encontrado";
        }
        UsuarioEntity usu = optUsu.get();
        //Buscar el usuario con el email introducido
        EmailInfoDto info = new EmailInfoDto();
        info.setCodigoUsuario(usu.getCodigoVerificacion());
        info.setTituloEmail("Reestablecer contraseña");
        info.setFicheroEmail("act/usuario_recuperar_password_email");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        // findByEmail.get()
        //Enviar el email.
        return "expo/coordinador/usuario_recuperar_password_confirmacion";

        /*TeamB /expo/coordinador/usuario/form Al crear un usuario 
     nuevo desde esta ventana se debe almacenar con el rol Expo_Coordinador.*/
    
    }
    
    @PostMapping("/reestablecer_password_enviar_email")
    public String reestablecerPasswordEnviarEmailString(Model m, String email) {
        List<UsuarioEntity> optUsu = repoUsuario.findByEmail(email);
        if (optUsu.size() == 0) {
            return "redirect:reestablecer_password?error=Usuario no encontrado";
        }
        UsuarioEntity usu = optUsu.get(0);
        //Buscar el usuario con el email introducido
        EmailInfoDto info = new EmailInfoDto();
        info.setCodigoUsuario(usu.getCodigoVerificacion());
        info.setTituloEmail("Reestablecer contraseña");
        info.setFicheroEmail("expo/usuario_reestablecer_password_enviar_email");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        // findByEmail.get()
        //Enviar el email.
        return "expo/coordinador/usuario_reestablecer_password_confirmacion";

    }
    
    
 
}

