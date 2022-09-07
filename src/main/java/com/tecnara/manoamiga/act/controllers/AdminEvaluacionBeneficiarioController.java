package com.tecnara.manoamiga.act.controllers;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/evaluacion_beneficiario")

public class AdminEvaluacionBeneficiarioController extends TecnaraBaseController{

    @Autowired
    private IEvaluacionBeneficiarioRepository repoEvaluacion_beneficiario;

    @GetMapping("/form")
    public String form(Model m) {
        m.addAttribute("registro", new EvaluacionBeneficiarioEntity());
        return "act/admin/evaluacion_beneficiario_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/evaluacion_beneficiario_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<EvaluacionBeneficiarioEntity> listData() {
        return repoEvaluacion_beneficiario.findAll();
    }

    @PostMapping("/guardar")
    public String guardar(EvaluacionBeneficiarioEntity registro) {
        repoEvaluacion_beneficiario.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoEvaluacion_beneficiario.deleteById(id);
        return "redirect:list";

    }
    /*@GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto>buscador(String filtro){
        return repoEvaluacion_beneficiario.findByCamposContainingIgnoreCase(filtro, filtro)
                .stream()
                .map(x->new ClaveValorDto(x.getIdActividad(),x.getIdBeneficiario()))
    }*/
}
