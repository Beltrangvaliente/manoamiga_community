/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.expo.services;

import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import java.util.function.BiConsumer;
import org.springframework.web.multipart.MultipartFile;


public interface INoticiaAjusteService {
    
    public void reajustarNoticia(NoticiaEntity noticia);
    
    public void cargarImagen(NoticiaEntity noticia, MultipartFile file, BiConsumer<NoticiaEntity, Long> setIdImagen);
    public void cargarImagen(NoticiaEntity noticia, String file, BiConsumer<NoticiaEntity, Long> setIdImagen);

    public NoticiaEntity crearNoticiaInicio();
}
