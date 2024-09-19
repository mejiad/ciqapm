package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
