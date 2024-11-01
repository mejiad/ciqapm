package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.config.StorageProperties;
import com.evoltech.ciqapm.exceptions.StorageException;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DatabaseStorageService implements StorageService{
    private static final Logger log = LoggerFactory.getLogger(DatabaseStorageService.class);

    private final Path rootLocation;

    @Autowired
    public DatabaseStorageService(StorageProperties properties) {

        if(properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be empty");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }


    @Override
    public void init() {
        log.info("Initializing FileSystemStorageService.");
    }

    @Override
    public String store(MultipartFile file) {

        String newFileName = generateFileName(file);
        log.info("New file name: " + newFileName);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(newFileName)
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw  new StorageException("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Fails to store file.", e );
        }
        return newFileName;
    }

    @Override
    @Transactional
    public byte[] storeDB(MultipartFile file) {

        String newFileName = generateFileName(file);
        byte[] contenido;
        log.info("New file name: " + newFileName);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(newFileName)
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw  new StorageException("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                contenido = new byte[(int) inputStream.available()];
                contenido = file.getBytes();

                System.out.println("Contenido lenght en storeDB:" + contenido.length);
            }
        } catch (IOException e) {
            throw new StorageException("Fails to store file.", e );
        }
        return contenido;
    }

    private String generateFileName(MultipartFile file){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd_HHmm");
        LocalDateTime date = LocalDateTime.now();
        String dateStr = dtf.format(date);

        String filename = file.getOriginalFilename();
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() -1 ) {
            return "";
        } else {
            return filename.substring(0, dotIndex ) + "-" +
                    dateStr +
                    "." +
                    filename.substring(dotIndex + 1);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            log.info("Root location: " + this.rootLocation.toAbsolutePath().toString());
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> ! path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files ", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw  new StorageException("Could not read file: " + filename, e);
        }
    }

    public void deleteAll() {
        try {
            log.info("Root location: " + this.rootLocation.toAbsolutePath().toString());
            List<Path> paths = Files.walk(this.rootLocation, 1)
                    .filter(path -> ! path.equals(this.rootLocation))
                    .collect(Collectors.toList()) ;
            paths.stream().forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files ", e);
        }
    }
}
