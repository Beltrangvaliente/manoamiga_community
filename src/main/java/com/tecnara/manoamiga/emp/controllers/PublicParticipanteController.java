package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.emp.entities.ExperienciaEntity;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.entities.FormacionPreviaEntity;
import com.tecnara.manoamiga.emp.entities.InteresEntity;
import com.tecnara.manoamiga.emp.entities.ParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.repositories.IExperienciaRepository;
import com.tecnara.manoamiga.emp.repositories.IFormacionPreviaRepository;
import com.tecnara.manoamiga.emp.repositories.IInteresRepository;
import com.tecnara.manoamiga.emp.repositories.IParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("emp_public_controller")
@RequestMapping("/emp/public/participante")
public class PublicParticipanteController extends TecnaraBaseController {

    @Autowired
    private IParticipanteRepository repoParticipante;

    @Autowired
    private IExperienciaRepository repoExperiencia;

    @Autowired
    private IFormacionPreviaRepository repoFormacion;

    @Autowired
    private IInteresRepository repoInteres;

    @Autowired
    private IEmailService servicioEmail;

    @Autowired
    private ITecnicoRepository repoTecnico;

    @GetMapping("/form_paso1")
    public String paso1(Model m) {
        m.addAttribute("texto_legal", general.getPreferencia("public_registro_texto_legal"));
        return "emp/public/participante_form_paso1";
    }

    @PostMapping("/form_paso1_guardar")
    public String paso1(ParticipanteEntity participante, String nombre, String apellido1, String apellido2, String email, String telefono, String password) {
        participante.setCodigoVerificacion(UUID.randomUUID().toString());
        repoParticipante.save(participante);

        List<TecnicoEntity> tecnicos = repoTecnico.enviarcorreo();

        tecnicos.forEach(tecnico -> {
            EmailInfoDto info = new EmailInfoDto();
            info.setNombre(participante.getNombre());
            info.setApellido1(participante.getApellido1());
            info.setApellido2(participante.getApellido2());
            info.setTituloEmail("Nuevo registro de usuario");
            info.setFicheroEmail("emp/usuario_registro_correo_tecnico");
            info.setEmail(tecnico.getEmail());
            info.setEmail2(participante.getEmail());
            servicioEmail.enviarEmail(info);
        });

        return "redirect:form_paso2?codigoVerificacion=" + participante.getCodigoVerificacion();
    }

    @GetMapping("/form_paso2")
    public String paso2(Model m, String codigoVerificacion) {
        m.addAttribute("codigoVerificacion", codigoVerificacion);
        return "emp/public/participante_form_paso2";
    }

    @GetMapping("/form_paso2_list_data")
    @ResponseBody
    public List<Map> paso2ListData(String codigoVerificacion) {
        return repoParticipante.publicFormPaso2(codigoVerificacion);
    }

    @GetMapping("/form_paso2_guardar")
    @ResponseBody
    public String paso2Guardar(ExperienciaEntity exp, String codigoVerificacion) {
        ParticipanteEntity p = repoParticipante.findByCodigoVerificacion(codigoVerificacion).get();
        exp.setIdParticipante(p.getId());
        repoExperiencia.save(exp);
        return "ok";
    }

    @GetMapping("/form_paso2_borrar")
    @ResponseBody
    public String paso2Borrar(String codigoVerificacion, Long id) {
        repoParticipante.publicFormPaso2Borrar(codigoVerificacion, id);
        return "ok";
    }

    @GetMapping("/form_paso3")
    public String paso3(Model m, String codigoVerificacion) {
        m.addAttribute("codigoVerificacion", codigoVerificacion);
        return "emp/public/participante_form_paso3";
    }

    @GetMapping("/form_paso3_list_data")
    @ResponseBody
    public List<Map> paso3ListData(String codigoVerificacion) {
        return repoParticipante.publicFormPaso3(codigoVerificacion);
    }

    @GetMapping("/form_paso3_guardar")
    @ResponseBody
    public String paso3Guardar(FormacionPreviaEntity f, String codigoVerificacion) {
        ParticipanteEntity p = repoParticipante.findByCodigoVerificacion(codigoVerificacion).get();
        f.setIdParticipante(p.getId());
        repoFormacion.save(f);
        return "ok";
    }

    @GetMapping("/form_paso3_borrar")
    @ResponseBody
    public String paso3Borrar(String codigoVerificacion, Long id) {
        repoParticipante.publicFormPaso3Borrar(codigoVerificacion, id);
        return "ok";
    }

    @GetMapping("/form_paso4")
    public String paso4(Model m, String codigoVerificacion) {
        m.addAttribute("codigoVerificacion", codigoVerificacion);
        return "emp/public/participante_form_paso4";
    }

    @GetMapping("/form_paso4_list_data")
    @ResponseBody
    public List<Map> paso4ListData(String codigoVerificacion) {
        return repoParticipante.publicFormPaso4(codigoVerificacion);
    }

    @GetMapping("/form_paso4_guardar")
    @ResponseBody
    public String paso4Guardar(InteresEntity i, String codigoVerificacion) {
        ParticipanteEntity p = repoParticipante.findByCodigoVerificacion(codigoVerificacion).get();
        i.setIdParticipante(p.getId());
        repoInteres.save(i);
        return "ok";
    }

    @GetMapping("/form_paso4_borrar")
    @ResponseBody
    public String paso4Borrar(String codigoVerificacion, Long id) {
        repoParticipante.publicFormPaso4Borrar(codigoVerificacion, id);
        return "ok";
    }

    @GetMapping("/confirmacion")
    public String confirm(Model m, String codigoVerificacion) {
        ParticipanteEntity p = repoParticipante.findByCodigoVerificacion(codigoVerificacion).get();
        m.addAttribute("codigoVerificacion", codigoVerificacion);
        m.addAttribute("registro", p);
        return "emp/public/confirmacion";
    }

    @GetMapping("/list")
    public String list() {
        return "emp/public/participante_list";
    }
}
