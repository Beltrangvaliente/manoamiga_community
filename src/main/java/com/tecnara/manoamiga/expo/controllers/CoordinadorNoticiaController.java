package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.expo.dto.NoticiaDto;
import com.tecnara.manoamiga.expo.dto.PlantillaDto;
import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import com.tecnara.manoamiga.expo.entities.PlantillaEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.params.CoordinadorNoticiaListParam;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import com.tecnara.manoamiga.expo.repositories.INoticiaRepository;
import com.tecnara.manoamiga.expo.repositories.IPlantillaRepository;
import com.tecnara.manoamiga.expo.repositories.ITrabajadorRepository;
import com.tecnara.manoamiga.expo.services.INoticiaAjusteService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/expo/coordinador/noticia")
public class CoordinadorNoticiaController extends TecnaraBaseController {

    @Autowired
    private INoticiaRepository repoNoticia;

    @Autowired
    private IImagenRepository repoImagen;

    @Autowired
    private INoticiaAjusteService noticiaAjuste;

    @Autowired
    private IPlantillaRepository repoPlantilla;

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/coordinador/noticia_list";
    }

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ITrabajadorRepository repoTrabajador;
    
    

    public NoticiaDto convertDto(NoticiaEntity origen) {
        NoticiaDto nuevo = mapper.map(origen, NoticiaDto.class);
        if (origen.getIdTrabajador() != null) {
            TrabajadorEntity trabajador = repoTrabajador.findById(origen.getIdTrabajador()).orElse(new TrabajadorEntity());
            nuevo.setNombreTrabajador(trabajador.getNombreTrabajador());
        }
        if (nuevo.getIdPlantilla()!=null){
            PlantillaEntity plantilla=repoPlantilla.findById(nuevo.getIdPlantilla()).orElse(new PlantillaEntity());
            nuevo.setPlantilla(mapper.map(plantilla, PlantillaDto.class));
        }
        return nuevo;
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            NoticiaEntity not=new NoticiaEntity();
            not.setIdPlantilla(repoPlantilla.coordinadorPlantillaInicial());
            m.addAttribute("registro", not);
           
        } else {
            m.addAttribute("registro", repoNoticia.findById(id).get());
        }
        return "expo/coordinador/noticia_form";
    }

    @GetMapping("/list_data")
    @ResponseBody     //JSON
    public List<NoticiaDto> list_data(CoordinadorNoticiaListParam params) {
        return repoNoticia.coordinadorList(params.getEstado(),params.getFiltro(),params.getFechaDesde(),params.getFechaHasta()).stream().map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @PostMapping("/guardar")
    public String guardar(NoticiaEntity registro, MultipartFile imagen1, MultipartFile imagen2, MultipartFile imagen3,
                            String img1, String img2, String img3) {
        noticiaAjuste.cargarImagen(registro, imagen1, (not, id) -> not.setIdImagen1(id));
        noticiaAjuste.cargarImagen(registro, imagen2, (not, id) -> not.setIdImagen2(id));
        noticiaAjuste.cargarImagen(registro, imagen3, (not, id) -> not.setIdImagen3(id));
        noticiaAjuste.cargarImagen(registro, img1, (not, id) -> not.setIdImagen1(id));
        noticiaAjuste.cargarImagen(registro, img2, (not, id) -> not.setIdImagen2(id));
        noticiaAjuste.cargarImagen(registro, img3, (not, id) -> not.setIdImagen3(id));
        noticiaAjuste.reajustarNoticia(registro);
        repoNoticia.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoNoticia.deleteById(id);
        return "redirect:list";

    }

    @PostMapping("/guardar_preview")
    public String guardar_preview(NoticiaEntity registro, MultipartFile imagen1, MultipartFile imagen2, MultipartFile imagen3, 
                                  String img1, String img2, String img3) {
        noticiaAjuste.cargarImagen(registro, imagen1, (not, id) -> not.setIdImagen1(id));
        noticiaAjuste.cargarImagen(registro, imagen2, (not, id) -> not.setIdImagen2(id));
        noticiaAjuste.cargarImagen(registro, imagen3, (not, id) -> not.setIdImagen3(id));
        noticiaAjuste.cargarImagen(registro, img1, (not, id) -> not.setIdImagen1(id));
        noticiaAjuste.cargarImagen(registro, img2, (not, id) -> not.setIdImagen2(id));
        noticiaAjuste.cargarImagen(registro, img3, (not, id) -> not.setIdImagen3(id));
        noticiaAjuste.reajustarNoticia(registro);
        NoticiaEntity almacenado = repoNoticia.save(registro);
        return "redirect:form?id=" + almacenado.getId();
    }

    @GetMapping("/preview")
    public String preview(Model m, Long id) {
        List<NoticiaEntity> lista=repoNoticia.publicNoticiasPlantilla(id);
        
        if (lista.size() >0) {
            m.addAttribute("noticias", convertDto(lista.get(0)) );
        } else {

            m.addAttribute("noticias", List.of(noticiaAjuste.crearNoticiaInicio()));
        }
        return "expo/public/noticias";
    }

    @GetMapping("/aceptar")
    public String aceptar(Long id) {
        NoticiaEntity obj = repoNoticia.findById(id).get();
        obj.setEstado("Aprobado");
        repoNoticia.save(obj);
        return "redirect:list";
    }
    @GetMapping("/rechazar")
    public String rechazarNoticia(Long id) {
        NoticiaEntity obj=repoNoticia.findById(id).get();
        obj.setEstado("Rechazado");
        repoNoticia.save(obj);
        return "redirect:list";
    }
    
    @GetMapping("/info_plantilla")
    @ResponseBody
    public Optional<Map> infoPlantilla(Long idPlantilla) {
        return repoNoticia.coordinadorFormInfoPlantilla(idPlantilla);
    }

    
    
    
}
