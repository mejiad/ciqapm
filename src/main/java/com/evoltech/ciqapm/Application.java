package com.evoltech.ciqapm;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.Cliente;
import com.evoltech.ciqapm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Date;

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

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inicializando la base...");
		createPersonal("01", PersonalCategoria.ITA);;
		createPersonal("02", PersonalCategoria.ITB);;
		createPersonal("03", PersonalCategoria.ITC);;
		createPersonal("04", PersonalCategoria.TTA);;
		createPersonal("05", PersonalCategoria.TTB);;
		Personal personal = createPersonal("06", PersonalCategoria.TTC);;

		createCliente("100");
		createCliente("101");
		createCliente("102");
		createCliente("103");
		Cliente cliente = createCliente("104");
		createCliente("105");

		createServicio("201");
		createServicio("202");
		Servicio servicio = createServicio("203");
		createServicio("204");
		createServicio("205");

		createProyecto("1000",personal, cliente);
		createProyecto("1001",personal, cliente);
		createProyecto("1002",personal, cliente);
		Proyecto proyecto = createProyecto("1003",personal, cliente);
		createProyecto("1004",personal, cliente);

		creaEtapa(personal, proyecto, servicio);

	}

	private Personal createPersonal(String post, PersonalCategoria categoria) {
		Personal personal = new Personal();
		personal.setId(0L);
		personal.setNombre("Nombre_" + post );
		personal.setApellidos("Apellidos-" +post);
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

	private Servicio createServicio(String post){
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

	private Proyecto  createProyecto(String post, Personal responsable, Cliente cliente){
		Proyecto proyecto = new Proyecto();
		proyecto.setResponsable(responsable);
		proyecto.setTipoProyecto(TipoProyecto.INDUSTRIA);
		proyecto.setNombre("Proyecto-" + post);
		proyecto.setDescripcion("Descipcion del proyecto Proyecto-" + post);
		// proyecto.setEtapas(etapas);
		proyecto.setId(0L);
		proyecto.setCliente(cliente);
		proyecto.setCreateUser("Create User");
		proyecto.setStatus("Status del proyecto"); // TODO: crear status

		Proyecto res = proyectoRepository.save(proyecto);
		return res;
	}

	private Etapa creaEtapa(Personal responsable, Proyecto proyecto, Servicio servicio) {
		Etapa etapa = new Etapa();
		etapa.setResponsable(responsable);
		etapa.setFechaEstimadaTerminacion(new Date("02/03/2025"));
		etapa.setProyecto(proyecto);
		etapa.setEntregable("Entregable de la etapa");
		etapa.setNombre("Etapa del proyecto ");
		etapa.setDescripcion("Etapa para hacer lo que el cliente quiere");
		etapa.setFechaEstimadaInicio(new Date("01/20/2024"));
		etapa.setFechaEstimadaTerminacion(new Date("02/02/2024"));
		etapa.setServicio(servicio);
		etapa.setStatus("Status de la etapa"); // TODO: crear data type
		etapa.setId(0L);

		Etapa res = etapaRepository.save(etapa);
		return res;
	}

	private Documento creaDocumento(Proyecto proyecto){
		Documento documento = new Documento();
		documento.setDescripcion("Descipción corta del documento");
		documento.setNombre("Cotización, propuesta, reporte, etc.");
		documento.setNombreArchivo("Propuesta del cliente x");

		Documento res = documentoRepository.save(documento);
		return res;
	}
}
