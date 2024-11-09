package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByNombreContainsIgnoreCase(String nombre);
}
