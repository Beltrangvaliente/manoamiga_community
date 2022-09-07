
package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.ImagenDecorativaEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IImagenDecorativaRepository extends JpaRepository<ImagenDecorativaEntity, Long>{

    public Optional<ImagenDecorativaEntity> findByFechaImagen(Date time);
    
    @Query(value = "SELECT *\n"
            + "FROM expo_imagenes_decorativas\n"
            + "WHERE fecha_imagen IS  NULL", nativeQuery = true)
    public List <ImagenDecorativaEntity> publicAgendaImagenSinFecha();
    
}
