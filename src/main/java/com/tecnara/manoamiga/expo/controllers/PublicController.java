/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.expo.dto.AgendaDiaDto;
import com.tecnara.manoamiga.expo.dto.EventoDto;
import com.tecnara.manoamiga.expo.dto.NoticiaDto;
import com.tecnara.manoamiga.expo.dto.PlantillaDto;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.EventoEntity;
import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import com.tecnara.manoamiga.expo.entities.PlantillaEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import com.tecnara.manoamiga.expo.repositories.IEspacioRepository;
import com.tecnara.manoamiga.expo.repositories.IEventoRepository;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import com.tecnara.manoamiga.expo.repositories.INoticiaRepository;
import com.tecnara.manoamiga.expo.repositories.IPlantillaRepository;
import com.tecnara.manoamiga.expo.repositories.IServicioRepository;
import com.tecnara.manoamiga.expo.services.IEventosService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("public_expo")
@RequestMapping("/expo/public")
public class PublicController extends TecnaraBaseController {

    @Autowired
    private INoticiaRepository repoNoticias;
    @Autowired
    private IEventoRepository repoEvento;
    @Autowired
    private IImagenRepository repoImagen;
    @Autowired
    private IAreaRepository repoArea;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IServicioRepository repoServicio;
    @Autowired
    private IEspacioRepository repoEspacio;

    @Autowired
    private IPlantillaRepository repoPlantillas;

    @GetMapping("/index")
    public String index() {
        return "redirect:/expo/public/usuario/login";
    }

    @GetMapping("/menu_pantallas")
    public String pantallas() {
        return "expo/public/menu_pantallas";
    }

    public EventoDto convertDto(EventoEntity origen) {
        EventoDto nuevo = mapper.map(origen, EventoDto.class);
        if (origen.getIdEspacio() != null) {
            EspacioEntity espacio = repoEspacio.findById(origen.getIdEspacio()).orElse(new EspacioEntity());
            nuevo.setUbicacion(espacio.getUbicacion());
            //
        } else {
            nuevo.setUbicacion("");
        }

        if (origen.getIdArea() != null) {
            AreaEntity area = repoArea.findById(origen.getIdArea()).orElse(new AreaEntity());
            nuevo.setArea(area.getArea());
            nuevo.setColor(area.getColor());

            int cont = 0;

            if (area.getColor() != null && area.getColor().length() > 6) {
                if (area.getColor().charAt(1) > '8') {
                    cont++;
                } else {
                    cont--;
                }
                if (area.getColor().charAt(3) > '8') {
                    cont++;
                } else {
                    cont--;
                }
                if (area.getColor().charAt(5) > '8') {
                    cont++;
                } else {
                    cont--;
                }

                if (cont > 0) {
                    nuevo.setColorTexto("#000");
                } else {
                    nuevo.setColorTexto("#FFF");
                }
            } else {
                nuevo.setColorTexto("#000");
            }

        }
        return nuevo;
    }

    @Autowired
    private IGeneral general;

    public NoticiaDto convertDto(NoticiaEntity origen) {
        NoticiaDto nuevo = mapper.map(origen, NoticiaDto.class);
        if (nuevo.getIdPlantilla()!=null){
            PlantillaEntity plantilla=repoPlantillas.findById(nuevo.getIdPlantilla()).orElse(new PlantillaEntity());
            nuevo.setPlantilla(mapper.map(plantilla, PlantillaDto.class));
        }
        return nuevo;
    }    
    
    @GetMapping("/agenda")
    public String agenda(Model m) {
        List<AgendaDiaDto> agenda = new ArrayList<>();
        for (int n = 0; n < 7; n++) {
            Date fecha = general.getFechaCercana(n);
            AgendaDiaDto dia = new AgendaDiaDto();
            dia.setFecha(fecha);
            dia.setEventos(
                    repoEvento.findByEstadoAndFechaActividadOrderByHora("Aprobado", fecha)
                            .stream()
                            .map(x -> convertDto(x))
                            .collect(Collectors.toList())
            );
            agenda.add(dia);
        }
        System.out.println(general.getPrimerDiaMes() + " " + general.getUltimoDiaMes());

        m.addAttribute("hoy", agenda.get(0).getEventos());
        m.addAttribute("semanal", agenda);
        m.addAttribute("mes", eventos.getAgenda());
        List<NoticiaEntity> noticias = repoNoticias.findByPantallaOrPantalla("1", "Todas");
        m.addAttribute("noticias", noticias.stream().map(x->convertDto(x)).collect(Collectors.toList()));
        m.addAttribute("aspecto", eventos.getAspecto() ); 

        m.addAttribute("config_ancho", general.getPreferencia("pantalla_ancho", "1920"));
        m.addAttribute("config_alto", general.getPreferencia("pantalla_ancho", "1080"));


        if (noticias.stream().anyMatch(x -> "Si".equals(x.getFija()))) {
            m.addAttribute("noticias", noticias.stream()
                    .filter(x -> "Si".equals(x.getFija()))
                    .map(x->convertDto(x))
                    .collect(Collectors.toList())
            );
            m.addAttribute("hay_noticia_fija", "Si");

        }
        m.addAttribute("servicios", repoServicio.leerServicios("si" , 1));
        return "expo/public/agenda";

    }

    @GetMapping("/noticias")
    public String noticia(Model m) {
        List<NoticiaEntity> noticias = repoNoticias.findByPantallaOrPantalla("2", "Todas");
        

        m.addAttribute("noticias", noticias.stream().map(x->convertDto(x)).collect(Collectors.toList())  );
        m.addAttribute("aspecto", eventos.getAspecto() );
        
        if (noticias.stream().anyMatch(x -> "Si".equals(x.getFija()))) {
            m.addAttribute("noticias", noticias.stream()
                    .filter(x -> "Si".equals(x.getFija()))
                    .map(x->convertDto(x))
                    .collect(Collectors.toList())
            );
        }
        m.addAttribute("servicios", repoServicio.leerServicios("si" ,2));
       
        m.addAttribute("config_ancho", general.getPreferencia("pantalla_ancho", "1920"));
        m.addAttribute("config_alto", general.getPreferencia("pantalla_ancho", "1080"));
        
        return "expo/public/noticias";
    }
    
    @Autowired
    private IEventosService eventos;
    @Autowired
    private INoticiaRepository repoNoticia;

    @GetMapping("/agenda_v2")
    public String agenda2(Model m) {

        m.addAttribute("aspecto", eventos.getAspecto());
        m.addAttribute("mes", eventos.getAgenda());
        return "expo/public/agenda_v2";
    }

    @GetMapping("/plantilla")
    public String plantilla() {
        return "expo/public/plantilla";
    }

    @GetMapping("/multipantalla")
    public String multipantalla() {
        return "expo/public/multipantalla";
    }
}
