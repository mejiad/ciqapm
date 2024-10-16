package com.evoltech.ciqapm.model.jpa;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
@MappedSuperclass
public abstract class BaseClass implements Serializable {

    @Column(name="status", length=10, unique=false)
    private String status = "Inicio";  // TODO: definir los status. Crear enum

    @Column
    private LocalDate createDate;

    @Column
    private LocalDate updateDate;

    @Column
    private String userUpdate;

    @Column
    private String createUser;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public LocalDate getCreateDate(){
        return createDate;
    }

    public void setCreateDate(LocalDate createDate){
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @PrePersist
    private void prePersist(){
        status = "Creado";
        createDate = LocalDate.now();
        log.info("++++++ PrePersist." + this.getClass().getName());
    }

    /*
    @PostPersist
    private void postPersist(){
        log.info("++++++ PostPersist." + status);
    }

    @PreRemove
    private void postRemove(){
        log.info("++++++ PostRemove." + this.getClass().getName());
    }

    @PreUpdate
    private void preUpdate(){
        log.info("++++++ PreUpdate." + this.getClass().getName());
    }

    @PostUpdate
    private void postUpdate() {
        log.info("++++++ PostUpdate." + this.getClass().getName());
    }

    @PostLoad
    private void postLoad() {
        log.info("++++++ PostLoad." + this.getClass().getName());
    }

     */
}
