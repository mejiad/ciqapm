package com.evoltech.ciqapm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RatePersonal {

    @Id
    @GeneratedValue
    private Long id;

    /*
    private Personal personal;

     */

    private Double rate;
}
