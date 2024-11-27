package com.evoltech.ciqapm.dto.mapper;

import com.evoltech.ciqapm.dto.ConahcytDto;
import com.evoltech.ciqapm.model.Conahcyt;
import com.evoltech.ciqapm.model.Proyecto;
import org.modelmapper.ModelMapper;

public class ModelMapperConverter implements Converter {

    private ModelMapper modelMapper;

    public ModelMapperConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Conahcyt convert(ConahcytDto conahcytDto) {
        return modelMapper.map(conahcytDto, Conahcyt.class );
    }

    @Override
    public ConahcytDto convert(Conahcyt conahcyt) {
        return modelMapper.map(conahcyt, ConahcytDto.class);
    }
}
