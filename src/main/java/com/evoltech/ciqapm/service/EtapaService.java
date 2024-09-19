package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.repository.EtapaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtapaService {

    @Autowired
    EtapaRepository etapaRepository;

    public EtapaService(EtapaRepository etapaRepository) {
        this.etapaRepository = etapaRepository;
    }

    public Etapa save(Etapa etapa){
        Etapa newEtapa = etapaRepository.save(etapa);
        return  newEtapa;
    }

}
