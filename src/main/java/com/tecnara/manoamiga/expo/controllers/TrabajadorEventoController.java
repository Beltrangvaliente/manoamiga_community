
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.dto.EventoDto;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.EventoEntity;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import com.tecnara.manoamiga.expo.repositories.IEspacioRepository;
import com.tecnara.manoamiga.expo.repositories.IEventoRepository;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import com.tecnara.manoamiga.expo.repositories.ITrabajadorRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
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
@RequestMapping("/expo/trabajador/evento")
public class TrabajadorEventoController extends TecnaraBaseController{

    @Autowired
    private IEventoRepository repoEvento;

    @Autowired
    private IImagenRepository repoImagen;

    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private IGeneral general;
    
    @Autowired
    private IAreaRepository repoArea;
    
    @Autowired
    private IEspacioRepository repoEspacio;

    @Autowired
    private ITrabajadorRepository repoTrabajador;

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/trabajador/evento_list";
    }    

    public EventoDto convertDto(EventoEntity origen){
      
        EventoDto nuevo=mapper.map(origen, EventoDto.class);
        
        if (origen.getIdArea()!=null){
            AreaEntity area=repoArea.findById(origen.getIdArea()).orElse(new AreaEntity());
            nuevo.setArea(area.getArea());
        }

        if (origen.getIdEspacio()!=null){
            EspacioEntity espacio=repoEspacio.findById(origen.getIdEspacio()).orElse(new EspacioEntity());
            nuevo.setUbicacion(espacio.getUbicacion());
        }

        if (origen.getIdTrabajador()!=null){
            TrabajadorEntity trabajador=repoTrabajador.findById(origen.getIdTrabajador()).orElse(new TrabajadorEntity());
            nuevo.setNombreTrabajador(trabajador.getNombreTrabajador());
        }

        return nuevo;
    }    
    
    @GetMapping("/list_data")
    @ResponseBody // para que devuelva un JSON
    public List<EventoDto> listData() {
        return repoEvento.findByIdTrabajadorOrderByFechaActividadDesc(general.getIdValidado()).stream()
                                   .map(x->convertDto(x))
                                    .collect(Collectors.toList()); // nos devuelve todos los datos de la tabla; sin filtrar
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new EventoEntity());
        } else {
            m.addAttribute("registro", repoEvento.findById(id).get());
        }
        return "expo/trabajador/evento_form";
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
    public String guardar(EventoEntity registro, MultipartFile imagen, String img) {
        if (imagen!=null && imagen.isEmpty()==false) {
            try {
                ImagenEntity nueva = new ImagenEntity();
                nueva.setImagen(imagen.getBytes());
                ImagenEntity guardado = repoImagen.save(nueva);
                Long id = guardado.getId();
                registro.setIdImagen(id);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:form";
            }
        }
        if (img.length()>0){
            img = img.replace("data:image/jpeg;base64,", "");  
            img = img.replace("data:image/png;base64,", "");  
            ImagenEntity nueva=new ImagenEntity();
            nueva.setImagen(convertToImage(img));
            ImagenEntity guardado = repoImagen.save(nueva);
            repoImagen.flush();  
            Long id = guardado.getId();
            registro.setIdImagen(id);         
        }
        
        
        registro.setIdTrabajador(general.getIdValidado());
        registro.setIdArea(general.getIdValidado()); 
        registro.setEstado("Pendiente");
        //Buscar el area del trabajador y rellenar el ID del area
        
        repoEvento.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoEvento.deleteById(id);
        return "redirect:list";
    }

}
