package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.dto.ConahcytDto;
import com.evoltech.ciqapm.dto.mapper.ModelMapperConverter;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.ConvocatoriaRepository;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConahcytService {

    @Autowired
    private final ConahcytRepository conahcytRepository;

    @Autowired
    private final ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    private final EmpleadoRepository personalRepository;

    @Autowired
    private final ModelMapperConverter converter;


    public ConahcytService(ConahcytRepository conahcytRepository, ConvocatoriaRepository convocatoriaRepository, EmpleadoRepository personalRepository, ModelMapperConverter modelMapperConverter) {
        this.conahcytRepository = conahcytRepository;
        this.convocatoriaRepository = convocatoriaRepository;
        this.personalRepository = personalRepository;
        this.converter = modelMapperConverter;
    }

    public ConahcytDto saveProyecto(ConahcytDto conacytDto) {

        Conahcyt conahcyt = converter.convert(conacytDto);
        conahcyt.setEstatus(Estado.CREACION);
        conahcyt.setTipoProyecto(TipoProyecto.CONAHCYT);

        Conahcyt res = conahcytRepository.save(conahcyt);

        ConahcytDto result = converter.convert(conahcyt);

        return result;
    }

    public List<ConahcytDto> findAll(){
        List<Conahcyt> conahcyts = conahcytRepository.findAll();
        List<ConahcytDto> dtos = conahcyts.stream().map(element -> converter.convert(element))
                .toList();

        return dtos;
    }

    public ConahcytDto createDto(Conahcyt conahcyt){
        return converter.convert(conahcyt);
    }
}
