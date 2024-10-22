package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosIndustria;
import com.evoltech.ciqapm.model.jpa.Convocatoria;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.repository.datos.IndustriaRepository;
import com.evoltech.ciqapm.security.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DataLoad {
    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ActividadRepository actividadRepository;

    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    IndustriaRepository industriaRepository;

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired RandomService randomService;

    public DataLoad(PersonalRepository repository, ClienteRepository clienteRepository,
                    ServicioRepository servicioRepository, ProyectoRepository proyectoRepository,
                    EtapaRepository etapaRepository, DocumentoRepository documentoRepository,
                    UsuarioRepository usuarioRepository, ActividadRepository actividadRepository,
                    ConvocatoriaRepository convocatoriaRepository,
                    PagoRepository pagoRepository,
                    IndustriaRepository industriaRepository,
                    AlumnoRepository alumnoRepository,
                    PasswordEncoder passwordEncoder) {

        this.personalRepository = repository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.documentoRepository = documentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.actividadRepository = actividadRepository;
        this.passwordEncoder = passwordEncoder;
        this.pagoRepository =  pagoRepository;
        this.alumnoRepository = alumnoRepository;
        this.convocatoriaRepository = convocatoriaRepository;
    }

    public void initializa() {
        System.out.println("Inicializando la base...");
        Personal personal1 = createPersonal(PersonalCategoria.ITA);
        Personal personal2 = createPersonal(PersonalCategoria.ITB);
        Personal personal3 = createPersonal(PersonalCategoria.ITC);
        Personal personal4 = createPersonal(PersonalCategoria.TTA);
        Personal personal5 = createPersonal(PersonalCategoria.TTB);
        Personal personal6 = createPersonal(PersonalCategoria.TTC);

        Cliente cliente1 = createCliente("100");
        Cliente cliente2 = createCliente("101");
        Cliente cliente3 = createCliente("102");
        Cliente cliente4 = createCliente("103");
        Cliente cliente5 = createCliente("104");
        Cliente cliente6 = createCliente("105");

        Servicio servicio1 = createServicio();
        Servicio servicio2 = createServicio();
        Servicio servicio3 = createServicio();
        Servicio servicio4 = createServicio();
        Servicio servicio5 = createServicio();

        Proyecto proyecto1 = createProyecto("1000", personal1, cliente1);
        Proyecto proyecto2 = createProyecto("1001", personal1, cliente2);
        Proyecto proyecto3 = createProyecto("1003", personal2, cliente3);
        Proyecto proyecto4 = createProyecto("1002", personal3, cliente4);
        Proyecto proyecto5 = createProyecto("1004", personal4, cliente5);

        Proyecto proyecto6 = createProyectoConahcyt("1005", personal4, cliente5);
        Proyecto proyecto7 = createProyectoConahcyt("1006", personal4, cliente5);

        Proyecto proyecto8 = createProyectoIndustria("1007", personal1, cliente5);
        Proyecto proyecto9 = createProyectoIndustria("1008", personal1, cliente5);

        Proyecto proyecto10 = createProyectoInternos("1009", personal3, cliente5);
        Proyecto proyecto11 = createProyectoInternos("1010", personal2, cliente5);

        Proyecto proyecto12 = createProyectoPosgrado("1011", personal2, cliente5);
        Proyecto proyecto13 = createProyectoPosgrado("1012", personal2, cliente5);

        LocalDate startDate = LocalDate.of(2024, 5, 10 );
        Etapa etapa1 = creaEtapa(personal1, proyecto1, servicio1, startDate);
        proyecto1.addEtapa(etapa1);
        Etapa etapa2 = creaEtapa(personal2, proyecto1, servicio2, startDate.plusDays(15));
        proyecto1.addEtapa(etapa2);
        Etapa etapa3 = creaEtapa(personal3, proyecto1, servicio3, startDate.plusDays(25));
        proyecto1.addEtapa(etapa3);
        Etapa etapa4 = creaEtapa(personal1, proyecto1, servicio1, startDate.plusDays(12));
        proyecto1.addEtapa(etapa4);
        Etapa etapa5 = creaEtapa(personal2, proyecto1, servicio2, startDate.plusDays(10));
        proyecto1.addEtapa(etapa5);
        Etapa etapa6 = creaEtapa(personal3, proyecto1, servicio1, startDate.plusDays(40));
        proyecto1.addEtapa(etapa6);
        Etapa etapa7 = creaEtapa(personal4, proyecto1, servicio2, startDate.plusDays(60));
        proyecto1.addEtapa(etapa7);
        Etapa etapa8 = creaEtapa(personal5, proyecto1, servicio3, startDate.plusDays(90));
        proyecto1.addEtapa(etapa8);
        proyectoRepository.save(proyecto1);

        Documento doc1 = creaDocumento("primer documento", proyecto1);
        Documento doc2 = creaDocumento("segundo documento", proyecto1);
        Documento doc3 = creaDocumento("tercer documento", proyecto1);
        Documento doc4 = creaDocumento("cuarto documento", proyecto2);
        Documento doc5 = creaDocumento("quinto documento", proyecto2);
        Documento doc6 = creaDocumento("sexto documento", proyecto3);

        Usuario usr1 = creaUsuario("user01", "pass01");
        Usuario usr2 = creaUsuario("user02", "pass02");
        Usuario usr3 = creaUsuario("user03", "pass03");


        Actividad actividad1 = creaActividad(etapa1, personal1);
        Actividad actividad2 = creaActividad(etapa2, personal1);
        Actividad actividad3 = creaActividad(etapa3, personal1);
        Actividad actividad4 = creaActividad(etapa2, personal2);
        Actividad actividad5 = creaActividad(etapa1, personal1);
        Actividad actividad6 = creaActividad(etapa3, personal1);
        Actividad actividad7 = creaActividad(etapa1, personal1);
        Actividad actividad8 = creaActividad(etapa2, personal1);

        startDate = LocalDate.of(2024, 7, 10 );
        Etapa etapa1_p2 = creaEtapa(personal1, proyecto2, servicio1, startDate);
        Etapa etapa2_p2 = creaEtapa(personal2, proyecto2, servicio2, startDate.plusDays(20));
        Etapa etapa3_p2 = creaEtapa(personal3, proyecto2, servicio3, startDate.plusDays(12));
        Etapa etapa4_p2 = creaEtapa(personal1, proyecto2, servicio1, startDate.plusDays(14));
        Etapa etapa5_p2 = creaEtapa(personal2, proyecto2, servicio2, startDate.plusDays(30));
        Etapa etapa6_p2 = creaEtapa(personal3, proyecto2, servicio1, startDate.plusDays(28));
        Etapa etapa7_p2 = creaEtapa(personal4, proyecto2, servicio2, startDate.plusDays(40));
        Etapa etapa8_p8 = creaEtapa(personal5, proyecto2, servicio3, startDate.plusDays(10));
        proyecto2.addEtapa(etapa1_p2);
        proyecto2.addEtapa(etapa2_p2);
        proyecto2.addEtapa(etapa3_p2);
        proyecto2.addEtapa(etapa4_p2);
        proyecto2.addEtapa(etapa5_p2);
        proyecto2.addEtapa(etapa6_p2);
        proyecto2.addEtapa(etapa7_p2);
        proyectoRepository.save(proyecto2);

        startDate = LocalDate.of(2025, 2, 10 );
        Etapa etapa1_p3 = creaEtapa(personal1, proyecto2, servicio1, startDate);
        Etapa etapa2_p3 = creaEtapa(personal2, proyecto2, servicio2, startDate.plusDays(15));
        Etapa etapa3_p3 = creaEtapa(personal3, proyecto2, servicio3, startDate.plusDays(20));
        Etapa etapa4_p3 = creaEtapa(personal1, proyecto2, servicio1, startDate.plusDays(18));
        Etapa etapa5_p3 = creaEtapa(personal2, proyecto2, servicio2, startDate.plusDays(14));
        Etapa etapa6_p3 = creaEtapa(personal3, proyecto2, servicio1, startDate.plusDays(44));
        Etapa etapa7_p3 = creaEtapa(personal4, proyecto2, servicio2, startDate.plusDays(50));
        Etapa etapa8_p3 = creaEtapa(personal5, proyecto2, servicio3, startDate.plusDays(90));

        startDate = LocalDate.of(2024, 9, 12 );
        Etapa etapa1_p4 = creaEtapa(personal1, proyecto2, servicio1, startDate);
        Etapa etapa2_p4 = creaEtapa(personal2, proyecto2, servicio2, startDate.plusDays(15));
        Etapa etapa3_p4 = creaEtapa(personal3, proyecto2, servicio3, startDate.plusDays(25));
        Etapa etapa4_p4 = creaEtapa(personal1, proyecto2, servicio1, startDate.plusDays(12));
        Etapa etapa5_p4 = creaEtapa(personal2, proyecto2, servicio2, startDate.plusDays(10));
        Etapa etapa6_p4 = creaEtapa(personal3, proyecto2, servicio1, startDate.plusDays(40));
        Etapa etapa7_p4 = creaEtapa(personal4, proyecto2, servicio2, startDate.plusDays(60));
        Etapa etapa8_p4 = creaEtapa(personal5, proyecto2, servicio3, startDate.plusDays(90));


        LocalDate stDate = proyecto1.getCreateDate();
        Pago pago = createPago(proyecto1, stDate.plusDays(10), 30);
        pago = createPago(proyecto1, stDate.plusDays(60), 30);
        pago = createPago(proyecto1, stDate.plusDays(90), 40);


        stDate = proyecto2.getCreateDate();
        pago = createPago(proyecto2, stDate.plusDays(30), 20);
        pago = createPago(proyecto2, stDate.plusDays(60), 30);
        pago = createPago(proyecto2, stDate.plusDays(120), 50);


        Convocatoria convocatoria = createConvocatoria("Primer convocatoria");
        convocatoria = createConvocatoria("Segunda Convocatoria");
        convocatoria = createConvocatoria("Tercer Convocatoria");
        convocatoria = createConvocatoria("Cuarta Convocatoria");

        Alumno alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();
        alumno = createAlumno();

        System.out.println("Database Inicializada ...");

    }

    private Personal createPersonal(PersonalCategoria categoria) {
        Personal personal = new Personal();
        personal.setNombre(randomService.nombre());
        personal.setCategoria(randomService.generaPersonalCategoria());
        personal.setRate(new BigDecimal("10.30"));
        personal.setUserUpdate("Update user- ");
        personal.setCreateUser("Create user- ");

        Personal res = personalRepository.save(personal);
        return res;
    }

    private Cliente createCliente(String post) {
        Cliente cliente = new Cliente();
        cliente.setEmail(randomService.email());
        cliente.setNombre(randomService.empresa());
        cliente.setNombreContacto(randomService.nombre());
        cliente.setRfc(randomService.rfc());
        cliente.setTelefono("+52 " + randomService.telefono());

        Cliente res = clienteRepository.save(cliente);
        return res;
    }

    private Servicio createServicio() {
        Servicio servicio = new Servicio();
        servicio.setCosto(randomService.generaCostoRandom());
        servicio.setEntregableEsperado(randomService.etapaEntregable());
        servicio.setDescripcion(randomService.descripcion(30));
        servicio.setNombre(randomService.etapaNombre());
        servicio.setHorasPromedioRealizacion(randomService.avances());

        Servicio res = servicioRepository.save(servicio);
        return res;
    }

    private Proyecto createProyecto(String post, Personal responsable, Cliente cliente) {
        Proyecto proyecto = new Proyecto();
        DatosIndustria datosIndustria = new DatosIndustria();
        datosIndustria.setCliente(cliente);
        datosIndustria.setPresupuesto(randomService.generaCostoRandom());

        proyecto.setResponsable(responsable);
        proyecto.setTipoProyecto(randomService.generaProyectoTipo());
        proyecto.setNombre("Proyecto-" + post);
        proyecto.setDescripcion(randomService.descripcion(250));
        proyecto.setEstatus(Estado.PROCESO);

        Proyecto res = proyectoRepository.save(proyecto);

        datosIndustria.setProyecto(res);
        DatosIndustria resDatosIndustria = industriaRepository.save(datosIndustria);

        return res;
    }

    private Proyecto createProyectoConahcyt(String post, Personal responsable, Cliente cliente) {
        Proyecto proyecto = new Proyecto();
        DatosIndustria datosIndustria = new DatosIndustria();
        datosIndustria.setCliente(cliente);
        datosIndustria.setPresupuesto(randomService.generaCostoRandom());

        proyecto.setResponsable(responsable);
        proyecto.setTipoProyecto(TipoProyecto.CONAHCYT);
        proyecto.setNombre("Proyecto-" + post);
        proyecto.setDescripcion(randomService.descripcion(250));
        proyecto.setEstatus(Estado.PROCESO);

        Proyecto res = proyectoRepository.save(proyecto);

        datosIndustria.setProyecto(res);
        DatosIndustria resDatosIndustria = industriaRepository.save(datosIndustria);

        return res;
    }

    private Proyecto createProyectoIndustria(String post, Personal responsable, Cliente cliente) {
        Proyecto proyecto = new Proyecto();
        DatosIndustria datosIndustria = new DatosIndustria();
        datosIndustria.setCliente(cliente);
        datosIndustria.setPresupuesto(randomService.generaCostoRandom());

        proyecto.setResponsable(responsable);
        proyecto.setTipoProyecto(TipoProyecto.INDUSTRIA);
        proyecto.setNombre("Proyecto-" + post);
        proyecto.setDescripcion(randomService.descripcion(250));
        proyecto.setEstatus(Estado.PROCESO);

        Proyecto res = proyectoRepository.save(proyecto);

        datosIndustria.setProyecto(res);
        DatosIndustria resDatosIndustria = industriaRepository.save(datosIndustria);
        return res;
    }

    private Proyecto createProyectoInternos(String post, Personal responsable, Cliente cliente) {
        Proyecto proyecto = new Proyecto();
        DatosIndustria datosIndustria = new DatosIndustria();
        datosIndustria.setCliente(cliente);
        datosIndustria.setPresupuesto(randomService.generaCostoRandom());

        proyecto.setResponsable(responsable);
        proyecto.setTipoProyecto(TipoProyecto.INTERNOS);
        proyecto.setNombre("Proyecto-" + post);
        proyecto.setDescripcion(randomService.descripcion(250));
        proyecto.setEstatus(Estado.PROCESO);

        Proyecto res = proyectoRepository.save(proyecto);

        datosIndustria.setProyecto(res);
        DatosIndustria resDatosIndustria = industriaRepository.save(datosIndustria);

        return res;
    }

    private Proyecto createProyectoPosgrado(String post, Personal responsable, Cliente cliente) {
        Proyecto proyecto = new Proyecto();
        DatosIndustria datosIndustria = new DatosIndustria();
        datosIndustria.setCliente(cliente);
        datosIndustria.setPresupuesto(randomService.generaCostoRandom());

        proyecto.setResponsable(responsable);
        proyecto.setTipoProyecto(TipoProyecto.POSTGRADO);
        proyecto.setNombre("Proyecto-" + post);
        proyecto.setDescripcion(randomService.descripcion(250));
        proyecto.setEstatus(Estado.PROCESO);

        Proyecto res = proyectoRepository.save(proyecto);

        datosIndustria.setProyecto(res);
        DatosIndustria resDatosIndustria = industriaRepository.save(datosIndustria);

        return res;
    }

    private Etapa creaEtapa(Personal responsable, Proyecto proyecto, Servicio servicio,
                            LocalDate startDate) {
        Etapa etapa = new Etapa();
        etapa.setResponsable(responsable);
        etapa.setProyecto(proyecto);
        etapa.setEntregable(randomService.etapaEntregable());
        etapa.setNombre(randomService.etapaNombre());
        etapa.setDescripcion(randomService.descripcion(120));
        LocalDate fechaInicio = randomService.fechaConOffset(startDate);
        etapa.setFechaEstimadaInicio(fechaInicio);
        etapa.setFechaEstimadaTerminacion(randomService.fechaConOffset(fechaInicio));
        etapa.setServicio(servicio);
        etapa.setPctCompleto(randomService.avances());
        etapa.setEstado(Estado.PROCESO);

        Etapa res = etapaRepository.save(etapa);

        return res;
    }

    private Documento creaDocumento(String nombre, Proyecto proyecto) {
        Documento documento = new Documento();
        documento.setDescripcion(randomService.descripcion(100));
        documento.setNombre(nombre);
        documento.setNombreArchivo(randomService.etapaEntregable());
        documento.setProyecto(proyecto);

        Documento res = documentoRepository.save(documento);
        return res;
    }

    private Usuario creaUsuario(String username, String password) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setEmail("email@test.com");

        Usuario res = usuarioRepository.save(usuario);
        return res;
    }

    private Actividad creaActividad(Etapa etapa, Personal personal) {
        Actividad actividad = new Actividad();
        actividad.setNombre("Actividad de prueba");
        actividad.setDescripcion(randomService.descripcion(120));
        actividad.setEstado(ActividadEstado.PROCESO);
        actividad.setEtapa(etapa);
        actividad.setFechaInicio(LocalDate.of(2024, 10, 14));
        actividad.setHoras(40);
        actividad.setRealizadoPor(personal);

        Actividad res = actividadRepository.save(actividad);
        return res;
    }

    private Pago createPago(Proyecto proyecto, LocalDate ld, int pct){
        Pago pago = new Pago();
        pago.setId(0L);
        pago.setFechaFacturacion(ld);
        pago.setEstado(PagosEstado.PENDIENTE);
        pago.setPorcentajePago(pct);
        pago.setProyecto(proyecto);

        Pago res = pagoRepository.save(pago);
        return res;
    }

    private Convocatoria createConvocatoria(String nombre){
        Convocatoria convocatoria = new Convocatoria();
        convocatoria.setNombre(nombre);
        convocatoria.setDescripcion("Descripción de la convocatoria " + nombre);
        convocatoria.setInicioVigencia(LocalDate.now());
        convocatoria.setTerminoVigencia(LocalDate.now().plusMonths(10));

        Convocatoria res = convocatoriaRepository.save(convocatoria);
        return res;
    }

    private Alumno createAlumno(){
        Alumno alumno = new Alumno();
        alumno.setNombre(randomService.nombre());
        alumno.setMatricula(randomService.rfc());
        alumno.setEmail(randomService.email());
        Alumno res = alumnoRepository.save(alumno);
        return res;
    }

}
