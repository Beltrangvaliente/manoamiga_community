package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.expo.dto.NoticiaDto;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.EventoEntity;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.expo.dto.PlantillaDto;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import com.tecnara.manoamiga.expo.entities.PlantillaEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import com.tecnara.manoamiga.expo.repositories.IEspacioRepository;
import com.tecnara.manoamiga.expo.repositories.IEventoRepository;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;

import com.tecnara.manoamiga.expo.repositories.INoticiaRepository;
import com.tecnara.manoamiga.expo.repositories.IPlantillaRepository;
import com.tecnara.manoamiga.expo.repositories.ITrabajadorRepository;
import com.tecnara.manoamiga.expo.services.INoticiaAjusteService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.catalina.mapper.Mapper;
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
@RequestMapping("expo/trabajador/noticia")
public class TrabajadorNoticiaController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private INoticiaRepository repoNoticia;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IImagenRepository repoImagen;

    @Autowired
    private INoticiaAjusteService noticiaAjuste;

    @Autowired
    private IEspacioRepository repoEspacio;

    @Autowired
    private IAreaRepository repoArea;

    @Autowired
    private IEventoRepository repoEvento;

    @Autowired
    private IPlantillaRepository repoPlantilla;
    
    @Autowired
    private ITrabajadorRepository repoTrabajador;

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/trabajador/noticia_list";
    }

    @GetMapping("/list_data")
    @ResponseBody // para que devuelva un JSON
    public List<NoticiaDto> listData(String filtro) {
        return repoNoticia.findByTituloContaining(filtro).stream().map(x -> convertDto(x)).collect(Collectors.toList()); // nos devuelve todos los datos de la tabla; sin filtrar
    }

    public NoticiaDto convertDto(NoticiaEntity origen) {
        NoticiaDto nuevo = mapper.map(origen, NoticiaDto.class); // nos devuelve todos los datos de la tabla; sin filtrar
        if (origen.getIdArea() != null) {
            AreaEntity area = repoArea.findById(origen.getIdArea()).orElse(new AreaEntity());
            nuevo.setArea(area.getArea());

        }
        if (nuevo.getIdPlantilla() != null) {
            PlantillaEntity plantilla = repoPlantilla.findById(nuevo.getIdPlantilla()).orElse(new PlantillaEntity());
            nuevo.setPlantilla(mapper.map(plantilla, PlantillaDto.class));
        }

        if (origen.getIdEspacio() != null) {

            EspacioEntity espacio = repoEspacio.findById(origen.getIdEspacio()).orElse(new EspacioEntity());
            nuevo.setUbicacion(espacio.getUbicacion());
        }

        return nuevo;
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            NoticiaEntity not=new NoticiaEntity();
            not.setIdPlantilla(repoPlantilla.trabajadorPlantillaInicial());
            m.addAttribute("registro", not);
             
        } else {
            m.addAttribute("registro", repoNoticia.findById(id).get());
        }
        return "expo/trabajador/noticia_form";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoNoticia.deleteById(id);
        return "redirect:list";
    }

    public byte[] convertToImage(String base64) {
        try {
            byte[] decodedString = Base64.getDecoder().decode(base64.getBytes("UTF-8"));
            return decodedString;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/guardar")
    public String guardar(NoticiaEntity registro, MultipartFile imagen1,
            MultipartFile imagen2, MultipartFile imagen3, String img1, String img2, String img3) {

        noticiaAjuste.cargarImagen(registro, imagen1, (not, id) -> not.setIdImagen1(id));
        noticiaAjuste.cargarImagen(registro, imagen2, (not, id) -> not.setIdImagen2(id));
        noticiaAjuste.cargarImagen(registro, imagen3, (not, id) -> not.setIdImagen3(id));
        noticiaAjuste.cargarImagen(registro, img1, (not, id) -> not.setIdImagen1(id));
        noticiaAjuste.cargarImagen(registro, img2, (not, id) -> not.setIdImagen2(id));
        noticiaAjuste.cargarImagen(registro, img3, (not, id) -> not.setIdImagen3(id));
        noticiaAjuste.reajustarNoticia(registro);

        registro.setIdTrabajador(general.getIdValidado());
        
        //Buscar el trabajador. 
        Optional<TrabajadorEntity> tra=repoTrabajador.findById(general.getIdValidado());
        
        //Si existe 
        if( tra.isPresent() ){
            //rellenar el area setIdArea. registro.setIdArea(repoArea);
            registro.setIdArea( tra.get().getIdArea());
        }
        
        registro.setEstado("Pendiente");
        repoNoticia.save(registro);
        return "redirect:list";
    }

    @PostMapping("/guardar_preview")
    public String guardar_preview(NoticiaEntity registro, MultipartFile imagen1, MultipartFile imagen2, MultipartFile imagen3, String img1, String img2, String img3) {

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

    
    @GetMapping("/info_plantilla")
    @ResponseBody
    public Optional<Map> infoPlantilla(Long idPlantilla) {
        return repoNoticia.trabajadorFormInfoPlantilla(idPlantilla);
    }    
}
