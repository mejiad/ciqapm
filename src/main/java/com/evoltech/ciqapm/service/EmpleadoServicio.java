package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Empleado;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoServicio {

    @Autowired
    EmpleadoRepository empleadoRepository;

    public EmpleadoServicio(EmpleadoRepository personalRepository) {
        this.empleadoRepository = personalRepository;
    }

    public Empleado addEmpleado(Empleado empleado) {
        Empleado newEmpleado = empleadoRepository.save(empleado);
        return  newEmpleado;
    }

    public Empleado findById(Long id) throws Exception {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()){
            return empleado.get();
        } else {
            throw new Exception("No existe el id " + id);
        }
    }
}
