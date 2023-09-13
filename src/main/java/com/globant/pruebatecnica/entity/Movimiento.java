package com.globant.pruebatecnica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @Column(name = "valor")
    private double valor;

    @Column(name = "saldo")
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "numero_cuenta")
    private Cuenta cuenta;
}