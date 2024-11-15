package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Conahcyt;
import com.evoltech.ciqapm.model.EmpConahcyt;
import com.evoltech.ciqapm.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpConahcytRepository extends JpaRepository<EmpConahcyt, Long> {

    List<EmpConahcyt> findByConahcyt(Conahcyt conahcyt);
}
