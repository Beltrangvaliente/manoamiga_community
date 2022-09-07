package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.ExperienciaEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

//falta la consulta
@Repository("emp_experiencia")
public interface IExperienciaRepository extends JpaRepository<ExperienciaEntity, Long> {

   // @Query(value = " ", nativeQuery = true)
   // public List<Map> listAceptarExperiencia(Long idParticipante);

}
