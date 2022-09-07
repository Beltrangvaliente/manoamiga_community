/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnara.manoamiga.expo.services;

import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.expo.dto.AgendaDiaDto;
import com.tecnara.manoamiga.expo.dto.EventoDto;
import com.tecnara.manoamiga.expo.entities.AspectoEntity;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.EventoEntity;
import com.tecnara.manoamiga.expo.entities.ImagenDecorativaEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import com.tecnara.manoamiga.expo.repositories.IAspectoRepository;
import com.tecnara.manoamiga.expo.repositories.IEspacioRepository;
import com.tecnara.manoamiga.expo.repositories.IEventoRepository;
import com.tecnara.manoamiga.expo.repositories.IImagenDecorativaRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.plaf.synth.ColorType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosService implements IEventosService {

    @Autowired
    private IGeneral general;

    @Autowired
    private IEventoRepository repoEvento;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IEspacioRepository repoEspacio;

    @Autowired
    private IImagenDecorativaRepository repoImagenDecorativa;

    @Autowired
    private IAreaRepository repoArea;

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

    @Override
    public List<AgendaDiaDto> getAgenda() {
        List<EventoDto> listaEventos = repoEvento.findByEstadoAndFechaActividadBetweenOrderByHora("Aprobado", general.getFechaCercana(-35), general.getFechaCercana(35))
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());

        return convertData(listaEventos);
    }

    public List<AgendaDiaDto> convertData(List<EventoDto> eventos) {
        
        List<ImagenDecorativaEntity> imagenes=repoImagenDecorativa.publicAgendaImagenSinFecha();
        int imagen=0;
        
        List<AgendaDiaDto> res = new ArrayList<>();
        Date d = general.getPrimerDiaMes();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        while (c.get(Calendar.DAY_OF_WEEK) != 1) {
            c.add(Calendar.DATE, -1);
        }
        
        //Pasar por todos los dias
        for (int n = 0; n < 6 * 7; n++){
            if (c.get(Calendar.DAY_OF_WEEK) > 1 && c.get(Calendar.DAY_OF_WEEK) < 7) {  
                AgendaDiaDto dia = new AgendaDiaDto();
                dia.setFecha(c.getTime()); 
                dia.setDiaSemana(general.getDiaSemana(c.get(Calendar.DAY_OF_WEEK)-1));
                dia.setEventos(eventos.stream()
                        .filter(x -> x.getFechaActividad().equals(c.getTime()))
                        .sorted((x, y) -> x.getHora().compareTo(y.getHora()))
                        .collect(Collectors.toList())
                );
                if (dia.getEventos().size() == 0 && imagenes.size()>0) {
                    
                    Optional<ImagenDecorativaEntity> imagenFecha=repoImagenDecorativa.findByFechaImagen(c.getTime());
                    if (imagenFecha.isPresent()){
                        dia.setIdImagen(imagenFecha.get().getIdImagen());
                    }else{
                        dia.setIdImagen(imagenes.get(imagen).getIdImagen());
                        imagen=(imagen+1)%imagenes.size();
                    }
                }else if (dia.getEventos().size() == 0){
                    Optional<ImagenDecorativaEntity> imagenFecha=repoImagenDecorativa.findByFechaImagen(c.getTime());
                    if (imagenFecha.isPresent()){
                        dia.setIdImagen(imagenFecha.get().getIdImagen());
                    }
                }
                res.add(dia);
            }
            c.add(Calendar.DATE, 1);
        }
        
        return res;
    }

    /*public List<AgendaDiaDto> getAgenda() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
    @Autowired
    private IAspectoRepository repoAspecto;
    
    @Autowired
    private IAspectoRepository aspecto;

    @Override
    public AspectoEntity getAspecto() {
        Optional<AspectoEntity> optAsp = repoAspecto.aspecto();
        if (optAsp.isPresent()) {
            return optAsp.get();
        }else {
            AspectoEntity aspecto = new AspectoEntity();
            aspecto.setColorFondo("#FFF");
            aspecto.setColorTexto("#000");
            aspecto.setColorTitulosFondo("#DDD");
            aspecto.setColorTitulosLetra("#000");
            aspecto.setTipoLetra("Poppins");
            return aspecto;
        }
        
        
    }

}
