package com.tecnara.manoamiga.expo.services;

import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.entities.NoticiaEntity;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.function.BiConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiaAjusteService implements INoticiaAjusteService {

    @Override
    public void reajustarNoticia(NoticiaEntity noticia) {
        if (noticia.getContenido2() == null) {
            noticia.setContenido2(noticia.getContenido3());
        }
        if (noticia.getContenido1() == null) {
            noticia.setContenido1(noticia.getContenido2());
        }
        if (noticia.getIdImagen2() == null) {
            noticia.setIdImagen2(noticia.getIdImagen3());
        }
        if (noticia.getIdImagen1() == null) {
            noticia.setIdImagen1(noticia.getIdImagen2());
        }
    }

    @Autowired
    private IImagenRepository repoImagen;

    @Override
    public void cargarImagen(NoticiaEntity noticia, MultipartFile file, BiConsumer<NoticiaEntity, Long> setIdImagen) {
        if (file.isEmpty() == false && file.isEmpty()==false) {
            try {
                ImagenEntity nueva = new ImagenEntity();
                nueva.setImagen(file.getBytes());
                ImagenEntity guardado = repoImagen.save(nueva);
                Long id = guardado.getId();
                setIdImagen.accept(noticia, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public NoticiaEntity crearNoticiaInicio() {
        NoticiaEntity noticia = new NoticiaEntity();
        noticia.setTitulo("Introduzca contenido o imágenes para ver la noticia");
        noticia.setContenido1("");
        noticia.setContenido2("");
        noticia.setContenido3("");
        return noticia;
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
    
    @Override
    public void cargarImagen(NoticiaEntity noticia, String img, BiConsumer<NoticiaEntity, Long> setIdImagen) {
        System.out.println("Entra " + img);
         if (img!=null && img.length()>10) {
            System.out.println("Entra 2" + img);
            img = img.replace("data:image/jpeg;base64,", "");
            img = img.replace("data:image/png;base64,", "");
            ImagenEntity nueva = new ImagenEntity();
            nueva.setImagen(convertToImage(img));
            ImagenEntity guardado = repoImagen.save(nueva);
            Long id = guardado.getId();
            setIdImagen.accept(noticia, id);
        }   
    }

}
