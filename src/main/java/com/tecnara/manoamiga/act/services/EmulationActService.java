package com.tecnara.manoamiga.act.services;

import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.AdministradorEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ClaseFormativaEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.entities.MemoriaEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IAdministradorRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IClaseFormativaRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IHorarioRepository;
import com.tecnara.manoamiga.act.repositories.IMemoriaRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.LeidoComentarioMonitorEntity;
import com.tecnara.manoamiga.act.repositories.ILeidoComentarioMonitorRepository;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmulationActService implements IEmulationActService {

    @Autowired
    private IGeneral general;
    
    private PasswordEncoder passEnc;

    @Override
    public void generarDatosDePrueba(PasswordEncoder passEnc) {
        this.passEnc=passEnc;
        try {
            if (repoActividad.count() > 0) {
                return; //No hacer nada
            }

            crear2Actividades();
            crear1Administrador();
            crear2Beneficiarios();
            crear2ClasesFormativas();
            crear6Comentarios();
            crear4ComentariosLeidos();
            crear2EvaluacionesBeneficiarios();
            crear2EvaluacionesMonitores();
            crear2FaltasDeAsistencia();
            crear2Horarios();
            crear2Memorias();
            crear2Monitores();
            crear2ParticipacionesBeneficiarios();
            crear2ParticipacionesMonitores();
            crear2Profesores();
            crear2Tutores();
            crear2Usuarios();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Autowired
    private IActividadRepository repoActividad;

    private void crear2Actividades() {
        ActividadEntity act1 = new ActividadEntity();
        act1.setId(1L);
        act1.setTipoActividad("Actividad Innovación Social");
        act1.setNombreActividad(
                "Desarrollo personal Scale Up");
        act1.setResponsableActividad("Javier");
        act1.setPlazaActividad(20);
        act1.setObservacion("Pendiente de confirmación"
        );
        act1.setLugarActividad("Colegio Mayor"
        );
        act1.setDireccion("Avda / Las Cantinas"
        );
        act1.setMail("colegiomayor@colegio.es");
        act1.setLocalidad("Madrid");
        act1.setNacionalidad("Marroquí");
        repoActividad.save(act1);

        ActividadEntity act2 = new ActividadEntity();
        act2.setId(2L);
        act2.setTipoActividad("Educación");
        act2.setNombreActividad("Escuela Solidaria");
        act2.setResponsableActividad("Mariola");
        act2.setPlazaActividad(15);
        act2.setObservacion("Cupo completo");
        act2.setLugarActividad("C.E.I.");
        act2.setDireccion("Calle Estudios");
        act2.setMail("estudios@director.com");
        act2.setLocalidad("Zaragoza");
        act2.setNacionalidad("Española");
        repoActividad.save(act2);

    }
    @Autowired
    private IAdministradorRepository repoAdministrador;

    private void crear1Administrador() {
        AdministradorEntity adm1 = new AdministradorEntity();
        adm1.setId(1L);
        adm1.setIdUsuario(1L);
        adm1.setNombre("Pedro");
        adm1.setApellido1("Herráiz");
        adm1.setApellido2("Bayod");
        adm1.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoAdministrador.save(adm1);
    }

    @Autowired
    private IClaseFormativaRepository repoClaseFor;

    private void crear2ClasesFormativas() {
        ClaseFormativaEntity clasefor1 = new ClaseFormativaEntity();
        clasefor1.setId(1L);
        clasefor1.setIdBeneficiario(1L);
        clasefor1.setIdProfesor(1L);
        clasefor1.setAsignatura("Matemáticas");
        repoClaseFor.save(clasefor1);

        ClaseFormativaEntity clasefor2 = new ClaseFormativaEntity();
        clasefor2.setId(1L);
        clasefor2.setIdBeneficiario(1L);
        clasefor2.setIdProfesor(1L);
        clasefor2.setAsignatura("Inglés");
        repoClaseFor.save(clasefor2);

    }

    @Autowired
    private IComentarioRepository repoComentario;

    private void crear6Comentarios() {
        ComentarioEntity comen1 = new ComentarioEntity();
        comen1.setId(1L);
        comen1.setTexto("No ha presentado la tarea indicada en la fecha propuesta");
        comen1.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        comen1.setHoraComentario("16:50");
        comen1.setIdBeneficiario(1L);
        comen1.setIdMonitor(1L);
        repoComentario.save(comen1);

        ComentarioEntity comen2 = new ComentarioEntity();
        comen2.setId(2L);
        comen2.setTexto("Ha realizado un buen entrenamiento");
        comen2.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        comen2.setHoraComentario("18:00");
        comen2.setIdBeneficiario(2L);
        comen2.setIdProfesor(2L);
        repoComentario.save(comen2);

        ComentarioEntity comen3 = new ComentarioEntity();
        comen3.setId(3L);
        comen3.setTexto("Ha realizado un buen entrenamiento");
        comen3.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        comen3.setHoraComentario("18:00");
        comen3.setIdBeneficiario(3L);
        comen3.setIdProfesor(3L);
        repoComentario.save(comen3);

        ComentarioEntity comen4 = new ComentarioEntity();
        comen4.setId(4L);
        comen4.setTexto("No ha realizado un buen entrenamiento");
        comen4.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        comen4.setHoraComentario("18:00");
        comen4.setIdBeneficiario(4L);
        comen4.setIdProfesor(4L);
        repoComentario.save(comen4);

        ComentarioEntity comen5 = new ComentarioEntity();
        comen4.setId(5L);
        comen4.setTexto("Ha realizado un buen entrenamiento");
        comen4.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        comen4.setHoraComentario("19:00");
        comen4.setIdBeneficiario(5L);
        comen4.setIdProfesor(5L);
        repoComentario.save(comen5);

        ComentarioEntity comen6 = new ComentarioEntity();
        comen4.setId(6L);
        comen4.setTexto("Ha realizado un mal  entrenamiento");
        comen4.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        comen4.setHoraComentario("13:00");
        comen4.setIdBeneficiario(6L);
        comen4.setIdProfesor(6L);
        repoComentario.save(comen6);

    }

    @Autowired
    private ILeidoComentarioMonitorRepository repoLeidoComentario;

    private void crear4ComentariosLeidos() {
        LeidoComentarioMonitorEntity comen1 = new LeidoComentarioMonitorEntity();
        comen1.setId(1L);
        comen1.setIdComentario(1L);
        comen1.setIdMonitor(1L);
        repoLeidoComentario.save(comen1);

        LeidoComentarioMonitorEntity comen2 = new LeidoComentarioMonitorEntity();
        comen2.setId(2L);
        comen2.setIdComentario(2L);
        comen2.setIdMonitor(2L);
        repoLeidoComentario.save(comen2);

        LeidoComentarioMonitorEntity comen3 = new LeidoComentarioMonitorEntity();
        comen2.setId(3L);
        comen2.setIdComentario(3L);
        comen2.setIdMonitor(3L);
        repoLeidoComentario.save(comen3);

        LeidoComentarioMonitorEntity comen4 = new LeidoComentarioMonitorEntity();
        comen2.setId(4L);
        comen2.setIdComentario(4L);
        comen2.setIdMonitor(4L);
        repoLeidoComentario.save(comen4);

    }

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    private void crear2Beneficiarios() {
        BeneficiarioEntity ben1 = new BeneficiarioEntity();
        ben1.setId(1L);
        ben1.setIdUsuario(1L);
        ben1.setNombre("Mohammed");
        ben1.setApellido1("Ali");
        ben1.setApellido2("Hassad");
        ben1.setDocumento("21231424B");
        ben1.setTelefono("976847598");
        ben1.setComentarios("Necesita reforzar conocimientos en el área de matemáticas");
        ben1.setIdTutor(1L);
        repoBeneficiario.save(ben1);

        BeneficiarioEntity ben2 = new BeneficiarioEntity();
        ben2.setId(2L);
        ben2.setIdUsuario(1L);
        ben2.setNombre("Carlos");
        ben2.setApellido1("Gasca");
        ben2.setApellido2("Pérez");
        ben2.setDocumento("21231424A");
        ben2.setTelefono("648795820");
        ben2.setComentarios("Necesario incentivar su participación");
        ben2.setIdTutor(1L);
        repoBeneficiario.save(ben2);

    }

    @Autowired
    private IEvaluacionBeneficiarioRepository repoEvaBen;

    private void crear2EvaluacionesBeneficiarios() {
        EvaluacionBeneficiarioEntity evaben1 = new EvaluacionBeneficiarioEntity();
        evaben1.setId(1L);
        evaben1.setIdBeneficiario(4L);
        evaben1.setIdProfesor(1L);
        evaben1.setIdMonitor(1L);
        evaben1.setIdActividad(1L);
        evaben1.setIdBeneficiario(1L);
        evaben1.setPuntuacion(7);
        evaben1.setComentarios("No hay comentarios");
        evaben1.setFechaDeEvaluacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoEvaBen.save(evaben1);

        EvaluacionBeneficiarioEntity evaben2 = new EvaluacionBeneficiarioEntity();
        evaben2.setId(2L);
        evaben2.setIdBeneficiario(1L);
        evaben2.setIdProfesor(1L);
        evaben2.setIdMonitor(1L);
        evaben2.setIdActividad(1L);
        evaben2.setPuntuacion(9);
        evaben2.setComentarios("Me ha encantado, ha sido muy entretenido y me ha servido de ayuda");
        evaben2.setFechaDeEvaluacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoEvaBen.save(evaben2);

    }

    @Autowired
    private IEvaluacionMonitorRepository repoEvaMon;

    private void crear2EvaluacionesMonitores() {
        EvaluacionMonitorEntity evamon1 = new EvaluacionMonitorEntity();
        evamon1.setId(1L);
        evamon1.setIdBeneficiario(1L);
        evamon1.setIdMonitor(1L);
        evamon1.setValoracion(10);
        evamon1.setComentario("No hay comentarios");
        evamon1.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoEvaMon.save(evamon1);

        EvaluacionMonitorEntity evamon2 = new EvaluacionMonitorEntity();
        evamon2.setId(2L);
        evamon2.setIdBeneficiario(4L);
        evamon2.setIdMonitor(4L);
        evamon2.setValoracion(8);
        evamon2.setComentario("No hay comentarios");
        evamon2.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoEvaMon.save(evamon2);
    }

    @Autowired
    private IFaltaAsistenciaRepository repoFaltas;

    private void crear2FaltasDeAsistencia() {
        FaltaAsistenciaEntity falta1 = new FaltaAsistenciaEntity();
        falta1.setId(1L);
        falta1.setIdBeneficiario(1L);
        falta1.setIdActividad(1L);
        falta1.setIdMonitor(1L);
        falta1.setIdProfesor(1L);
        falta1.setMotivo("Enfermedad");
        falta1.setObservacion("Se ha presentado el justificante médico");
        falta1.setFechaFaltaAsistencia(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        falta1.setIdActividad(1L);
        repoFaltas.save(falta1);

        FaltaAsistenciaEntity falta2 = new FaltaAsistenciaEntity();
        falta2.setId(1L);
        falta2.setIdBeneficiario(1L);
        falta2.setIdMonitor(1L);
        falta2.setId(2L);
        falta2.setIdActividad(1L);
        falta2.setIdBeneficiario(1L);
        falta2.setIdMonitor(3L);
        falta2.setIdProfesor(1L);
        falta2.setMotivo("Desconocido");
        falta2.setObservacion("A la espeera de hablar con los tutores y el alumno");
        falta2.setFechaFaltaAsistencia(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        falta2.setIdActividad(1L);
        repoFaltas.save(falta2);
    }

    @Autowired
    private IHorarioRepository repoHorario;

    private void crear2Horarios() {
        HorarioEntity hor1 = new HorarioEntity();
        hor1.setId(1L);
        hor1.setIdActividad(1L);
        hor1.setHorarioInicio("19:00");
        hor1.setHorarioFin("20:15");
        hor1.setTipoActividad("Periodica");
        hor1.setFechaActividad(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        hor1.setFechaInicio(general.getFechaCercana((int) (Math.random() * 10 - 10)));
        hor1.setFechaFin(general.getFechaCercana((int) (Math.random() * 10)));
        hor1.setDiaSemana(5);
        repoHorario.save(hor1);

        HorarioEntity hor2 = new HorarioEntity();
        hor2.setId(2L);
        hor2.setIdActividad(2L);
        hor2.setHorarioInicio("17:00");
        hor2.setHorarioFin("23:45");
        hor2.setTipoActividad("Puntual");
        hor2.setDiaSemana(1);
        hor2.setFechaActividad(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        hor2.setFechaInicio(general.getFechaCercana((int) (Math.random() * 10 - 10)));
        hor2.setFechaFin(general.getFechaCercana((int) (Math.random() * 10)));
        hor2.setDiaSemana(5);
        repoHorario.save(hor2);
    }

    @Autowired
    private IMonitorRepository repoMonitor;

    private void crear2Monitores() {
        MonitorEntity mon1 = new MonitorEntity();
        mon1.setId(1L);
        mon1.setIdUsuario(1L);
        mon1.setNombre("Alberto");
        mon1.setApellido1("García");
        mon1.setApellido2("Pérez");
        mon1.setTelefono("687558962");
        mon1.setCorreo("agarcia@monitor.es");
        mon1.setFechaActividad(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMonitor.save(mon1);

        MonitorEntity mon2 = new MonitorEntity();
        mon2.setId(2L);
        mon2.setIdUsuario(2L);
        mon2.setNombre("Sandra");
        mon2.setApellido1("Jimenez");
        mon2.setApellido2("Lahoz");
        mon2.setTelefono("610578741");
        mon2.setCorreo("sjimenez@monitor.es");
        mon2.setFechaActividad(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMonitor.save(mon2);

    }

    @Autowired
    private IMemoriaRepository repoMemoria;

    private void crear2Memorias() {
        MemoriaEntity mem1 = new MemoriaEntity();
        mem1.setId(1L);
        mem1.setIdBeneficiario(1L);
        mem1.setIdMonitor(1L);
        mem1.setComentarios("Su rendimiento ha sido satisfactorio");
        mem1.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMemoria.save(mem1);

        MemoriaEntity mem2 = new MemoriaEntity();
        mem2.setId(1L);
        mem2.setIdBeneficiario(1L);
        mem2.setIdMonitor(1L);
        mem2.setComentarios("Sigue necesitando ayuda en áreas como historia y geografía");
        mem2.setFechaComentario(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMemoria.save(mem2);
    }

    @Autowired
    private IParticipacionBeneficiarioRepository repoParBen;

    private void crear2ParticipacionesBeneficiarios() {
        ParticipacionBeneficiarioEntity parben1 = new ParticipacionBeneficiarioEntity();
        parben1.setId(1L);
        parben1.setIdBeneficiario(1L);
        parben1.setIdActividad(1L);
        repoParBen.save(parben1);

        ParticipacionBeneficiarioEntity parben2 = new ParticipacionBeneficiarioEntity();
        parben2.setId(2L);
        parben2.setIdBeneficiario(1L);
        parben2.setIdActividad(1L);
        repoParBen.save(parben2);
    }

    @Autowired
    private IProfesorRepository repoProfesor;

    private void crear2Profesores() {
        ProfesorEntity prof1 = new ProfesorEntity();
        prof1.setId(1L);
        prof1.setIdUsuario(1L);
        prof1.setNombre("Ruth");
        prof1.setApellido1("Lorenzo");
        prof1.setApellido2("Gómez");
        repoProfesor.save(prof1);

        ProfesorEntity prof2 = new ProfesorEntity();
        prof2.setId(2L);
        prof2.setIdUsuario(2L);
        prof2.setNombre("Laura");
        prof2.setApellido1("Samperiz");
        prof2.setApellido2("Casajús");
        repoProfesor.save(prof2);
    }

    @Autowired
    private ITutorRepository repoTutor;

    private void crear2Tutores() {
        TutorEntity tut1 = new TutorEntity();
        tut1.setId(1L);
        tut1.setNombre("Adila");
        tut1.setApellido("Ait");
        tut1.setDireccion("Calle Conde Aranda");
        tut1.setTelefono("976879764");
        tut1.setComentario("Me gustaría hablar con el tutor acerca de los avances de mi hijo");
        tut1.setNombreMonitor("Raúl");
        tut1.setFechaUltimaEntrada(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoTutor.save(tut1);

        TutorEntity tut2 = new TutorEntity();
        tut2.setId(2L);
        tut2.setNombre("Mauricio");
        tut2.setApellido("Hernández");
        tut2.setDireccion("Avda/ La Diputación");
        tut2.setTelefono("78945862");
        tut2.setComentario("No hay comentarios");
        tut2.setNombreMonitor("Lucía");
        tut2.setFechaUltimaEntrada(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoTutor.save(tut2);
    }

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Transactional
    private void crear2Usuarios() {
        UsuarioEntity us1 = new UsuarioEntity();
        us1.setUsuario("admin");
        us1.setPassword(passEnc.encode("1111"));
        us1.setEstado("Aceptado");
        us1.setRol("ROLE_ACT_Admin");
        us1.setEmail("carla-marin@gmail.com");
        us1.setCodigoVerificacion(UUID.randomUUID().toString());;
        repoUsuario.save(us1);

        UsuarioEntity us2 = new UsuarioEntity();
        us2.setUsuario("beneficiario");
        us2.setPassword(passEnc.encode("1111"));
        us2.setEstado("Aceptado");
        us2.setRol("ROLE_ACT_Beneficiario");
        us2.setEmail("dmartinez97@gmail.com");
        us2.setCodigoVerificacion(UUID.randomUUID().toString());;
        us2 = repoUsuario.save(us2);
        BeneficiarioEntity ben2 = new BeneficiarioEntity();
        ben2.setIdUsuario(us2.getId());
        ben2.setNombre("Sandra");
        ben2.setApellido1("Ruiz");
        ben2.setApellido2("Pérez");
        ben2.setDocumento("21231424A");
        ben2.setTelefono("648795820");
        ben2.setComentarios("Necesario incentivar su participación");
        ben2.setIdTutor(1L);
        repoBeneficiario.save(ben2);
        

        us2 = new UsuarioEntity();
        us2.setUsuario("monitor");
        us2.setPassword(passEnc.encode("1111"));
        us2.setEstado("Aceptado");
        us2.setRol("ROLE_ACT_Monitor");
        us2.setEmail("dmartinez97@gmail.com");
        us2.setCodigoVerificacion(UUID.randomUUID().toString());;
        us2=repoUsuario.save(us2);
        MonitorEntity mon2 = new MonitorEntity();
        mon2.setIdUsuario(us2.getId());
        mon2.setNombre("Juan");
        mon2.setApellido1("Fernandez");
        mon2.setApellido2("Lahoz");
        mon2.setTelefono("610578741");
        mon2.setCorreo("sjimenez@monitor.es");
        mon2.setFechaActividad(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMonitor.save(mon2);

        
        
        us2 = new UsuarioEntity();
        us2.setUsuario("profesor");
        us2.setPassword(passEnc.encode("1111"));
        us2.setEstado("Aceptado");
        us2.setRol("ROLE_ACT_Profesor");
        us2.setEmail("dmartinez97@gmail.com");
        us2.setCodigoVerificacion(UUID.randomUUID().toString());
        us2=repoUsuario.save(us2);
        us2.setCodigoVerificacion(UUID.randomUUID().toString());;
        ProfesorEntity prof2 = new ProfesorEntity();
        prof2.setIdUsuario(us2.getId());
        prof2.setNombre("Laura");
        prof2.setApellido1("Agne");
        prof2.setApellido2("Casajús");
        repoProfesor.save(prof2);

        

        us2 = new UsuarioEntity();
        us2.setUsuario("tutor");
        us2.setPassword(passEnc.encode("1111"));
        us2.setRol("ROLE_ACT_Tutor");
        us2.setEstado("Aceptado");
        us2.setEmail("dmartinez97@gmail.com");
        us2.setCodigoVerificacion(UUID.randomUUID().toString());
        us2 = repoUsuario.save(us2);
        TutorEntity tut1 = new TutorEntity();
        tut1.setIdUsuario(us2.getId());
        tut1.setNombre("Alfredo");
        tut1.setApellido("Lopez");
        tut1.setDireccion("Calle Conde Aranda");
        tut1.setTelefono("976879764");
        tut1.setComentario("Me gustaría hablar con el tutor acerca de los avances de mi hijo");
        tut1.setNombreMonitor("Raúl");
        tut1.setFechaUltimaEntrada(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoTutor.save(tut1);
    }

    @Autowired
    private IParticipacionMonitorRepository repoParMon;

    private void crear2ParticipacionesMonitores() {
        ParticipacionMonitorEntity parmon1 = new ParticipacionMonitorEntity();
        parmon1.setId(1L);
        parmon1.setIdActividad(1L);
        parmon1.setIdMonitor(1L);
        repoParMon.save(parmon1);

        ParticipacionMonitorEntity parmon2 = new ParticipacionMonitorEntity();
        parmon2.setId(2L);
        parmon2.setIdActividad(2L);
        parmon2.setIdMonitor(2L);
        repoParMon.save(parmon2);
    }

}
