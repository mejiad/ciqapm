package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

@Slf4j
@MappedSuperclass
public abstract class BaseClass implements Serializable {

    @Column(name="status", length=10, unique=false)
    private String status = "Inicio";

    @Column
    private Date createDate;

    @Column
    private Date updateDate;

    @Column
    private String userUpdate;

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

    public Date getCreateDate(){
        return createDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @PrePersist
    private void prePersist(){
        status = "Creado";
        createDate = new Date();
        log.info("++++++ PrePersist." + this.getClass().getName());
    }

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
}
