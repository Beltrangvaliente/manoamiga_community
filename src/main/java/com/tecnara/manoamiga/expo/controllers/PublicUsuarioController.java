package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.aaa.services.adapters.UserServiceAdapter;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.expo.entities.CoordinadorEntity;
import com.tecnara.manoamiga.expo.repositories.ICoordinadorRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("expo_public_usuario")
@RequestMapping("expo/public/usuario")
public class PublicUsuarioController extends TecnaraBaseController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IGeneral general;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IEmailService email;

    @Autowired
    private IEmailService mail;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new UsuarioEntity());
        } else {
            m.addAttribute("registro", repoUsuario.findById(id).get());
        }
        return "expo/public/usuario_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/public/usuario_list";
    }

    @GetMapping("/recuperar_password")
    public String clave(Model m) {
        return "expo/public/usuario_recuperar_password";
    }

    @GetMapping("/usuario_recuperar_password_cambiado")
    public String recuperarPassword(Model m, String code) {
        if (code == null) {
            return "redirect:/expo/public/index?error=Codigo incorrecto";
        }
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(code).get();
        m.addAttribute("registro", usuario);
        return "expo/public/usuario_recuperar_password_cambiado";
    }

    @PostMapping("/recuperar_password_enviar_email")
    public String recuperarPasswordEnviarEmail(Model m, String email) {
        List<UsuarioEntity> optUsu = repoUsuario.findByEmail(email);
        if (optUsu.size() == 0) {
            return "redirect:recuperar_password?error=Usuario no encontrado";
        }

        UsuarioEntity usu = optUsu.get(0);
        //Buscar el usuario con el email introducido
        EmailInfoDto info = new EmailInfoDto();
        info.setCodigoUsuario(usu.getCodigoVerificacion());
        info.setTituloEmail("Restablecer contraseña");
        info.setFicheroEmail("expo/usuario_recuperar_password_email");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        // findByEmail.get()
        //Enviar el email.
        return "expo/public/usuario_recuperar_password_confirmacion";

    }

    @GetMapping("/recuperar_password_confirmacion")
    public String recuperarPasswordConfirmacion(Model m) {
        return "expo/public/usuario_recuperar_password_confirmacion";
    }

    @PostMapping("/recuperar_password_guardar")
    public String guardarPassword(String codigoVerificacion, String password) {
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(codigoVerificacion).get();
        usuario.setPassword(passwordEncoder.encode(password));
        repoUsuario.save(usuario);
        return "expo/public/usuario_recuperar_password_guardar";
    }

    @GetMapping("/validar")
    public String validar(String code) {
        if (code != null) {
            repoUsuario.validarEstadoByCodigoVerificacion("Pendiente", code);
        }
        return "expo/public/usuario_registro_realizado";    //Indicar al usuario que se ha validado.
    }

    @GetMapping("/verificar")
    public String verificar(String code) {
        if (code != null) {
            repoUsuario.updateEstadoByCodigoVerificacion("Aceptado", code);
        }
        return "redirect:form?error=Se va a verificar el usuario";    //Indicar al usuario que se verificarán los datos.
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "expo/public/usuario_help";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String usuario, String estado, String rol) {
        return repoUsuario.adminListData(estado, usuario, rol);
    }

    @PostMapping("/guardar")
    public String guardar(UsuarioEntity registro, String rol) {
        registro.setRol("ROLE_EXPO_Admin");
        repoUsuario.save(registro);
        return "redirect:list";
    }
     @GetMapping("/recuperar_password_form")
    public String recuperarPasswordForm(Model m, String code) {
        if (code == null) {
            return "redirect:/expo/public/index?error=Codigo incorrecto";
        }
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(code).get();
        m.addAttribute("registro", usuario);
        return "expo/public/usuario_recuperar_password_form";
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

    @GetMapping("/login")
    public String index(Model m, String error) {
        m.addAttribute("error", error);
       return "expo/public/usuario_login";
    }

    @Autowired
    private AuthenticationManager authMng;

    @PostMapping("/login")
    public String indexPost(String usuario, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usuario, password);
        Authentication auth = authMng.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (auth.isAuthenticated()) {
            UserServiceAdapter user = (UserServiceAdapter) auth.getPrincipal();
            switch (user.getUsuario().getRol()) {
                case "ROLE_EXPO_Coordinador":
                    return "redirect:/expo/coordinador/index";
                case "ROLE_EXPO_Trabajador":
                    return "redirect:/expo/trabajador/actividad/list";

                default:
                    return "redirect:/expo/index";
            }
        } else {
            return "redirect:login?error=Usuario o password incorrecto";
        }
    }

    @Autowired
    private ICoordinadorRepository repoCoordinador;

    @GetMapping("/init")
    public String init() {
        if (repoUsuario.countByRolAndEstado("ROLE_EXPO_Coordinador", "Aceptado") == 0) {
            return "expo/public/usuario_init";
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }

    @PostMapping("/init")
    public String init(String usuario, String password, String email) {
        if (repoUsuario.countByRolAndEstado("ROLE_EXPO_Coordinador", "Aceptado") == 0) {
            UsuarioEntity user = new UsuarioEntity();
            user.setUsuario(usuario);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRol("ROLE_EXPO_Coordinador");
            user.setCodigoVerificacion(UUID.randomUUID().toString());
            user.setEstado("Aceptado");
            repoUsuario.save(user);

            CoordinadorEntity coor = new CoordinadorEntity();
            coor.setNombre(usuario);
            coor.setFechaAlta(general.getFechaActual());
            coor.setIdUsuario(user.getId());
            repoCoordinador.save(coor);
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }

}
