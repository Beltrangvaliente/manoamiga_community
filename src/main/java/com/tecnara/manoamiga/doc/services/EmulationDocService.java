package com.tecnara.manoamiga.doc.services;

import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.AsistenciaEntity;
import com.tecnara.manoamiga.doc.entities.CentroAtencionEntity;
import com.tecnara.manoamiga.doc.entities.CentroEntity;
import com.tecnara.manoamiga.doc.entities.CitaEntity;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoRequeridoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSolicitadoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import com.tecnara.manoamiga.doc.entities.EmpresaEntity;
import com.tecnara.manoamiga.doc.entities.MatriculaEntity;
import com.tecnara.manoamiga.doc.entities.NotificacionEntity;
import com.tecnara.manoamiga.doc.entities.ParticipacionProfesorCursoEntity;
import com.tecnara.manoamiga.doc.entities.ProfesorEntity;
import com.tecnara.manoamiga.doc.entities.TituloEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.IAsistenciaRepository;
import com.tecnara.manoamiga.doc.repositories.ICentroAtencionRepository;
import com.tecnara.manoamiga.doc.repositories.ICentroRepository;
import com.tecnara.manoamiga.doc.repositories.ICitaRepository;
import com.tecnara.manoamiga.doc.repositories.ICursoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoRequeridoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSolicitadoRepository;
import com.tecnara.manoamiga.doc.repositories.IEmpresaRepository;
import com.tecnara.manoamiga.doc.repositories.IMatriculaRepository;
import com.tecnara.manoamiga.doc.repositories.INotificacionRepository;
import com.tecnara.manoamiga.doc.repositories.IParticipacionProfesorCursoRepository;
import com.tecnara.manoamiga.doc.repositories.IProfesorRepository;
import com.tecnara.manoamiga.doc.repositories.ITituloRepository;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmulationDocService implements IEmulationDocService {

    @Autowired
    private IGeneral general;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private ICitaRepository repoCita;

    @Autowired
    private IProfesorRepository repoProfesor;

    @Autowired
    private IParticipacionProfesorCursoRepository repoParticipacionProfesorCurso;

    @Autowired
    private IUsuarioRepository repoUsuarios;

    @Autowired
    private ICentroAtencionRepository repoCentroAtencion;

    @Autowired
    private IAsistenciaRepository repoAsistencia;

    @Autowired
    private IDocumentoSubidoRepository repoDocumentoSubido;

    @Autowired
    private IDocumentoRequeridoRepository repoDocumentoRequerido;

    @Autowired
    private IDocumentoSolicitadoRepository repoDocumentoSolicitado;

    @Autowired
    private INotificacionRepository repoNotificacion;

    public Date getDateRandom() {
        return general.getFechaCercana((int) (Math.random() * 10 - 5));
    }

    private PasswordEncoder passEnc;
    
    @Override
    public void generarDatosDePrueba(PasswordEncoder passEnc) {
        this.passEnc=passEnc;
        try {

            if (repoEmpresa.count() > 0) {
                return; //No hacer nada
            }

            //Información de los cursos
            crear2Empresas();
            crear2Centros();
            crear4Cursos();

            //Informacion de los alumnos
            crear10Alumnos();   //Incluir también tabla usuarios
            crear12Titulos();
            crear12Matriculas();
            crear15DocumentosRequeridos();
            crear4DocumentosSolicitados();
            crear15DocumentosSubidos();
            crear15Asistencias();

            crear2CentrosDeAtencion();
            crear10Citas();

            //Informacion de los profesores
            crear3Profesores(); //Incluir también tabla usuarios
            crear4ParticipacionProfesorCurso();
            crearNotificacionLeidoNoAlumno();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private IEmpresaRepository repoEmpresa;

    private void crear2Empresas() {
        EmpresaEntity empresa1 = new EmpresaEntity();
        empresa1.setId(1L);
        empresa1.setNombre("Tecnara");
        empresa1.setCodigoPostal("50014");
        empresa1.setDireccion("Av/ San Jose 45");
        empresa1.setNif("232452634G");
        empresa1.setWebEmpresa("http://www.tecnara.com");
        repoEmpresa.save(empresa1);

        EmpresaEntity empresa2 = new EmpresaEntity();
        empresa2.setId(2L);
        empresa2.setNombre("Hiberus");
        empresa2.setCodigoPostal("50007");
        empresa2.setDireccion("Av/ Romareda");
        empresa2.setNif("5232634G");
        empresa2.setWebEmpresa("http://www.hiberus.com");
        repoEmpresa.save(empresa2);
    }

    @Autowired
    private ICentroRepository repoCentro;

    private void crear2Centros() {
        CentroEntity centro1 = new CentroEntity();
        centro1.setId(1L);
        centro1.setNombre("Goya");
        centro1.setCodigoPostal("50008");
        centro1.setDireccion("Av/ Goya 145");
        centro1.setContactoMail("http://www.goya.com");
        centro1.setContactoTelefono("976598743");
        centro1.setDescripcion("Prestigioso centro educativo");
        centro1.setPersonaContacto("Maria Luisa");
        centro1.setHorarioAtencion("9:00,14:30");
        repoCentro.save(centro1);

        CentroEntity centro2 = new CentroEntity();
        centro2.setId(2L);
        centro2.setNombre("Ceste");
        centro2.setCodigoPostal("50020");
        centro2.setDireccion("Av/ Canal 125");
        centro2.setContactoMail("http://www.ceste.com");
        centro2.setContactoTelefono("976591723");
        centro2.setDescripcion("Prestigioso centro educativo");
        centro2.setPersonaContacto("Luis Cañete");
        centro2.setHorarioAtencion("8:30,17:30");
        repoCentro.save(centro2);

    }

    @Autowired
    private ICursoRepository repoCurso;

    private void crear4Cursos() {
        CursoEntity curso1 = new CursoEntity();
        curso1.setIdCentro(1L);
        curso1.setNombreCurso("Java");
        curso1.setModalidad("presencial");
        curso1.setNumeroHoras(10);
        curso1.setNumeroAlumnos(12);
        curso1.setHorario("08:00-13:00");
        curso1.setPrecio(450.50);
        curso1.setLocalidad("Zaragoza");
        curso1.setReferencia("JV-2132");
        curso1.setFechaInicio(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoCurso.save(curso1);

        CursoEntity curso2 = new CursoEntity();
        curso2.setId(2L);
        curso2.setIdCentro(1L);
        curso2.setNombreCurso("Html");
        curso2.setModalidad("online");
        curso2.setNumeroHoras(500);
        curso2.setNumeroAlumnos(22);
        curso2.setHorario("04:00-09:00");
        curso2.setPrecio(150.50);
        curso2.setLocalidad("Huesca");
        curso2.setReferencia("HTML-4252");
        curso2.setFechaInicio(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoCurso.save(curso2);

        CursoEntity curso3 = new CursoEntity();
        curso3.setId(3L);
        curso3.setIdCentro(1L);
        curso3.setNombreCurso("Css");
        curso3.setModalidad("online");
        curso3.setNumeroHoras(150);
        curso3.setNumeroAlumnos(15);
        curso3.setHorario("08:00-14:00");
        curso3.setPrecio(150.50);
        curso3.setLocalidad("Teruel");
        curso3.setReferencia("CSS-2555");
        curso3.setFechaInicio(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoCurso.save(curso3);

        CursoEntity curso4 = new CursoEntity();
        curso4.setId(4L);
        curso4.setIdCentro(1L);
        curso4.setNombreCurso("Jardineria");
        curso4.setModalidad("presencial");
        curso4.setNumeroHoras(1250);
        curso4.setNumeroAlumnos(25);
        curso4.setHorario("18:00-21:00");
        curso4.setPrecio(150.50);
        curso4.setLocalidad("Zaragoza");
        curso4.setReferencia("JAR-442442");
        curso4.setFechaInicio(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoCurso.save(curso4);

    }

    @Autowired
    private IAlumnoRepository repoAlumno;

    private void crear10Alumnos() {
        UsuarioEntity usu1 = new UsuarioEntity();
        usu1.setUsuario("alumno");
        usu1.setEmail("pepegm@gmail.com");
        usu1.setEstado("Aceptado");
        usu1.setPassword(passEnc.encode("1111"));
        usu1.setRol("ROLE_DOC_Alumno");
        usu1.setCodigoVerificacion(UUID.randomUUID().toString());
        usu1=repoUsuarios.save(usu1);
        AlumnoEntity alumno1 = new AlumnoEntity();
        alumno1.setIdUsuario(usu1.getId());
        alumno1.setNombre("Pepe");
        alumno1.setApellido1("Gonzalez");
        alumno1.setApellido2("Miranda");
        alumno1.setMail("pepegm@gmail.com");
        alumno1.setDireccion("Calle Fita nº12");
        alumno1.setTelefono("657894174");
        alumno1.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno1);

        UsuarioEntity usu2 = new UsuarioEntity();
        usu2.setUsuario("Ana");
        usu2.setEmail("anagam@gmail.com");
        usu2.setEstado("aceptado");
        usu2.setCodigoVerificacion(UUID.randomUUID().toString());
        usu2.setPassword(passEnc.encode("1111"));
        usu2.setRol("ROLE_DOC_Alumno");
        usu2=repoUsuarios.save(usu2);

        AlumnoEntity alumno2 = new AlumnoEntity();
        alumno2.setIdUsuario(usu2.getId());
        alumno2.setNombre("Ana");
        alumno2.setApellido1("Garcia");
        alumno2.setApellido2("Misales");
        alumno2.setMail("anagam@gmail.com");
        alumno2.setDireccion("Calle Rosas nº25");
        alumno2.setTelefono("638571243");
        alumno2.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno2);

        UsuarioEntity usu3 = new UsuarioEntity();
        usu3.setUsuario("Carlos");
        usu3.setEmail("martinez@hotmail.com");
        usu3.setEstado("aceptado");
        usu3.setCodigoVerificacion(UUID.randomUUID().toString());
        usu3.setPassword(passEnc.encode("1111"));
        usu3.setRol("ROLE_DOC_Alumno");
        
        usu3=repoUsuarios.save(usu3);

        AlumnoEntity alumno3 = new AlumnoEntity();
        alumno3.setIdUsuario(usu3.getId());
        alumno3.setNombre("Carlos");
        alumno3.setApellido1("Martinez");
        alumno3.setApellido2("Galgo");
        alumno3.setMail("martinez@hotmail.com");
        alumno3.setDireccion("Calle Rosas nº25");
        alumno3.setTelefono("638271263");
        alumno3.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno3);

        UsuarioEntity usu4 = new UsuarioEntity();
        usu4.setUsuario("Ana");
        usu4.setEmail("anagam@gmail.com");
        usu4.setEstado("Aceptado");
        usu4.setCodigoVerificacion(UUID.randomUUID().toString());
        usu4.setPassword(passEnc.encode("1111"));
        usu4.setRol("ROLE_DOC_Alumno");
        usu4=repoUsuarios.save(usu4);

        AlumnoEntity alumno4 = new AlumnoEntity();
        alumno4.setIdUsuario(usu4.getId());
        alumno4.setNombre("Ana");
        alumno4.setApellido1("Garcia");
        alumno4.setApellido2("Misales");
        alumno4.setMail("anagam@gmail.com");
        alumno4.setDireccion("Calle Rosas nº25");
        alumno4.setTelefono("631571203");
        alumno4.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno4);

        UsuarioEntity usu5 = new UsuarioEntity();
        usu5.setUsuario("Jorge");
        usu5.setEmail("jorgeh@gmail.com");
        usu5.setEstado("Aceptado");
        usu5.setCodigoVerificacion(UUID.randomUUID().toString());
        usu5.setPassword(passEnc.encode("1111"));
        usu5.setRol("ROLE_DOC_Alumno");
        usu5=repoUsuarios.save(usu5);

        AlumnoEntity alumno5 = new AlumnoEntity();
        alumno5.setIdUsuario(usu5.getId());
        alumno5.setNombre("Jorge");
        alumno5.setApellido1("Hernandez");
        alumno5.setApellido2("Cales");
        alumno5.setMail("jorgeh@gmail.com");
        alumno5.setDireccion("Calle Delicias nº5");
        alumno5.setTelefono("538571223");
        alumno5.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno5);

        UsuarioEntity usu6 = new UsuarioEntity();
        usu6.setUsuario("Carmen");
        usu6.setEmail("carmen@hotmail.com");
        usu6.setEstado("aceptado");
        usu6.setCodigoVerificacion(UUID.randomUUID().toString());
        usu6.setPassword(passEnc.encode("1111"));
        usu6.setRol("ROLE_DOC_Alumno");
        usu6=repoUsuarios.save(usu6);

        AlumnoEntity alumno6 = new AlumnoEntity();
        alumno6.setIdUsuario(usu6.getId());
        alumno6.setNombre("Carmen");
        alumno6.setApellido1("Rodrigez");
        alumno6.setApellido2("Sarasa");
        alumno6.setMail("carmen@hotmail.com");
        alumno6.setDireccion("Camino del Bado nº45");
        alumno6.setTelefono("612371423");
        alumno6.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno6);

        UsuarioEntity usu7 = new UsuarioEntity();
        usu7.setUsuario("Carmen");
        usu7.setEmail("carmen@hotmail.com");
        usu7.setEstado("aceptado");
        usu7.setCodigoVerificacion(UUID.randomUUID().toString());
        usu7.setPassword(passEnc.encode("1111"));
        usu7.setRol("ROLE_DOC_Alumno");
        usu7=repoUsuarios.save(usu7);

        AlumnoEntity alumno7 = new AlumnoEntity();
        alumno7.setIdUsuario(usu7.getId());
        alumno7.setNombre("Melisa");
        alumno7.setApellido1("Gasset");
        alumno7.setApellido2("Perales");
        alumno7.setMail("www.melisa@gmail.com");
        alumno7.setDireccion("Calle Alfonso nº13");
        alumno7.setTelefono("534571129");
        alumno7.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno7);

        UsuarioEntity usu8 = new UsuarioEntity();
        usu8.setUsuario("Toñi");
        usu8.setEmail("pelaez@gmail.com");
        usu8.setEstado("aceptado");
        usu8.setCodigoVerificacion(UUID.randomUUID().toString());
        usu8.setPassword(passEnc.encode("1111"));
        usu8.setRol("ROLE_DOC_Alumno");
        usu8=repoUsuarios.save(usu8);

        AlumnoEntity alumno8 = new AlumnoEntity();
        alumno8.setIdUsuario(usu8.getId());
        alumno8.setNombre("Toñi");
        alumno8.setApellido1("Pelaez,Garces");
        alumno8.setApellido2("Garces");
        alumno8.setMail("pelaez@gmail.com");
        alumno8.setDireccion("Calle San Pablo nº69");
        alumno8.setTelefono("601234567");
        alumno8.setFechaNacimiento(general.getFechaActual());
        repoAlumno.save(alumno8);
    }

    @Autowired
    private ITituloRepository repoTitulo;

    private void crear12Titulos() {

        TituloEntity titulo1 = new TituloEntity();
        titulo1.setId(1L);
        titulo1.setIdAlumno(1L);
        titulo1.setTitulacion("Graduado Escolar");
        titulo1.setNivel("Basica");
        titulo1.setAnyoExpedicion(1985);
        titulo1.setCentroExpedicion("Colegio Miraflores");
        titulo1.setHomologacion("si");
        titulo1.setPaisExpedicion("España");
        repoTitulo.save(titulo1);

        TituloEntity titulo2 = new TituloEntity();
        titulo2.setId(2L);
        titulo2.setIdAlumno(2L);
        titulo2.setTitulacion("Bachiller");
        titulo2.setNivel("Secundaria");
        titulo2.setAnyoExpedicion(1995);
        titulo2.setCentroExpedicion("I.E.S.Pablo Gargallo");
        titulo2.setHomologacion("si");
        titulo2.setPaisExpedicion("España");
        repoTitulo.save(titulo2);

        TituloEntity titulo3 = new TituloEntity();
        titulo3.setId(3L);
        titulo3.setIdAlumno(3L);
        titulo3.setTitulacion("FP");
        titulo3.setNivel("Secundaria");
        titulo3.setAnyoExpedicion(1991);
        titulo3.setCentroExpedicion("I.E.S.Miguel Servet");
        titulo3.setHomologacion("si");
        titulo3.setPaisExpedicion("España");
        repoTitulo.save(titulo3);

        TituloEntity titulo4 = new TituloEntity();
        titulo4.setId(4L);
        titulo4.setIdAlumno(4L);
        titulo4.setTitulacion("ESO");
        titulo4.setNivel("Basica");
        titulo4.setAnyoExpedicion(1981);
        titulo4.setCentroExpedicion("Miguel Catalán");
        titulo4.setHomologacion("si");
        titulo4.setPaisExpedicion("España");
        repoTitulo.save(titulo4);

        TituloEntity titulo5 = new TituloEntity();
        titulo5.setId(5L);
        titulo5.setIdAlumno(5L);
        titulo5.setTitulacion("FP2");
        titulo5.setNivel("Superior");
        titulo5.setAnyoExpedicion(1997);
        titulo5.setCentroExpedicion("I.E.S.Severo Ochoa");
        titulo5.setHomologacion("si");
        titulo5.setPaisExpedicion("España");
        repoTitulo.save(titulo5);

        TituloEntity titulo6 = new TituloEntity();
        titulo6.setId(6L);
        titulo6.setIdAlumno(6L);
        titulo6.setTitulacion("Taller Ocupacional");
        titulo6.setNivel("Laboral 1");
        titulo6.setAnyoExpedicion(1987);
        titulo6.setCentroExpedicion("Grillo Jiménez");
        titulo6.setHomologacion("si");
        titulo6.setPaisExpedicion("España");
        repoTitulo.save(titulo6);

        TituloEntity titulo7 = new TituloEntity();
        titulo7.setId(7L);
        titulo7.setIdAlumno(7L);
        titulo7.setTitulacion("ESO");
        titulo7.setNivel("Secundaria");
        titulo7.setAnyoExpedicion(1947);
        titulo7.setCentroExpedicion("Agustinos");
        titulo7.setHomologacion("si");
        titulo7.setPaisExpedicion("España");
        repoTitulo.save(titulo7);

        TituloEntity titulo8 = new TituloEntity();
        titulo8.setId(8L);
        titulo8.setIdAlumno(8L);
        titulo8.setTitulacion("Universitaria");
        titulo8.setNivel("Maximus");
        titulo8.setAnyoExpedicion(1977);
        titulo8.setCentroExpedicion("Universidad de Zaragoza");
        titulo8.setHomologacion("si");
        titulo8.setPaisExpedicion("España");
        repoTitulo.save(titulo8);

        TituloEntity titulo9 = new TituloEntity();
        titulo9.setId(9L);
        titulo9.setIdAlumno(9L);
        titulo9.setTitulacion("Universitaria");
        titulo9.setNivel("Superior");
        titulo9.setAnyoExpedicion(2017);
        titulo9.setCentroExpedicion("Universidad San Jorge");
        titulo9.setHomologacion("si");
        titulo9.setPaisExpedicion("España");
        repoTitulo.save(titulo9);

        TituloEntity titulo10 = new TituloEntity();
        titulo10.setId(10L);
        titulo10.setIdAlumno(10L);
        titulo10.setTitulacion("Primaria");
        titulo10.setNivel("Basica");
        titulo10.setAnyoExpedicion(2017);
        titulo10.setCentroExpedicion("Carmelitas");
        titulo10.setHomologacion("si");
        titulo10.setPaisExpedicion("España");
        repoTitulo.save(titulo10);

        TituloEntity titulo11 = new TituloEntity();
        titulo11.setId(11L);
        titulo11.setIdAlumno(10L);
        titulo11.setTitulacion("ESO");
        titulo11.setNivel("Basica");
        titulo11.setAnyoExpedicion(2017);
        titulo11.setCentroExpedicion("Carmelitas");
        titulo11.setHomologacion("si");
        titulo11.setPaisExpedicion("España");
        repoTitulo.save(titulo11);

        TituloEntity titulo12 = new TituloEntity();
        titulo12.setId(12L);
        titulo12.setIdAlumno(10L);
        titulo12.setTitulacion("Tecnara");
        titulo12.setNivel("Tope de gama");
        titulo12.setAnyoExpedicion(2021);
        titulo12.setCentroExpedicion("Tecnopro");
        titulo12.setHomologacion("si");
        titulo12.setPaisExpedicion("España");
        repoTitulo.save(titulo12);

    }

    @Autowired
    private IMatriculaRepository repoMatricula;

    private void crear12Matriculas() {

        MatriculaEntity matricula1 = new MatriculaEntity();
        matricula1.setId(1L);
        matricula1.setIdAlumno(1L);
        matricula1.setIdCurso(1L);
        matricula1.setEstado("confirmado");
        matricula1.setPagado("si");
        matricula1.setComentarios("genial todo bien");
        matricula1.setFechaMatricula(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMatricula.save(matricula1);

        MatriculaEntity matricula2 = new MatriculaEntity();
        matricula2.setId(2L);
        matricula2.setIdAlumno(1L);
        matricula2.setIdCurso(2L);
        matricula2.setEstado("confirmado");
        matricula2.setPagado("si");
        matricula2.setComentarios("genial todo bien");
        matricula2.setFechaMatricula(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMatricula.save(matricula2);

        MatriculaEntity matricula3 = new MatriculaEntity();
        matricula3.setId(3L);
        matricula3.setIdAlumno(3L);
        matricula3.setIdCurso(3L);
        matricula3.setEstado("confirmado");
        matricula3.setPagado("si");
        matricula3.setComentarios("genial todo bien");
        matricula3.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula3);

        MatriculaEntity matricula4 = new MatriculaEntity();
        matricula4.setId(4L);
        matricula4.setIdAlumno(4L);
        matricula4.setIdCurso(4L);
        matricula4.setEstado("confirmado");
        matricula4.setPagado("si");
        matricula4.setComentarios("genial todo bien");
        matricula4.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula4);

        MatriculaEntity matricula5 = new MatriculaEntity();
        matricula5.setId(5L);
        matricula5.setIdAlumno(5L);
        matricula5.setIdCurso(1L);
        matricula5.setEstado("confirmado");
        matricula5.setPagado("si");
        matricula5.setComentarios("genial todo bien");
        matricula5.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula5);

        MatriculaEntity matricula6 = new MatriculaEntity();
        matricula6.setId(6L);
        matricula6.setIdAlumno(6L);
        matricula6.setIdCurso(2L);
        matricula6.setEstado("confirmado");
        matricula6.setPagado("si");
        matricula6.setComentarios("genial todo bien");
        matricula6.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula6);

        MatriculaEntity matricula7 = new MatriculaEntity();
        matricula7.setId(7L);
        matricula7.setIdAlumno(7L);
        matricula7.setIdCurso(3L);
        matricula7.setEstado("confirmado");
        matricula7.setPagado("si");
        matricula7.setComentarios("genial todo bien");
        matricula7.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula7);

        MatriculaEntity matricula8 = new MatriculaEntity();
        matricula8.setId(8L);
        matricula8.setIdAlumno(8L);
        matricula8.setIdCurso(4L);
        matricula8.setEstado("confirmado");
        matricula8.setPagado("si");
        matricula8.setComentarios("genial todo mal");
        matricula8.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula8);

        MatriculaEntity matricula9 = new MatriculaEntity();
        matricula9.setId(9L);
        matricula9.setIdAlumno(9L);
        matricula9.setIdCurso(1L);
        matricula9.setEstado("confirmado");
        matricula9.setPagado("si");
        matricula9.setComentarios("genial todo bien");
        matricula9.setFechaMatricula(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoMatricula.save(matricula9);

        MatriculaEntity matricula10 = new MatriculaEntity();
        matricula10.setId(10L);
        matricula10.setIdAlumno(10L);
        matricula10.setIdCurso(2L);
        matricula10.setEstado("aceptado");
        matricula10.setPagado("no");
        matricula10.setComentarios("genial todo mal");
        matricula10.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula10);

        MatriculaEntity matricula11 = new MatriculaEntity();
        matricula11.setId(11L);
        matricula11.setIdAlumno(9L);
        matricula11.setIdCurso(3L);
        matricula11.setEstado("confirmado");
        matricula11.setPagado("si");
        matricula11.setComentarios("genial todo bien");
        matricula11.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula11);

        MatriculaEntity matricula12 = new MatriculaEntity();
        matricula12.setId(12L);
        matricula12.setIdAlumno(10L);
        matricula12.setIdCurso(4L);
        matricula12.setEstado("preinscripcion");
        matricula12.setPagado("no");
        matricula12.setComentarios("genial todo mal");
        matricula12.setFechaMatricula(getDateRandom());
        repoMatricula.save(matricula12);

    }

    @Autowired

    private void crear15DocumentosRequeridos() {
        DocumentoRequeridoEntity obj1 = new DocumentoRequeridoEntity();
        obj1.setId(1L);
        obj1.setIdCurso(1L);
        obj1.setTipoDeDocumento("DNI");
        obj1.setObligatorio("SI");
        obj1.setDiasPlazo(10);
        obj1.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj1);

        DocumentoRequeridoEntity obj2 = new DocumentoRequeridoEntity();
        obj2.setId(2L);
        obj2.setIdCurso(1L);
        obj2.setTipoDeDocumento("Tarjeta de Residencia");
        obj2.setObligatorio("SI");
        obj2.setDiasPlazo(10);
        obj2.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj2);

        DocumentoRequeridoEntity obj3 = new DocumentoRequeridoEntity();
        obj3.setId(3L);
        obj3.setIdCurso(1L);
        obj3.setTipoDeDocumento("Título Educación Básica");
        obj3.setObligatorio("SI");
        obj3.setDiasPlazo(10);
        obj3.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj3);

        DocumentoRequeridoEntity obj4 = new DocumentoRequeridoEntity();
        obj4.setId(4L);
        obj4.setIdCurso(1L);
        obj4.setTipoDeDocumento("Certificado de Renta Familiar");
        obj4.setObligatorio("SI");
        obj4.setDiasPlazo(10);
        obj4.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj4);

        DocumentoRequeridoEntity obj5 = new DocumentoRequeridoEntity();
        obj5.setId(5L);
        obj5.setIdCurso(2L);
        obj5.setTipoDeDocumento("DNI");
        obj5.setObligatorio("SI");
        obj5.setDiasPlazo(10);
        obj5.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoRequerido.save(obj5);

        DocumentoRequeridoEntity obj6 = new DocumentoRequeridoEntity();
        obj6.setId(6L);
        obj6.setIdCurso(2L);
        obj6.setTipoDeDocumento("Tarjeta de Residencia");
        obj6.setObligatorio("SI");
        obj6.setDiasPlazo(10);
        obj6.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj6);

        DocumentoRequeridoEntity obj7 = new DocumentoRequeridoEntity();
        obj7.setId(7L);
        obj7.setIdCurso(2L);
        obj7.setTipoDeDocumento("Título Educación Básica");
        obj7.setObligatorio("SI");
        obj7.setDiasPlazo(10);
        obj7.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj7);

        DocumentoRequeridoEntity obj8 = new DocumentoRequeridoEntity();
        obj8.setId(8L);
        obj8.setIdCurso(2L);
        obj8.setTipoDeDocumento("Certificado de Renta Familiar");
        obj8.setObligatorio("SI");
        obj8.setDiasPlazo(10);
        obj8.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj8);

        DocumentoRequeridoEntity obj9 = new DocumentoRequeridoEntity();
        obj9.setId(9L);
        obj9.setIdCurso(3L);
        obj9.setTipoDeDocumento("DNI");
        obj9.setObligatorio("SI");
        obj9.setDiasPlazo(10);
        obj9.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj9);

        DocumentoRequeridoEntity obj10 = new DocumentoRequeridoEntity();
        obj10.setId(10L);
        obj10.setIdCurso(3L);
        obj10.setTipoDeDocumento("Tarjeta de Residencia");
        obj10.setObligatorio("SI");
        obj10.setDiasPlazo(10);
        obj10.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj10);

        DocumentoRequeridoEntity obj11 = new DocumentoRequeridoEntity();
        obj11.setId(3L);
        obj11.setIdCurso(3L);
        obj11.setTipoDeDocumento("Título Educación Básica");
        obj11.setObligatorio("SI");
        obj11.setDiasPlazo(10);
        obj11.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj11);

        DocumentoRequeridoEntity obj12 = new DocumentoRequeridoEntity();
        obj12.setId(12L);
        obj12.setIdCurso(3L);
        obj12.setTipoDeDocumento("Certificado de Renta Familiar");
        obj12.setObligatorio("SI");
        obj12.setDiasPlazo(10);
        obj12.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj12);

        DocumentoRequeridoEntity obj13 = new DocumentoRequeridoEntity();
        obj13.setId(13L);
        obj13.setIdCurso(4L);
        obj13.setTipoDeDocumento("DNI");
        obj13.setObligatorio("SI");
        obj13.setDiasPlazo(10);
        obj13.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj13);

        DocumentoRequeridoEntity obj14 = new DocumentoRequeridoEntity();
        obj14.setId(14L);
        obj14.setIdCurso(4L);
        obj14.setTipoDeDocumento("Tarjeta de Residencia");
        obj14.setObligatorio("SI");
        obj14.setDiasPlazo(10);
        obj14.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj14);

        DocumentoRequeridoEntity obj15 = new DocumentoRequeridoEntity();
        obj15.setId(15L);
        obj15.setIdCurso(4L);
        obj15.setTipoDeDocumento("Certificado de Renta Familiar");
        obj15.setObligatorio("SI");
        obj15.setDiasPlazo(10);
        obj15.setFechaActualizacion(getDateRandom());
        repoDocumentoRequerido.save(obj15);

    }

    private void crear4DocumentosSolicitados() {

        DocumentoSolicitadoEntity obj1 = new DocumentoSolicitadoEntity();
        obj1.setId(1L);
        obj1.setIdAlumno(1L);
        obj1.setIdMatricula(1L);
        obj1.setEstado("Pendiente");
        obj1.setFecha_limite(getDateRandom());
        obj1.setIdDocumentoRequerido(1L);
        repoDocumentoSolicitado.save(obj1);

        DocumentoSolicitadoEntity obj2 = new DocumentoSolicitadoEntity();
        obj2.setId(2L);
        obj2.setIdAlumno(1L);
        obj2.setIdMatricula(2L);
        obj2.setEstado("Aceptado");
        obj2.setFecha_limite(getDateRandom());
        obj2.setIdDocumentoRequerido(2L);
        repoDocumentoSolicitado.save(obj2);

        DocumentoSolicitadoEntity obj3 = new DocumentoSolicitadoEntity();
        obj3.setId(3L);
        obj3.setIdAlumno(1L);
        obj3.setIdMatricula(3L);
        obj3.setEstado("Rechazado");
        obj3.setFecha_limite(getDateRandom());
        obj3.setIdDocumentoRequerido(3L);
        repoDocumentoSolicitado.save(obj3);

        DocumentoSolicitadoEntity obj4 = new DocumentoSolicitadoEntity();
        obj4.setId(4L);
        obj4.setIdAlumno(1L);
        obj4.setIdMatricula(4L);
        obj4.setEstado("Aceptado");
        obj4.setFecha_limite(getDateRandom());
        obj4.setIdDocumentoRequerido(4L);
        repoDocumentoSolicitado.save(obj4);

    }

    private void crear15DocumentosSubidos() {
        DocumentoSubidoEntity obj1 = new DocumentoSubidoEntity();
        obj1.setId(1L);
        obj1.setIdAlumno(1L);
        obj1.setIdDocumentoSolicitado(1L);
        obj1.setFechaSubida(getDateRandom());
        obj1.setVerificacion("aceptado");
        obj1.setObservaciones(" ");
        obj1.setFechaActualizacion(getDateRandom());
        repoDocumentoSubido.save(obj1);

        DocumentoSubidoEntity obj2 = new DocumentoSubidoEntity();
        obj2.setId(2L);
        obj2.setIdAlumno(1L);
        obj2.setIdDocumentoSolicitado(1L);
        obj2.setFechaSubida(getDateRandom());
        obj2.setVerificacion("pendiente");
        obj2.setObservaciones(" ");
        obj2.setFechaActualizacion(getDateRandom());
        repoDocumentoSubido.save(obj2);

        DocumentoSubidoEntity obj3 = new DocumentoSubidoEntity();
        obj3.setId(3L);
        obj3.setIdAlumno(6L);
        obj3.setIdDocumentoSolicitado(1L);
        obj3.setFechaSubida(getDateRandom());
        obj3.setVerificacion("pendiente");
        obj3.setObservaciones(" ");
        obj3.setFechaActualizacion(getDateRandom());
        repoDocumentoSubido.save(obj3);

        DocumentoSubidoEntity obj4 = new DocumentoSubidoEntity();
        obj4.setId(4L);
        obj4.setIdAlumno(8L);
        obj4.setIdDocumentoSolicitado(1L);
        obj4.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj4.setVerificacion("aceptado");
        obj4.setObservaciones(" ");
        obj4.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj4);

        DocumentoSubidoEntity obj5 = new DocumentoSubidoEntity();
        obj5.setId(5L);
        obj5.setIdAlumno(1L);
        obj5.setIdDocumentoSolicitado(1L);
        obj5.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj5.setVerificacion("rechazado");
        obj5.setObservaciones("documento caducado");
        obj5.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj5);

        DocumentoSubidoEntity obj6 = new DocumentoSubidoEntity();
        obj6.setId(6L);
        obj6.setIdAlumno(9L);
        obj6.setIdDocumentoSolicitado(1L);
        obj6.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj6.setVerificacion("aceptado");
        obj6.setObservaciones(" ");
        obj6.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj6);

        DocumentoSubidoEntity obj7 = new DocumentoSubidoEntity();
        obj7.setId(7L);
        obj7.setIdAlumno(7L);
        obj7.setIdDocumentoSolicitado(1L);
        obj7.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj7.setVerificacion("aceptado");
        obj7.setObservaciones(" ");
        obj7.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj7);

        DocumentoSubidoEntity obj8 = new DocumentoSubidoEntity();
        obj8.setId(8L);
        obj8.setIdAlumno(5L);
        obj8.setIdDocumentoSolicitado(1L);
        obj8.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj8.setVerificacion("aceptado");
        obj8.setObservaciones(" ");
        obj8.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj8);

        DocumentoSubidoEntity obj9 = new DocumentoSubidoEntity();
        obj9.setId(9L);
        obj9.setIdAlumno(3L);
        obj9.setIdDocumentoSolicitado(1L);
        obj9.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj9.setVerificacion("pendiente");
        obj9.setObservaciones("en espera de ser revisado");
        obj9.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj9);

        DocumentoSubidoEntity obj10 = new DocumentoSubidoEntity();
        obj10.setId(10L);
        obj10.setIdAlumno(1L);
        obj10.setIdDocumentoSolicitado(1L);
        obj10.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj10.setVerificacion("aceptado");
        obj10.setObservaciones(" ");
        obj10.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj10);

        DocumentoSubidoEntity obj11 = new DocumentoSubidoEntity();
        obj11.setId(11L);
        obj11.setIdAlumno(10L);
        obj11.setIdDocumentoSolicitado(1L);
        obj11.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj11.setVerificacion("aceptado");
        obj11.setObservaciones(" ");
        obj11.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj11);

        DocumentoSubidoEntity obj12 = new DocumentoSubidoEntity();
        obj12.setId(12L);
        obj12.setIdAlumno(5L);
        obj12.setIdDocumentoSolicitado(1L);
        obj12.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj12.setVerificacion("aceptado");
        obj12.setObservaciones(" ");
        obj12.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj12);

        DocumentoSubidoEntity obj13 = new DocumentoSubidoEntity();
        obj13.setId(13L);
        obj13.setIdAlumno(8L);
        obj13.setIdDocumentoSolicitado(1L);
        obj13.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj13.setVerificacion("pendiente");
        obj13.setObservaciones(" ");
        obj13.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj13);

        DocumentoSubidoEntity obj14 = new DocumentoSubidoEntity();
        obj14.setId(14L);
        obj14.setIdAlumno(5L);
        obj14.setIdDocumentoSolicitado(1L);
        obj14.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj14.setVerificacion("aceptado");
        obj14.setObservaciones("documento renovado");
        obj14.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj14);

        DocumentoSubidoEntity obj15 = new DocumentoSubidoEntity();
        obj15.setId(15L);
        obj15.setIdAlumno(3L);
        obj15.setIdDocumentoSolicitado(1L);
        obj15.setFechaSubida(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj15.setVerificacion("aceptado");
        obj15.setObservaciones(" ");
        obj15.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoDocumentoSubido.save(obj15);

    }

    private void crear15Asistencias() {
        AsistenciaEntity obj1 = new AsistenciaEntity();
        obj1.setId(1L);
        obj1.setIdAlumno(5L);
        obj1.setIdCurso(4L);
        obj1.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj1.setHorario("9:00-9:30h");
        repoAsistencia.save(obj1);

        AsistenciaEntity obj2 = new AsistenciaEntity();
        obj2.setId(2L);
        obj2.setIdAlumno(4L);
        obj2.setIdCurso(2L);
        obj2.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj2.setHorario("10:30-11:30h");
        repoAsistencia.save(obj2);

        AsistenciaEntity obj3 = new AsistenciaEntity();
        obj3.setId(3L);
        obj3.setIdAlumno(3L);
        obj3.setIdCurso(3L);
        obj3.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj3.setHorario("10:00-10:30h");
        repoAsistencia.save(obj3);

        AsistenciaEntity obj4 = new AsistenciaEntity();
        obj4.setId(4L);
        obj4.setIdAlumno(2L);
        obj4.setIdCurso(1L);
        obj4.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj4.setHorario("11:30-12:00h");
        repoAsistencia.save(obj4);

        AsistenciaEntity obj5 = new AsistenciaEntity();
        obj5.setId(5L);
        obj5.setIdAlumno(6L);
        obj5.setIdCurso(3L);
        obj5.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj5.setHorario("12:30-13:00h");
        repoAsistencia.save(obj5);

        AsistenciaEntity obj6 = new AsistenciaEntity();
        obj6.setId(6L);
        obj6.setIdAlumno(7L);
        obj6.setIdCurso(1L);
        obj6.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj6.setHorario("11:30-12:00h");
        repoAsistencia.save(obj6);

        AsistenciaEntity obj7 = new AsistenciaEntity();
        obj7.setId(7L);
        obj7.setIdAlumno(8L);
        obj7.setIdCurso(4L);
        obj7.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj7.setHorario("10:30-11:30h");
        repoAsistencia.save(obj7);

        AsistenciaEntity obj8 = new AsistenciaEntity();
        obj8.setId(8L);
        obj8.setIdAlumno(9L);
        obj8.setIdCurso(2L);
        obj8.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj8.setHorario("9:30-10:30h");
        repoAsistencia.save(obj8);

        AsistenciaEntity obj9 = new AsistenciaEntity();
        obj9.setId(9L);
        obj9.setIdAlumno(10L);
        obj9.setIdCurso(1L);
        obj9.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj9.setHorario("9:00-10:00h");
        repoAsistencia.save(obj9);

        AsistenciaEntity obj10 = new AsistenciaEntity();
        obj10.setId(10L);
        obj10.setIdAlumno(5L);
        obj10.setIdCurso(3L);
        obj10.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj10.setHorario("9:00-9:30h");
        repoAsistencia.save(obj10);

        AsistenciaEntity obj11 = new AsistenciaEntity();
        obj11.setId(11L);
        obj11.setIdAlumno(3L);
        obj11.setIdCurso(2L);
        obj11.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj11.setHorario("11:00-11:30h");
        repoAsistencia.save(obj11);

        AsistenciaEntity obj12 = new AsistenciaEntity();
        obj12.setId(12L);
        obj12.setIdAlumno(1L);
        obj12.setIdCurso(4L);
        obj12.setFecha(new Date(2021, 9, 24));
        obj12.setHorario("10:30-11:30h");
        repoAsistencia.save(obj12);

        AsistenciaEntity obj13 = new AsistenciaEntity();
        obj13.setId(13L);
        obj13.setIdAlumno(4L);
        obj13.setIdCurso(3L);
        obj13.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj13.setHorario("11:30-12:30h");
        repoAsistencia.save(obj13);

        AsistenciaEntity obj14 = new AsistenciaEntity();
        obj14.setId(14L);
        obj14.setIdAlumno(2L);
        obj14.setIdCurso(2L);
        obj14.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj14.setHorario("12:00-12:30h");
        repoAsistencia.save(obj14);

        AsistenciaEntity obj15 = new AsistenciaEntity();
        obj15.setId(15L);
        obj15.setIdAlumno(3L);
        obj15.setIdCurso(1L);
        obj15.setFecha(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj15.setHorario("13:30-14:00h");
        repoAsistencia.save(obj15);

    }

    private void crear2CentrosDeAtencion() {
        CentroAtencionEntity obj1 = new CentroAtencionEntity();
        obj1.setId(1L);
        obj1.setNombre("Fernando El Católico");
        obj1.setTelefono("976333444");
        obj1.setCorreo("info.fernandoelcatolico@cruzroja.es");
        obj1.setDireccion("Avda. Fernando El Católico, 3");
        obj1.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoCentroAtencion.save(obj1);

        CentroAtencionEntity obj2 = new CentroAtencionEntity();
        obj2.setId(2L);
        obj2.setNombre("La Magdalena");
        obj2.setTelefono("976555666");
        obj2.setCorreo("info.lamagdalena@cruzroja.es");
        obj2.setDireccion("C/ La Pera, 15");
        obj2.setFechaActualizacion(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        repoCentroAtencion.save(obj2);
    }

    private void crear10Citas() {
        CitaEntity obj1 = new CitaEntity();
        obj1.setId(1L);
        obj1.setFechaDeCita(new Date(2022, 1, 30));
        obj1.setHoraDeCita("09:30");
        obj1.setIdCentroDeAtencion(1L);
        obj1.setMotivoConsulta("Formación");
        obj1.setExplicacionConsulta("Prueba de nivel de español");
        obj1.setIdCurso(1L);
        obj1.setIdAlumno(10L);
        obj1.setEstado("aceptado");
        repoCita.save(obj1);

        CitaEntity obj2 = new CitaEntity();
        obj2.setId(2L);
        obj2.setFechaDeCita(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj2.setHoraDeCita("10:30");
        obj2.setIdCentroDeAtencion(2L);
        obj2.setMotivoConsulta("Ayudas Económicas");
        obj2.setExplicacionConsulta("Solicitud ayudas");
        obj2.setIdCurso(1L);
        obj2.setEstado("pendiente");
        repoCita.save(obj2);

        CitaEntity obj3 = new CitaEntity();
        obj3.setId(3L);
        obj3.setFechaDeCita(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj3.setHoraDeCita("11:30");
        obj3.setIdCentroDeAtencion(2L);
        obj3.setMotivoConsulta("Orientación");
        obj3.setExplicacionConsulta("Orientación necesidades formativas");
        obj3.setIdCurso(1L);
        obj3.setIdAlumno(8L);
        obj3.setEstado("aceptado");
        repoCita.save(obj3);

        CitaEntity obj4 = new CitaEntity();
        obj4.setId(4L);
        obj4.setFechaDeCita(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj4.setHoraDeCita("12:30");
        obj4.setIdCentroDeAtencion(1L);
        obj4.setMotivoConsulta("Formación");
        obj4.setExplicacionConsulta("Matrícula Curso Español");
        obj4.setIdCurso(1L);
        obj4.setIdAlumno(1L);
        obj4.setEstado("rechazado");
        repoCita.save(obj4);

        CitaEntity obj5 = new CitaEntity();
        obj5.setId(5L);
        obj5.setFechaDeCita(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj5.setHoraDeCita("13:30");
        obj5.setIdCentroDeAtencion(1L);
        obj5.setMotivoConsulta("Orientación");
        obj5.setExplicacionConsulta("Asesoramiento inicial");
        obj5.setIdCurso(1L);
        obj5.setEstado("aceptado");
        repoCita.save(obj5);

        CitaEntity obj6 = new CitaEntity();
        obj6.setId(6L);
        obj6.setFechaDeCita(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj6.setHoraDeCita("09:00");
        obj6.setIdCentroDeAtencion(2L);
        obj6.setMotivoConsulta("Formación");
        obj6.setExplicacionConsulta("Preinscripción diversos cursos");
        obj6.setIdCurso(1L);
        obj6.setIdAlumno(1L);
        obj6.setEstado("pendiente");
        repoCita.save(obj6);

        CitaEntity obj7 = new CitaEntity();
        obj7.setId(7L);
        obj7.setFechaDeCita(general.getFechaCercana((int) (Math.random() * 10 - 5)));
        obj7.setHoraDeCita("10:00");
        obj7.setIdCentroDeAtencion(1L);
        obj7.setMotivoConsulta("Ayudas Económicas");
        obj7.setExplicacionConsulta("Solicitud ayudas");
        obj7.setIdCurso(1L);
        obj7.setIdAlumno(1L);
        obj7.setEstado("aceptado");
        repoCita.save(obj7);

        CitaEntity obj8 = new CitaEntity();
        obj8.setId(8L);
        obj8.setFechaDeCita(getDateRandom());
        obj8.setHoraDeCita("11:00");
        obj8.setIdCentroDeAtencion(2L);
        obj8.setMotivoConsulta("Formación");
        obj8.setExplicacionConsulta("Prueba de nivel de español");
        obj8.setIdCurso(1L);
        obj8.setIdAlumno(3L);
        obj8.setEstado("pendiente");
        repoCita.save(obj8);

        CitaEntity obj9 = new CitaEntity();
        obj9.setId(9L);
        obj9.setFechaDeCita(getDateRandom());
        obj9.setHoraDeCita("12:00");
        obj9.setIdCentroDeAtencion(2L);
        obj9.setMotivoConsulta("Formación");
        obj9.setExplicacionConsulta("Prueba de nivel de español");
        obj9.setIdCurso(1L);
        obj9.setIdAlumno(2L);
        obj9.setEstado("rechazado");
        repoCita.save(obj9);

        CitaEntity obj10 = new CitaEntity();
        obj10.setId(10L);
        obj10.setFechaDeCita(getDateRandom());
        obj10.setHoraDeCita("13:00");
        obj10.setIdCentroDeAtencion(2L);
        obj10.setMotivoConsulta("Orientación");
        obj10.setExplicacionConsulta("Asesoramiento cursos de capacitación español");
        obj10.setIdCurso(1L);
        obj10.setIdAlumno(1L);
        obj10.setEstado("aceptado");
        repoCita.save(obj10);

    }

    private void crear3Profesores() {
        UsuarioEntity usu1 = new UsuarioEntity();
        usu1.setId(11L);
        usu1.setUsuario("doc_profesor");
        usu1.setPassword(passEnc.encode("1111"));
        usu1.setEstado("Aceptado");
        usu1.setRol("ROLE_DOC_Profesor");
        usu1.setCodigoVerificacion(UUID.randomUUID().toString());;
        usu1=repoUsuario.save(usu1);
        ProfesorEntity obj1 = new ProfesorEntity();
        obj1.setIdUsuario(usu1.getId());
        obj1.setNombreProf("Juan");
        obj1.setApellido1Prof("Saura");
        obj1.setApellido2Prof("Martín");
        repoProfesor.save(obj1);

        UsuarioEntity usu2 = new UsuarioEntity();
        usu2.setUsuario("miguel");
        usu2.setPassword(passEnc.encode("1111"));
        usu2.setEstado("Aceptado");
        usu2.setRol("ROLE_DOC_Profesor");
        usu2.setCodigoVerificacion(UUID.randomUUID().toString());;
        usu2=repoUsuario.save(usu2);
        ProfesorEntity obj2 = new ProfesorEntity();
        obj2.setIdUsuario(usu2.getId());
        obj2.setNombreProf("Miguel");
        obj2.setApellido1Prof("Calvo");
        obj2.setApellido2Prof("Huguet");
        repoProfesor.save(obj2);

        UsuarioEntity usu3 = new UsuarioEntity();
        usu3.setUsuario("maria");
        usu3.setPassword(passEnc.encode("1111"));
        usu3.setEstado("aceptado");
        usu3.setRol("ROLE_DOC_Profesor");
        usu3.setCodigoVerificacion(UUID.randomUUID().toString());;
        usu3=repoUsuario.save(usu3);
        ProfesorEntity obj3 = new ProfesorEntity();
        obj3.setIdUsuario(usu3.getId());
        obj3.setNombreProf("María");
        obj3.setApellido1Prof("Guara");
        obj3.setApellido2Prof("Liminiana");
        repoProfesor.save(obj3);

    }

    private void crear4ParticipacionProfesorCurso() {
        ParticipacionProfesorCursoEntity obj1 = new ParticipacionProfesorCursoEntity();
        obj1.setId(1L);
        obj1.setIdProfesor(2L);
        obj1.setIdCurso(3L);
        repoParticipacionProfesorCurso.save(obj1);

        ParticipacionProfesorCursoEntity obj2 = new ParticipacionProfesorCursoEntity();
        obj2.setId(2L);
        obj2.setIdProfesor(1L);
        obj2.setIdCurso(1L);
        repoParticipacionProfesorCurso.save(obj2);

        ParticipacionProfesorCursoEntity obj3 = new ParticipacionProfesorCursoEntity();
        obj3.setId(3L);
        obj3.setIdProfesor(3L);
        obj3.setIdCurso(2L);
        repoParticipacionProfesorCurso.save(obj3);

        ParticipacionProfesorCursoEntity obj4 = new ParticipacionProfesorCursoEntity();
        obj4.setId(4L);
        obj4.setIdProfesor(2L);
        obj4.setIdCurso(4L);
        repoParticipacionProfesorCurso.save(obj4);

    }

    private void NotificacionAlumno() {

        NotificacionEntity not1 = new NotificacionEntity();
        not1.setId(1L);
        not1.setIdAlumno(1L);
        not1.setFechaNotificacion(getDateRandom());
        not1.setHoraNotificacion("12:00");
        not1.setFechaActualizacion(getDateRandom());
        not1.setTextoNotificacion("Clase del lunes cancelada por enfermedad del profesor");
        not1.setLeido("Leído");
        not1.setUrlLink("www.tecnara.com");
        repoNotificacion.save(not1);

        NotificacionEntity not2 = new NotificacionEntity();
        not2.setId(2L);
        not2.setIdAlumno(1L);
        not2.setFechaNotificacion(getDateRandom());
        not2.setHoraNotificacion("11:00");
        not2.setFechaActualizacion(getDateRandom());
        not2.setTextoNotificacion("Recordatorio entrenamiento viernes");
        not2.setLeido("No leído");
        not2.setUrlLink("www.tecnara.es");
        repoNotificacion.save(not2);

        NotificacionEntity not3 = new NotificacionEntity();
        not3.setId(3L);
        not3.setIdAlumno(1L);
        not3.setFechaNotificacion(getDateRandom());
        not3.setHoraNotificacion("09:45");
        not3.setFechaActualizacion(getDateRandom());
        not3.setTextoNotificacion("Notas académicas");
        not3.setLeido("Leído");
        not3.setUrlLink("www.clustertecnara.com");
        repoNotificacion.save(not3);

        NotificacionEntity not4 = new NotificacionEntity();
        not4.setId(1L);
        not4.setIdAlumno(1L);
        not4.setFechaNotificacion(getDateRandom());
        not4.setHoraNotificacion("15:00");
        not4.setFechaActualizacion(getDateRandom());
        not4.setTextoNotificacion("Clase del lunes retrasada por cambio de hora");
        not4.setLeido("No");
        not4.setUrlLink("www.tecnara.com");
        repoNotificacion.save(not4);

        NotificacionEntity not5 = new NotificacionEntity();
        not5.setId(2L);
        not5.setIdAlumno(1L);
        not5.setFechaNotificacion(getDateRandom());
        not5.setHoraNotificacion("09:00");
        not5.setFechaActualizacion(getDateRandom());
        not5.setTextoNotificacion("Recordatorio entrenamiento viernes");
        not5.setLeido("No");
        not5.setUrlLink("www.tecnara.es");

        repoNotificacion.save(not5);

    }

    private void crearNotificacionLeidoNoAlumno() {
        NotificacionEntity not4 = new NotificacionEntity();
        not4.setId(1L);
        not4.setIdAlumno(1L);
        not4.setFechaNotificacion(getDateRandom());
        not4.setHoraNotificacion("15:00");
        not4.setFechaActualizacion(getDateRandom());
        not4.setTextoNotificacion("Clase del lunes retrasada por cambio de hora");
        not4.setLeido("No");
        not4.setUrlLink("www.tecnara.com");
        repoNotificacion.save(not4);

        NotificacionEntity not5 = new NotificacionEntity();
        not5.setId(2L);
        not5.setIdAlumno(1L);
        not5.setFechaNotificacion(getDateRandom());
        not5.setHoraNotificacion("09:00");
        not5.setFechaActualizacion(getDateRandom());
        not5.setTextoNotificacion("Recordatorio entrenamiento viernes");
        not5.setLeido("No");
        not5.setUrlLink("www.tecnara.es");

    }

}