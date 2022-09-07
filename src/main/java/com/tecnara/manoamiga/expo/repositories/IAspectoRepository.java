package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.AspectoEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAspectoRepository extends JpaRepository<AspectoEntity, Long> {

    @Query(value = "SELECT *\n"
            + "FROM expo_aspectos asp\n"
            + "WHERE  IFNULL(fecha_inicio,'1900-01-01')<=CURDATE()\n"
            + "AND IFNULL(fecha_fin,'2200-01-01')>=CURDATE()\n"
            + "LIMIT 1", nativeQuery = true)
    public Optional<AspectoEntity> aspecto();
    
    

}
