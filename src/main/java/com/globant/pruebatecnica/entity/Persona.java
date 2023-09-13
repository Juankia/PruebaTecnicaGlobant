package com.globant.pruebatecnica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "persona")
@Inheritance( strategy = InheritanceType.JOINED )
@Getter
@Setter
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "genero")
    private String genero;

    @Column(name = "edad")
    private int edad;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;
}

