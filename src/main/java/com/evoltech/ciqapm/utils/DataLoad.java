package com.evoltech.ciqapm.utils;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.*;
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
    PasswordEncoder passwordEncoder;

    @Autowired RandomService randomService;

    public DataLoad(PersonalRepository repository, ClienteRepository clienteRepository,
                    ServicioRepository servicioRepository, ProyectoRepository proyectoRepository,
                    EtapaRepository etapaRepository, DocumentoRepository documentoRepository,
                    UsuarioRepository usuarioRepository, ActividadRepository actividadRepository,
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

        LocalDate startDate = LocalDate.of(2024, 5, 10 );
        Etapa etapa1 = creaEtapa(personal1, proyecto1, servicio1, startDate);
        Etapa etapa2 = creaEtapa(personal2, proyecto1, servicio2, startDate.plusDays(15));
        Etapa etapa3 = creaEtapa(personal3, proyecto1, servicio3, startDate.plusDays(25));
        Etapa etapa4 = creaEtapa(personal1, proyecto1, servicio1, startDate.plusDays(12));
        Etapa etapa5 = creaEtapa(personal2, proyecto1, servicio2, startDate.plusDays(10));
        Etapa etapa6 = creaEtapa(personal3, proyecto1, servicio1, startDate.plusDays(40));
        Etapa etapa7 = creaEtapa(personal4, proyecto1, servicio2, startDate.plusDays(60));
        Etapa etapa8 = creaEtapa(personal5, proyecto1, servicio3, startDate.plusDays(90));

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
        proyecto.setResponsable(responsable);
        proyecto.setTipoProyecto(randomService.generaProyectoTipo());
        proyecto.setNombre("Proyecto-" + post);
        proyecto.setDescripcion(randomService.descripcion(250));
        proyecto.setCliente(cliente);
        proyecto.setEstatus(Estado.PROCESO);

        Proyecto res = proyectoRepository.save(proyecto);
        return res;
    }

    private Etapa creaEtapa(Personal responsable, Proyecto proyecto, Servicio servicio,
                            LocalDate startDate) {
        System.out.println("Proyecto donde se agragara la etapa: " + proyecto.getNombre());
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

}
