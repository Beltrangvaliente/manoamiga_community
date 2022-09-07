package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.act.dto.TutorDto;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.swing.Spring;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/usuario")
public class AdminUsuarioController extends TecnaraBaseController {

    @Autowired
    private IUsuarioRepository repoUsuario;
    @Autowired
    private IEmailService email;

    @Autowired
    private IGeneral general;

    @Autowired
    private IEmailService mail;

    @GetMapping("/form")
    public String form(Model m, Long id, String rol) {
        if (id == null) {
            UsuarioEntity usuario = new UsuarioEntity();
            if (rol != null) {
                usuario.setRol(rol);
            }

            m.addAttribute("registro", usuario);
        } else {
            m.addAttribute("registro", repoUsuario.findById(id).get());
        }
        return "act/admin/usuario_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/usuario_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "act/admin/usuario_help";
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
            UsuarioEntity lectura = repoUsuario.findById(registro.getId()).get();
            registro.setCodigoVerificacion(lectura.getCodigoVerificacion());
            registro.setRol(lectura.getRol());
            registro.setPassword(lectura.getPassword());
        }
        repoUsuario.save(registro);
        if ("si".equalsIgnoreCase(enviarEmail)){
            EmailInfoDto info = new EmailInfoDto();
            info.setCodigoUsuario(registro.getCodigoVerificacion());
            info.setTituloEmail("Requerimiento de cambio de contraseña");
            info.setFicheroEmail("act/usuario_guardar_password_email");
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
            UsuarioEntity lectura = repoUsuario.findById(registro.getId()).get();
            registro.setCodigoVerificacion(lectura.getCodigoVerificacion());
            registro.setRol(lectura.getRol());
            registro.setPassword(lectura.getPassword());
        }
        repoUsuario.save(registro);
        if ("si".equalsIgnoreCase(enviarEmail)){
            EmailInfoDto info = new EmailInfoDto();
            info.setCodigoUsuario(registro.getCodigoVerificacion());
            info.setTituloEmail("Requerimiento de cambio de contraseña");
            info.setFicheroEmail("act/usuario_guardar_password_email");
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

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoUsuario.findByUsuarioContainingIgnoreCase(filtro).stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getUsuario()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }

    @GetMapping("/activar_usuario")
    public String activarUsuario(Long idUsuario) {
        repoUsuario.usuarioPendiente(idUsuario);
        Optional<UsuarioEntity> opt = repoUsuario.findById(idUsuario);
        UsuarioEntity usu = opt.get();
        EmailInfoDto info = new EmailInfoDto();
        info.setUsuario(usu.getUsuario());
        info.setTituloEmail("Usuario Activado");
        info.setFicheroEmail("/act/usuario_activado");
        info.setEmail(usu.getEmail());
        this.mail.enviarEmail(info);
        return "redirect:list";

    }

    //TeamB /act/admin/usuario/form Al crear un usuario 
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
        return "act/admin/usuario_recuperar_password_confirmacion";

        /*TeamB /act/admin/usuario/form Al crear un usuario 
     nuevo desde esta ventana se debe almacenar con el rol Act_Admin.*/
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
        info.setFicheroEmail("act/usuario_reestablecer_password_enviar_email");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        // findByEmail.get()
        //Enviar el email.
        return "act/admin/usuario_reestablecer_password_confirmacion";

    }
    
    @GetMapping("/check_usuario")
    @ResponseBody
    public String checkUsuario(Long id, String usuario, String email){
        if (id==null){
            id=0L;
        }
        if (id>0){
            if (usuario!=null && usuario.length()>0){
                if (repoUsuario.existsByUsuario(usuario)){
                    return "Usuario";
                }
            }
            if (email!=null && email.length()>0){
                if (repoUsuario.existsByEmail(email)){ 
                    return "Email";
                }
            }
            return "";    
        }else{
            if (usuario!=null && usuario.length()>0){
                if (repoUsuario.existsByIdNotAndUsuario(id, usuario)){
                    return "Usuario";
                }
            }
            if (email!=null && email.length()>0){ 
                if (repoUsuario.existsByIdNotAndEmail(id, email)){
                    return "Email";
                }
            }
            return "";    
        }
        
    }
    
}
