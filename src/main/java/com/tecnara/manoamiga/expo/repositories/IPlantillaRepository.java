
package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.PlantillaEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IPlantillaRepository extends JpaRepository<PlantillaEntity, Long>{

 public List<PlantillaEntity>findByNombreContainingIgnoreCase(String filtro);
 
 @Query(value = "SELECT id\n" +
"FROM expo_plantillas\n" +
"ORDER BY numero_imagenes * 2 + numero_textos DESC\n" +
"LIMIT 1", nativeQuery = true)
 public Long coordinadorPlantillaInicial();
 
  @Query(value = "SELECT id\n" +
"FROM expo_plantillas\n" +
"ORDER BY numero_imagenes * 2 + numero_textos DESC\n" +
"LIMIT 1", nativeQuery = true)
 public Long trabajadorPlantillaInicial();
 
  @Query(value = "SELECT numero_imagenes , numero_textos, COUNT(1)\n" +
"FROM expo_plantillas\n" +
"GROUP BY numero_imagenes, numero_textos", nativeQuery = true)
 public Long contarImagenesTextos();
 
}
