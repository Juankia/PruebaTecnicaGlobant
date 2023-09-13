package com.globant.pruebatecnica.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MovimientoDetalleResponse {
    private Date fecha;
    private String cliente;
    private Long numeroCuenta;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
}
