package com.evoltech.ciqapm.dto.mapper;

import com.evoltech.ciqapm.dto.ConahcytDto;
import com.evoltech.ciqapm.dto.ProyectoAbstractDto;
import com.evoltech.ciqapm.model.Conahcyt;
import com.evoltech.ciqapm.model.Proyecto;

public interface Converter {

    Conahcyt convert(ConahcytDto conhacytDto);
    ConahcytDto convert(Conahcyt conahcyt);
}
