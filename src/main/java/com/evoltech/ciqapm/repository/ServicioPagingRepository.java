package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Servicio;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioPagingRepository extends PagingAndSortingRepository<Servicio, Long> {
}
