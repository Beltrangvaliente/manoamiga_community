 
package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.ServicioEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IServicioRepository extends JpaRepository<ServicioEntity, Long> {
    @Query(value="  SELECT  s.id, s.activo, s.nombre, s.pantalla, s.id_imagen\n" +
"FROM expo_servicios s", nativeQuery = true)
    public List<Map> leerListaCoordinador();
    
 @Query(value="SELECT *\n" +
" FROM expo_servicios ser\n" +
" INNER JOIN (SELECT :activo activo, :pantalla pantalla ) params\n" +
" WHERE ( ser.activo= params.activo)" +
" AND (ser.pantalla='todas' OR ser.pantalla=params.pantalla)\n" +
" \n" +
" ", nativeQuery = true)
  public List<ServicioEntity> leerServicios(String activo, Integer pantalla);
}
