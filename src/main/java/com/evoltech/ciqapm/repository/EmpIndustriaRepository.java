package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Conahcyt;
import com.evoltech.ciqapm.model.EmpConahcyt;
import com.evoltech.ciqapm.model.EmpIndustria;
import com.evoltech.ciqapm.model.Industria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpIndustriaRepository extends JpaRepository<EmpIndustria, Long> {

    List<EmpIndustria> findByIndustria(Industria industria);
}
