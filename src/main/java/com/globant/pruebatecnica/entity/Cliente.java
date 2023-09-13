package com.globant.pruebatecnica.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(referencedColumnName="id")
@Getter
@Setter
public class Cliente extends Persona {

    private static final long serialVersionUID = 1L;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "estado")
    private boolean estado;
}
