package com.evoltech.ciqapm;

import com.evoltech.ciqapm.config.StorageProperties;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.Cliente;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.security.Usuario;
import com.evoltech.ciqapm.service.FileSystemStorageService;
import com.evoltech.ciqapm.service.StorageService;
import com.evoltech.ciqapm.utils.DataLoad;
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
	DataLoad dataLoad;

	@Override
	public void run(String... args) throws Exception {

		dataLoad.initializa();

	}

	@Bean
	public StorageProperties storageProperties() {
		return new StorageProperties();
	}

}
