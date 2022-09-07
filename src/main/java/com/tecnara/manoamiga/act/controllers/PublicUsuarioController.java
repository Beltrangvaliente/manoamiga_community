package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.aaa.services.adapters.UserServiceAdapter;
import com.tecnara.manoamiga.act.entities.AdministradorEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IAdministradorRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

@Controller
@RequestMapping("/act/public/usuario")
public class PublicUsuarioController extends TecnaraBaseController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private IGeneral general;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @Autowired
    private IEmailService email;

    @Autowired
    private IEmailService usuario;

    @Autowired
    private IEmailService mail;

    @Autowired
    private IMonitorRepository repoMonitor;

    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;


    
    @GetMapping("/login")
    public String index(Model m, String error) {
        m.addAttribute("error", error);
        return "act/public/usuario_login";
    }
    
    @Autowired
    private AuthenticationManager authMng;    

    @PostMapping("/login")
    public String indexPost(String usuario, String password) {
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(usuario, password);
        Authentication auth=authMng.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (auth.isAuthenticated()){
            UserServiceAdapter user=(UserServiceAdapter)auth.getPrincipal();
            switch(user.getUsuario().getRol()){
                case "ROLE_ACT_Admin":
                    return "redirect:/act/admin/index";
                case "ROLE_ACT_Beneficiario":
                    return "redirect:/act/beneficiario/actividad/list";
                case "ROLE_ACT_Monitor":
                    return "redirect:/act/monitor/index";
                case "ROLE_ACT_Profesor":
                    return "redirect:/act/profesor/beneficiario/list";
                case "ROLE_ACT_Tutor":
                    return "redirect:/act/tutor/beneficiario/list";
                default:
                    return "redirect:/act/index";
            }
        }else{
            return "redirect:login?error=Usuario o password incorrecto";    
        }
    }

    @GetMapping("/form")
    public String form(Model m) {
        m.addAttribute("registro", new UsuarioEntity());
        m.addAttribute("texto_legal", general.getPreferencia("public_registro_texto_legal"));
        
        return "act/public/usuario_form";
    }

    @GetMapping("/recuperar_password")
    public String recuperarPassword(Model m) {
        return "act/public/usuario_recuperar_password";
    }

    @GetMapping("/recuperar_password_confirmacion")
    public String recuperarPasswordConfirmacion(Model m) {
        return "act/public/usuario_recuperar_password_confirmacion";
    }

    @PostMapping("/recuperar_password_enviar_email")
    public String recuperarPasswordEnviarEmail(Model m, String email) {
        List<UsuarioEntity> optUsu = repoUsuario.findByEmail(email);
        if (optUsu.size()==0) {
            return "redirect:recuperar_password?error=Usuario no encontrado";
        }
        UsuarioEntity usu = optUsu.get(0);
        //Buscar el usuario con el email introducido
        EmailInfoDto info = new EmailInfoDto();
        info.setCodigoUsuario(usu.getCodigoVerificacion());
        info.setTituloEmail("Restablecer contraseña");
        info.setFicheroEmail("act/usuario_recuperar_password_email");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        // findByEmail.get()
        //Enviar el email.
        return "act/public/usuario_recuperar_password_confirmacion";

    }

    @GetMapping("/recuperar_password_form")
    public String recuperarPasswordForm(Model m, String code) {
        if (code == null) {
            return "redirect:/act/public/index?error=Codigo incorrecto";
        }
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(code).get();
        m.addAttribute("registro", usuario);
        return "act/public/usuario_recuperar_password_form";
    }

    @PostMapping("/recuperar_password_guardar")
    public String guardarPassword(String codigoVerificacion, String password) {
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(codigoVerificacion).get();
        usuario.setPassword(passwordEncoder.encode(password));
        repoUsuario.save(usuario);
        return "act/public/usuario_recuperar_password_cambiada_show";
    }

 

    @GetMapping("/validar")
    public String validar(String code) {
        if (code != null) {
            repoUsuario.validarEstadoByCodigoVerificacion("Pendiente", code);
        }
        return "act/public/usuario_registro_realizado";    //Indicar al usuario que se ha validado.
    }    
    
    @GetMapping("/verificar")
    public String verificar( String code) {
        if (code != null) {
            repoUsuario.updateEstadoByCodigoVerificacion("Aceptado", code);
        }
        return "redirect:form?error=Se va a verificar el usuario";    //Indicar al usuario que se verificarán los datos.
    }

    @PostMapping("/guardar")
    public String guardar(UsuarioEntity usuario, String nombre, String apellido1, String apellido2, String tipoUsuario, String email, String passwordreg) {
        if (repoUsuario.findByUsuario(usuario.getEmail()).isPresent()) {
            return "redirect:form?error=El usuario ya existe";
        }

        usuario.setPassword(passwordEncoder.encode(passwordreg));
        usuario.setUsuario(usuario.getEmail());
        usuario.setEstado("Pendiente de verificacion");
        usuario.setCodigoVerificacion(UUID.randomUUID().toString());
        
        if (tipoUsuario.equals("tutor")) {
           usuario.setRol("ROLE_ACT_Tutor");
        } else if (tipoUsuario.equals("monitor")) {
            usuario.setRol("ROLE_ACT_Monitor");
        } else if (tipoUsuario.equals("beneficiario")) {
            usuario.setRol("ROLE_ACT_Beneficiario");
        }
        usuario = repoUsuario.save(usuario);

        //tipoUsuario
       if (tipoUsuario.equals("tutor")) {

            TutorEntity tutor = new TutorEntity();
            tutor.setNombre(nombre);
            tutor.setApellido(apellido1 + " " + apellido2);
            tutor.setIdUsuario(usuario.getId());
            repoTutor.save(tutor);

        } else if (tipoUsuario.equals("monitor")) {

            MonitorEntity monitor = new MonitorEntity();
            monitor.setNombre(nombre);
            monitor.setApellido1(apellido1);
            monitor.setApellido2(apellido2);
            monitor.setIdUsuario(usuario.getId());
            repoMonitor.save(monitor);

        } else if (tipoUsuario.equals("beneficiario")) {

            BeneficiarioEntity beneficiario = new BeneficiarioEntity();
            beneficiario.setNombre(nombre);
            beneficiario.setApellido1(apellido1);
            beneficiario.setApellido2(apellido2);
            beneficiario.setIdUsuario(usuario.getId());
            repoBeneficiario.save(beneficiario);
        }

        EmailInfoDto info = new EmailInfoDto();
        
        info.setNombre(nombre);
        info.setApellido1(apellido1);
        info.setApellido2(apellido2);
        info.setEmail(email);
        info.setCodigoUsuario(usuario.getCodigoVerificacion());
        
        info.setTituloEmail("Registro usuario");
        info.setFicheroEmail("act/usuario_registro_email");
        info.setEmail(usuario.getEmail());
        mail.enviarEmail(info);
        
        return "act/public/usuario_registro_confirmacion";

    }
    
    @GetMapping("/cancelar")
    public String borrar(String code) {
        repoUsuario.cancelarByCodigoVerificacion(code);
        return "act/public/usuario_cancelar_registro";

    }
    /*En el correo de confirmación el link: 
    /act/public/usuario/cancelar?code=375ff5c8-019c-4596-b66e-6cd821106dc6 no está hecho. 
    Este debe borrar el usuario , el tutor , beneficiario y monitor en caso de estar en estado del usuario “Pendiente de verificación”. 
    En caso de no estar en ese estado no hacer nada. 
    Mostrar un mensaje indicando si se ha borrado o no.*/

    @Autowired
    private IAdministradorRepository repoAdministrador;
        
    @GetMapping("/init")
    public String init() {
        if (repoUsuario.countByRolAndEstado("ROLE_ACT_Admin", "Aceptado")==0){
            return "act/public/usuario_init";
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }

    @PostMapping("/init")
    public String init(String usuario, String password, String email) {
        if (repoUsuario.countByRolAndEstado("ROLE_ACT_Admin", "Aceptado")==0){
            UsuarioEntity user=new UsuarioEntity();
            user.setUsuario(usuario);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRol("ROLE_ACT_Admin");
            user.setCodigoVerificacion(UUID.randomUUID().toString());
            user.setEstado("Aceptado");
            repoUsuario.save(user);
            
            AdministradorEntity admin=new AdministradorEntity();
            admin.setNombre(usuario);
            admin.setIdUsuario(user.getId());
            admin.setFechaActualizacion(general.getFechaActual());
            repoAdministrador.save(admin);
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }

    
}
