package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.EmpresaEntity;
import com.tecnara.manoamiga.emp.entities.FormacionParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_formacion_participante")

public interface IFormacionParticipanteRepository extends JpaRepository<FormacionParticipanteEntity, Long> {

    public void save(FormacionEntity registro);

    // @Query(value ="", nativeQuery = true) 
    // public List<Map> tecnicoFormFormacionParticipante(idParticipante);
    @Query(value = "SELECT DISTINCT CONCAT(p.nombre, ' ', p.apellido1, ' ', p.apellido2) Persona, p.id\n"
            + "            FROM emp_participantes p\n"
            + "           INNER JOIN emp_proyectos_participantes pro_par ON (p.id = pro_par.id_participante)\n"
            + "            INNER JOIN emp_proyectos pro ON (pro.id=pro_par.id_proyecto)\n"
            + "            INNER JOIN emp_formaciones form ON (form.id_proyecto = pro.id)\n"
            + "            LEFT JOIN emp_formaciones_participantes fp ON (fp.id_participante = p.id AND fp.id_formacion=form.id)\n"
            + "            INNER JOIN (SELECT :idFormacion id_formacion) params\n"
            + "            WHERE form.id=params.id_formacion\n"
            + "            AND fp.id IS NULL", nativeQuery = true)
    public List<Map> listProyecto(Long idFormacion);

    @Query(value = "SELECT DISTINCT fp.id, CONCAT(IFNULL(p.nombre, ' '), ' ',IFNULL(p.apellido1, ' '), ' ',IFNULL (p.apellido2, ' ')) Persona\n"
            + "FROM emp_participantes p\n"
            + "INNER JOIN emp_formaciones_participantes fp ON (fp.id_participante = p.id)\n"
            + "INNER JOIN (SELECT :idFormacion id_formacion) params\n"
            + "WHERE fp.id_formacion=params.id_formacion", nativeQuery = true)
    public List<Map> listFormacion(Long idFormacion);

    @Modifying
    @Query(value = "INSERT INTO emp_formaciones_participantes (id_formacion, id_participante, estado ) VALUES (:idFormacion, :idParticipante, 'Candidato')", nativeQuery = true)
    public void btnInscribir(Long idFormacion, Long idParticipante);


}
