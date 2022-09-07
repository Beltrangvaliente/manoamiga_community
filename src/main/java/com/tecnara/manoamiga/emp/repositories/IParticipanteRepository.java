package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.DocumentoParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.ParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("emp_participantes")
public interface IParticipanteRepository extends JpaRepository<ParticipanteEntity, Long> {

    public List<ParticipanteEntity> findByNombreContainingOrApellido1ContainingOrApellido2Containing(String nombre, String apellido1, String apellido2);

    @Query(value = "SELECT p.id,p.nombre,p.apellido1,p.apellido2,p.email,p.telefono,fecha_darde,p.contactado \n"
            + "FROM emp_participantes p\n"
            + "INNER JOIN (SELECT  CONCAT('%',:filtro,'%') filtro, :fechaDardeDesde fecha_darde_desde, :fechaDardeHasta fecha_darde_hasta)params\n"
            + "WHERE (p.nombre LIKE params.filtro OR p.apellido1 LIKE params.filtro \n"
            + "OR p.apellido2 LIKE params.filtro OR p.email LIKE params.filtro  OR p.telefono LIKE params.filtro )\n"
            + "AND (params.fecha_darde_desde  IS NULL OR params.fecha_darde_desde  <= p.fecha_darde )\n"
            + "AND (params.fecha_darde_hasta  IS NULL OR params.fecha_darde_hasta  >= p.fecha_darde )", nativeQuery = true)
    public List<Map> tecnicoList(String filtro, Date fechaDardeDesde, Date fechaDardeHasta);

    public Optional<ParticipanteEntity> findByCodigoVerificacion(String codigoVerificacion);

    @Query(value = "SELECT expe.*\n"
            + "FROM emp_experiencias expe\n"
            + "INNER JOIN emp_participantes par ON (expe.id_participante = par.id)\n"
            + "INNER JOIN (SELECT :codigoVerificacion codigo_verificacion) params\n"
            + "WHERE par.codigo_verificacion=params.codigo_verificacion", nativeQuery = true)
    public List<Map> publicFormPaso2(String codigoVerificacion);

    @Query(value = "SELECT form.*\n"
            + "FROM emp_formaciones_previas form\n"
            + "INNER JOIN emp_participantes par ON (form.id_participante = par.id)\n"
            + "INNER JOIN (SELECT :codigoVerificacion codigo_verificacion) params\n"
            + "WHERE par.codigo_verificacion=params.codigo_verificacion", nativeQuery = true)
    public List<Map> publicFormPaso3(String codigoVerificacion);

    @Query(value = "SELECT inte.*\n"
            + "FROM emp_intereses inte\n"
            + "INNER JOIN emp_participantes par ON (inte.id_participante = par.id)\n"
            + "INNER JOIN (SELECT :codigoVerificacion codigo_verificacion) params\n"
            + "WHERE par.codigo_verificacion=params.codigo_verificacion", nativeQuery = true)
    public List<Map> publicFormPaso4(String codigoVerificacion);

    @Query(value = "DELETE FROM emp_experiencias\n"
            + "WHERE id=:id\n"
            + "AND id_participante IN (\n"
            + "	SELECT id\n"
            + "	FROM emp_participantes\n"
            + "	WHERE codigo_verificacion=:codigoVerificacion\n"
            + ")     ", nativeQuery = true)
    @Modifying
    @Transactional
    public void publicFormPaso2Borrar(String codigoVerificacion, Long id);

    @Query(value = "DELETE FROM emp_formaciones_previas\n"
            + "WHERE id=:id\n"
            + "AND id_participante IN (\n"
            + "	SELECT id\n"
            + "	FROM emp_participantes\n"
            + "	WHERE codigo_verificacion=:codigoVerificacion\n"
            + ")   ", nativeQuery = true)
    @Modifying
    @Transactional
    public void publicFormPaso3Borrar(String codigoVerificacion, Long id);

    @Query(value = "DELETE FROM emp_intereses\n"
            + "WHERE id=:id\n"
            + "AND id_participante IN (\n"
            + "	SELECT id\n"
            + "	FROM emp_participantes\n"
            + "	WHERE codigo_verificacion=:codigoVerificacion\n"
            + ")     ", nativeQuery = true)
    @Modifying
    @Transactional
    public void publicFormPaso4Borrar(String codigoVerificacion, Long id);

    @Query(value = "SELECT expe.*\n"
            + "FROM emp_experiencias expe\n"
            + "INNER JOIN emp_participantes par ON (expe.id_participante = par.id)\n"
            + "INNER JOIN (SELECT :idParticipante id_participante) params\n"
            + "WHERE par.id=params.id_participante", nativeQuery = true)
    public List<Map> tecnicoFormExperiencias(Long idParticipante);

    @Query(value = "SELECT form.*\n"
            + "FROM emp_formaciones_previas form\n"
            + "INNER JOIN emp_participantes par ON (form.id_participante = par.id)\n"
            + "INNER JOIN (SELECT :idParticipante id_participante) params\n"
            + "WHERE par.id=params.id_participante", nativeQuery = true)
    public List<Map> tecnicoFormFormacionesPrevias(Long idParticipante);

    @Query(value = "SELECT f.nombre, f.estado, fp.id, fp.estado estado_alumno, fp.evaluacion, fp.id_fichero \n"
            + "FROM emp_formaciones_participantes fp\n"
            + "INNER JOIN emp_participantes p ON (p.id = fp.id_participante)\n"
            + "INNER JOIN emp_formaciones f ON (f.id = fp.id_formacion)\n"
            + "INNER JOIN (SELECT :idParticipante id_participante) params\n"
            + "WHERE p.id=params.id_participante", nativeQuery = true)
    public List<Map> tecnicoFormFormacionesParticipantes(Long idParticipante);

    @Query(value = "SELECT inte.*\n"
            + "FROM emp_intereses inte\n"
            + "INNER JOIN emp_participantes par ON (inte.id_participante = par.id)\n"
            + "INNER JOIN (SELECT :idParticipante id_participante) params\n"
            + "WHERE par.id=params.id_participante", nativeQuery = true)
    public List<Map> tecnicoFormIntereses(Long idParticipante);

    @Query(value = "  SELECT c.id,e.nombre,c.puesto,c.fecha_inicio\n"
            + "            FROM emp_contratos c \n"
            + "            INNER JOIN emp_empresas e ON (c.id_empresa = e.id)\n"
            + "            INNER JOIN (SELECT :idParticipante id_participante)params\n"
            + "            WHERE c.id_empresa = e.id           \n"
            + "             ",
            nativeQuery = true
    )
    public List<Map> tecnicoFormContratos(Long idParticipante);

    @Query(value = "SELECT dp.id,dp.fecha_subido,dp.id_documento,dp.id_participante,dp.nombre_documento\n"
            + "FROM emp_documentos_participantes dp\n"
            + "INNER JOIN (SELECT :idParticipante id_participante)params\n"
            + "WHERE dp.id_participante=params.id_participante ", nativeQuery = true)
    public List<Map> tecnicoFormDocumentosParticipantes(Long idParticipante);

    @Modifying

    @Query(value = "UPDATE emp_experiencias\n"
            + "SET verificado='si'\n"
            + "WHERE id=:id", nativeQuery = true)
    public void aceptarExperiencias(Long id);

    @Modifying

    @Query(value = "UPDATE emp_experiencias\n"
            + "SET verificado='no'\n"
            + "WHERE id=:id", nativeQuery = true)
    public void rechazarExperiencias(Long id);

    @Modifying

    @Query(value = "UPDATE emp_formaciones_previas\n"
            + "SET verificado='si'\n"
            + "WHERE id=:id", nativeQuery = true)
    public void aceptarFormacionesPrevias(Long id);

    @Modifying

    @Query(value = "UPDATE emp_formaciones_previas\n"
            + "SET verificado='no'\n"
            + "WHERE id=:id", nativeQuery = true)
    public void rechazarFormacionesPrevias(Long id);

    @Modifying

    @Query(value = "SELECT emp.id,CONCAT(emp.nombre,' ',emp.apellido1,' ',emp.apellido2)nombre,emp.fecha_darde, emp.email, emp.telefono,IFNULL(emp.contactado,'')contactado\n"
            + "FROM emp_participantes emp\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro, :fechaDardeDesde fechaDardeDesde, :fechaDardeHasta fechaDardeHasta, :contactado contactado)params \n"
            + "WHERE(emp.nombre LIKE params.filtro OR emp.apellido1 LIKE params.filtro OR emp.apellido2 LIKE params.filtro) \n"
            + "AND (params.fechaDardeDesde IS NULL OR emp.fecha_darde >= params.fechaDardeDesde) \n"
            + "AND (params.fechaDardeHasta IS NULL OR emp.fecha_darde <= params.fechaDardeHasta) \n"
            + "AND (params.contactado IS NULL OR params.contactado = '' OR IFNULL(emp.contactado,'') = params.contactado)", nativeQuery = true)
    public List<Map> tecnicoList(String filtro, Date fechaDardeDesde, Date fechaDardeHasta, String contactado);

}
