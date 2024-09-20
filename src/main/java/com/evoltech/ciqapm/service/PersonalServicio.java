package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalServicio {

    @Autowired
    PersonalRepository personalRepository;

    public PersonalServicio(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    public Personal addPersonal(Personal personal) {
        Personal newPersonal = personalRepository.save(personal);
        return  newPersonal;
    }

    public Personal findById(Long id) throws Exception {
        Optional<Personal> personal = personalRepository.findById(id);
        if (personal.isPresent()){
            return personal.get();
        } else {
            throw new Exception("No existe el id " + id);
        }
    }
}
