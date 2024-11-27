package com.evoltech.ciqapm.config;

import com.evoltech.ciqapm.dto.mapper.ModelMapperConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public ModelMapperConverter modelMapperCoverter(){
        ModelMapper modelMapper = new ModelMapper();
        return new ModelMapperConverter(modelMapper);
    }
}
