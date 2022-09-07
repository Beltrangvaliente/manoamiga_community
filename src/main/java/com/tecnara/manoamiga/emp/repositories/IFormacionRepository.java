package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_formacion_repository")
public interface IFormacionRepository extends JpaRepository<FormacionEntity, Long> {

    @Query(value = "SELECT f.nombre nombre_curso,  e.nombre empresa, p.fecha_inicio, p.fecha_fin ,f.estado,p.nombre proyecto, f.id\n"
            + "FROM emp_formaciones f \n"
            + "LEFT JOIN emp_proyectos p ON(f.id_proyecto =p.id )\n"
            + "LEFT JOIN emp_empresas e ON(p.id_empresa =e.id )\n"
            + "INNER JOIN (SELECT CONCAT ('%', :filtro,'%') filtro , :fechaInicio fecha_inicio, :fechaFin fecha_fin, :idEmpresa id_empresa)params\n"
            + "WHERE (e.nombre LIKE params.filtro \n"
            + "OR p.nombre LIKE params.filtro )\n"
            + "AND (params.fecha_inicio IS NULL OR ifnull(p.fecha_inicio,'1900-01-01') >= params.fecha_fin)\n"
            + "AND (params.fecha_fin IS NULL OR ifnull(p.fecha_fin,'2200-01-01') <= params.fecha_inicio)\n"
            + "AND  (params.id_empresa IS NULL OR e.id = params.id_empresa) \n"
            + "ORDER BY p.fecha_inicio DESC ", nativeQuery = true)
    public List<Map> tecnicoList(String filtro, Date fechaInicio, Date fechaFin, Long idEmpresa);



    @Query(value = "SELECT fp.id,fp.id_fichero, fp.id_participante, CONCAT (p.nombre,' ',p.apellido1,' ',p.apellido2) Participante, p.telefono Telefono, p.email Email, fp.estado Estado\n"

            + "FROM emp_formaciones_participantes fp\n"
            + "INNER JOIN emp_participantes p ON (p.id = fp.id_participante)\n"
            + "INNER JOIN emp_formaciones f ON (f.id = fp.id_formacion)\n"
            + "INNER JOIN (SELECT :idFormacion id_formacion) params\n"
            + "WHERE f.id=params.id_formacion", nativeQuery = true)
    public List<Map> tecnicoFormParticipante(Long idFormacion);

    public List<FormacionEntity> findByNombreContaining(String filtro);

    @Query(value = "SELECT df.id, df.nombre_documento Documento, df.fecha_subido Fecha_subido, df.id_fichero \n"
            + "FROM emp_documentos_formaciones df\n"
            + "INNER JOIN (SELECT :idFormacion id_formacion) params\n"
            + "WHERE df.id_formacion=params.id_formacion", nativeQuery = true)
    public List<Map> tecnicoFormDocumentosFormaciones(Long idFormacion);

    @Modifying
    @Query(value = "UPDATE emp_formaciones SET estado='Activo' \n"
            + "WHERE id=:id\n"
            + "AND fecha_inicio <= CURDATE()", nativeQuery = true)
    public void iniciarCurso(Long id);

    @Modifying
    @Query(value = "UPDATE emp_formaciones SET estado='Finalizado' \n"
            + "WHERE id= :id", nativeQuery = true)
    public void finalizarCurso(Long id);
}
