package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.aaa.services.adapters.UserServiceAdapter;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
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

@Controller("doc_public_usuario_controller")
@RequestMapping("/doc/public/usuario")
public class PublicUsuarioController extends TecnaraBaseController {

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @Autowired
    private IEmailService email;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/form")
   
    public String form(Model m) {
        m.addAttribute("registro", new UsuarioEntity());
        m.addAttribute("texto_legal",general.getPreferencia("public_registro_texto_legal"));
        
        return "doc/public/usuario_form";
    }


    
    @GetMapping("/login")
    public String login(Model m, String url, String error) {
        m.addAttribute("error", error);
        m.addAttribute("url", url);
        
       return "doc/public/usuario_login";
    }
    
    @Autowired
    private AuthenticationManager authMng;    
    
    @PostMapping("/login")
    public String indexPost(String usuario, String password, String url) {
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(usuario, password);
        Authentication auth=authMng.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (auth.isAuthenticated()){
            if (url==null || url.length()==0){
                UserServiceAdapter user=(UserServiceAdapter)auth.getPrincipal();
                String rol=user.getUsuario().getRol();
                switch(rol){
                    case "ROLE_DOC_Admin":
                        return "redirect:/doc/admin/index";
                    case "ROLE_DOC_Alumno":
                        return "redirect:/doc/alumno/index";
                    case "ROLE_DOC_Profesor":
                        return "redirect:/doc/profesor/index";
                    default:
                        return "redirect:/doc/index";
                }
            }else{
                return "redirect:" + url;
            }
            //return "redirect:login?error=Este codigo se está ejecutando";    
        }else{
            return "redirect:login?error=Usuario o password incorrecto";    
        }
    }    


    @PostMapping("/registrar")
    public String registrar(UsuarioEntity usuario, AlumnoEntity alumno) {
        return "doc/public/usuario_registrar";
    }
    
 

    @GetMapping("/recuperar_password")
    public String recuperarPassword(Model m) {
        return "doc/public/usuario_recuperar_password";
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
        info.setFicheroEmail("doc/usuario_recuperar_password_email");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        // findByEmail.get()
        //Enviar el email.
        return "doc/public/usuario_recuperar_password_confirmacion";
    }
    
   

    @GetMapping("/verificar")
    public String verificar(String codigoVerificacion) {
        if (codigoVerificacion != null) {
            repoUsuario.updateEstadoByCodigoVerificacion("Pendiente", codigoVerificacion);
        }
        return "doc/public/usuario_registro_mensaje_ok";    //Indicar al usuario que se verificará los datos.
    }
    
    @GetMapping("/validar")
    public String validar(String code) {
        if (code != null) {
          repoUsuario.updateEstadoByCodigoVerificacion("Pendiente", code);
        }
        return "/doc/public/usuario_registro_validar";   
    }
    


    @PostMapping("/guardar")
    public String guardar(AlumnoEntity alumno, UsuarioEntity usuario) {
        if (repoUsuario.findByUsuario(usuario.getEmail()).isPresent()) {
            return "redirect:form?error=El usuario ya existe";
        }
        usuario.setUsuario(usuario.getEmail());
        usuario.setEstado("Pendiente de verificacion");
        usuario.setCodigoVerificacion(UUID.randomUUID().toString());
        usuario = repoUsuario.save(usuario);

        alumno.setIdUsuario(usuario.getId());
        alumno.setMail(usuario.getEmail());
        repoAlumno.save(alumno);

        
        //save
        EmailInfoDto info = new EmailInfoDto();
        info.setNombre(alumno.getNombre());
        info.setApellido1(alumno.getApellido1());
        info.setApellido2(alumno.getApellido2());
        info.setCodigoUsuario(usuario.getCodigoVerificacion());
        info.setTituloEmail("Registro usuario");
        info.setFicheroEmail("doc/usuario_registro_email");
        info.setEmail(usuario.getEmail());

        email.enviarEmail(info);
        return "doc/public/usuario_registro_confirmacion";
    }

    @Autowired
    private IEmailService usuario;
    
    @GetMapping("/recuperar_password_confirmacion")
    public String recuperarPasswordConfirmacion(Model m) {
        return "doc/public/usuario_recuperar_password_confirmacion";
    }

    
    
    @GetMapping("/recuperar_password_form") 
    public String recuperarPasswordForm(Model m, String code) {
        if (code==null){
            return "/doc/public/index?error=Codigo incorrecto";
        }
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(code).get();
        //usuario.setPassword(code);
        //repoUsuario.save(usuario);
        m.addAttribute("registro", usuario);
        return "doc/public/usuario_recuperar_password_form";
    }
  
    @PostMapping("/recuperar_password_guardar")
    public String guardarPassword(UsuarioEntity registro) {
        //Hacer un hidden en el formulario con el codigoVerificacion
        UsuarioEntity usuario=repoUsuario.findByCodigoVerificacion(registro.getCodigoVerificacion()).get();
        usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
        repoUsuario.save(usuario);
        return "doc/public/usuario_recuperar_password_cambiada_show";
    }
    
    @GetMapping("/cancelar")
    public String cancelar(String code, Model m) {
        int registrosBorrados=repoUsuario.cancelarByCodigoVerificacion(code);
        if (registrosBorrados==0){
            m.addAttribute("mensaje", "No se ha borrado el usuario");
        }else{
            m.addAttribute("mensaje", "Usuario cancelado correctamente");
        }
        // <span th:text="${mensaje}"/>
        return "doc/public/usuario_cancelar_registro";
    }
    
      
    
    /*En el correo de confirmación el link: /act/public/usuario/cancelar?code=375ff5c8-019c-4596-b66e-6cd821106dc6 no está hecho. 
    Este debe borrar el usuario , el tutor , beneficiario y monitor en caso de estar en estado del usuario “Pendiente de verificación”. 
    En caso de no estar en ese estado no hacer nada. Mostrar un mensaje indicando si se ha borrado o no.
    */
    
    @GetMapping("/init")
    public String init() {
        if (repoUsuario.countByRolAndEstado("ROLE_DOC_Admin", "Aceptado")==0){
            return "doc/public/usuario_init";
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }

    @PostMapping("/init")
    public String init(String usuario, String password, String email) {
        if (repoUsuario.countByRolAndEstado("ROLE_DOC_Admin", "Aceptado")==0){
            UsuarioEntity user=new UsuarioEntity();
            user.setUsuario(usuario);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRol("ROLE_DOC_Admin");
            user.setCodigoVerificacion(UUID.randomUUID().toString());
            user.setEstado("Aceptado");
            repoUsuario.save(user);
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }
    
    


    
    
}


