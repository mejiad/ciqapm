package com.evoltech.ciqapm;

import com.evoltech.ciqapm.config.StorageProperties;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.Cliente;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.security.Usuario;
import com.evoltech.ciqapm.service.FileSystemStorageService;
import com.evoltech.ciqapm.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	PersonalRepository repository;

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

	Random r = new Random();

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Inicializando la base...");
		Personal personal1 = createPersonal("01", PersonalCategoria.ITA);
		;
		Personal personal2 = createPersonal("02", PersonalCategoria.ITB);
		;
		Personal personal3 = createPersonal("03", PersonalCategoria.ITC);
		;
		Personal personal4 = createPersonal("04", PersonalCategoria.TTA);
		;
		Personal personal5 = createPersonal("05", PersonalCategoria.TTB);
		;
		Personal personal6 = createPersonal("06", PersonalCategoria.TTC);
		;

		Cliente cliente1 = createCliente("100");
		Cliente cliente2 = createCliente("101");
		Cliente cliente3 = createCliente("102");
		Cliente cliente4 = createCliente("103");
		Cliente cliente5 = createCliente("104");
		Cliente cliente6 = createCliente("105");

		Servicio servicio1 = createServicio("201");
		Servicio servicio2 = createServicio("202");
		Servicio servicio3 = createServicio("203");
		Servicio servicio4 = createServicio("204");
		Servicio servicio5 = createServicio("205");

		Proyecto proyecto1 = createProyecto("1000", personal1, cliente1);
		Proyecto proyecto2 = createProyecto("1001", personal1, cliente2);
		Proyecto proyecto3 = createProyecto("1003", personal2, cliente3);
		Proyecto proyecto4 = createProyecto("1002", personal3, cliente4);
		Proyecto proyecto5 = createProyecto("1004", personal4, cliente5);

		Etapa etapa1 = creaEtapa(personal1, proyecto1, servicio1);
		Etapa etapa2 = creaEtapa(personal2, proyecto2, servicio2);
		Etapa etapa3 = creaEtapa(personal3, proyecto3, servicio3);

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

		System.out.println("Database Inicializada ...");

	}

	private Personal createPersonal(String post, PersonalCategoria categoria) {
		Personal personal = new Personal();
		personal.setId(0L);
		personal.setNombre("Nombre_" + post);
		personal.setApellidos("Apellidos-" + post);
		personal.setCategoria(categoria);
		personal.setRate(new BigDecimal("10.30"));
		personal.setUserUpdate("Update user-" + post);
		personal.setUpdateDate(new Date("01/01/2024"));
		personal.setCreateUser("Create user-" + post);
		personal.setCreateDate(new Date("02/02/2023"));

		Personal res = repository.save(personal);
		return res;
	}

	private Cliente createCliente(String post) {
		Cliente cliente = new Cliente();
		cliente.setEmail("email@test-" + post + ".com");
		cliente.setNombre("Cliente-" + post);
		cliente.setEmail("email-");
		cliente.setNombreContacto("Contacto-" + post);
		cliente.setRfc("RFC-" + post);
		cliente.setTelefono("+52 5522334455" + post);

		Cliente res = clienteRepository.save(cliente);
		return res;
	}

	private Servicio createServicio(String post) {
		Servicio servicio = new Servicio();
		servicio.setCosto(new BigDecimal("23.12"));
		servicio.setEntregableEsperado("Reporte del servicio");
		servicio.setDescripcion("La descripcion del servicio");
		servicio.setNombre("Servicio-" + post);
		servicio.setId(0L);
		servicio.setHorasPromedioRealizacion(200);
		servicio.setCreateUser("Create user");
		servicio.setUserUpdate("Update user");

		Servicio res = servicioRepository.save(servicio);
		return res;
	}

	private Proyecto createProyecto(String post, Personal responsable, Cliente cliente) {
		Proyecto proyecto = new Proyecto();
		proyecto.setResponsable(responsable);
		proyecto.setTipoProyecto(TipoProyecto.INDUSTRIA);
		proyecto.setNombre("Proyecto-" + post);
		proyecto.setDescripcion("Descipcion del proyecto Proyecto-" + post + " donde se definen los detalles que debe cumplir este proyecto para su terminación");
		// proyecto.setEtapas(etapas);
		proyecto.setCliente(cliente);
		proyecto.setCreateUser("Create User");
		proyecto.setStatus("Status del proyecto"); // TODO: crear status

		Proyecto res = proyectoRepository.save(proyecto);
		return res;
	}

	private Etapa creaEtapa(Personal responsable, Proyecto proyecto, Servicio servicio) {
		System.out.println("Proyecto donde se agragara la etapa: " + proyecto.getNombre());
		Etapa etapa = new Etapa();
		etapa.setResponsable(responsable);
		etapa.setFechaEstimadaTerminacion(LocalDate.of(2025, 02, 03));
		etapa.setProyecto(proyecto);
		etapa.setEntregable("Entregable de la etapa");
		etapa.setNombre("Etapa del proyecto ");
		etapa.setDescripcion("Etapa para hacer lo que el cliente quiere");
		etapa.setFechaEstimadaInicio(LocalDate.of(2024, 1, 20));
		etapa.setFechaEstimadaTerminacion((LocalDate.of(2024, 02, 02)));
		etapa.setServicio(servicio);
		etapa.setStatus("Status de la etapa"); // TODO: crear data type
		etapa.setPctCompleto(nextRandom(10, 80));
		// etapa.setId(0L);

		Etapa res = etapaRepository.save(etapa);
		/*
		res.setProyecto(proyecto);
		res = etapaRepository.save(etapa);
		 */

		return res;
	}

	private Documento creaDocumento(String nombre, Proyecto proyecto) {
		Documento documento = new Documento();
		documento.setDescripcion("Descripción corta del documento " + nombre);
		documento.setNombre(nombre);
		documento.setNombreArchivo("Propuesta del cliente");

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
		actividad.setDescripcion("Descrición de la actividad realizada...");
		actividad.setEstado(ActividadEstado.PROCESO);
		actividad.setEtapa(etapa);
		actividad.setFechaInicio(LocalDate.of(2024, 10, 14));
		actividad.setFechaTerminacion(LocalDate.of(2024, 11, 10));
		actividad.setHorasUtilizadas(40);
		actividad.setRealizadoPor(personal);

		Actividad res = actividadRepository.save(actividad);
		return res;
	}

	@Bean
	public StorageProperties storageProperties() {
		return new StorageProperties();
	}

	private int nextRandom(int low, int high) {
		int result = r.nextInt(high - low) + low;
		return result;
	}

}
