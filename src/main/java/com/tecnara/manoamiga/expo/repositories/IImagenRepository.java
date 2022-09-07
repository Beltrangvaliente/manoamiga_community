package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IImagenRepository extends JpaRepository<ImagenEntity, Long>{
    
}
