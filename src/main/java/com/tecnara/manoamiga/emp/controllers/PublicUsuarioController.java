package com.tecnara.manoamiga.emp.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.aaa.services.adapters.UserServiceAdapter;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import java.util.List;
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

@Controller("emp_public_usuario")
@RequestMapping("/emp/public/usuario")
public class PublicUsuarioController extends TecnaraBaseController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private IGeneral general;
    
     @Autowired
    private IEmailService email;

    @Autowired
    private IEmailService usuario;
    
    @Autowired
    private IUsuarioRepository repoUsuario;

    @GetMapping("/login")
    public String index(Model m, String error) {
        m.addAttribute("error", error);
        return "emp/public/usuario_login";
    } 
     @GetMapping("/form")
    public String form(Model m) {
        m.addAttribute("registro", new UsuarioEntity());
        m.addAttribute("texto_legal", general.getPreferencia("public_registro_texto_legal"));
        
        return "emp/public/usuario_form";
    }
          @GetMapping("/recuperar_password")
    public String recuperarPassword(Model m) {
        return "emp/public/usuario_recuperar_password";
    }
      @GetMapping("/recuperar_password_confirmacion")
    public String recuperarPasswordConfirmacion(Model m) {
        return "emp/public/usuario_recuperar_password_confirmacion";
    
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
        info.setFicheroEmail("emp/usuario_recuperar_password_email");
        info.setEmail(usu.getEmail());
        this.email.enviarEmail(info);
        // findByEmail.get()
        //Enviar el email.
        return "emp/public/usuario_recuperar_password_confirmacion";
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
                case "ROLE_EMP_Tecnico":
                    return "redirect:/emp/tecnico/index";
                default:
                    return "redirect:/emp/index";
            }
        }else{
            return "redirect:login?error=Usuario o password incorrecto";    
        }
    }    
    
    @GetMapping("/recuperar_password_form")
    public String recuperarPasswordForm(Model m, String code) {
        if (code == null) {
            return "redirect:/emp/public/index?error=Codigo incorrecto";
        }
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(code).get();
        m.addAttribute("registro", usuario);
        return "emp/public/usuario_recuperar_password_form";
    }
      @PostMapping("/recuperar_password_guardar")
    public String guardarPassword(String codigoVerificacion, String password) {
        UsuarioEntity usuario = repoUsuario.findByCodigoVerificacion(codigoVerificacion).get();
        usuario.setPassword(passwordEncoder.encode(password));
        repoUsuario.save(usuario);
        return "emp/public/usuario_recuperar_password_cambiada_show";
    }
    

    @Autowired
    private ITecnicoRepository repoTecnico;
    
    @GetMapping("/init")
    public String init() {
        if (repoUsuario.countByRolAndEstado("ROLE_EMP_Tecnico", "Aceptado")==0){
            return "emp/public/usuario_init";
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }

    @PostMapping("/init")
    public String init(String usuario, String password, String email) {
        if (repoUsuario.countByRolAndEstado("ROLE_EMP_Tecnico", "Aceptado")==0){
            UsuarioEntity user=new UsuarioEntity();
            user.setUsuario(usuario);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRol("ROLE_EMP_Tecnico");
            user.setCodigoVerificacion(UUID.randomUUID().toString());
            user.setEstado("Aceptado");
            repoUsuario.save(user);
            
            TecnicoEntity tecnico=new TecnicoEntity();
            tecnico.setNombre(usuario);
            tecnico.setEmail(email);
            tecnico.setIdUsuario(user.getId());
            repoTecnico.save(tecnico);
        }
        return "redirect:login";    //Indicar al usuario que se verificarán los datos.
    }    
}