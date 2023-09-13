package com.globant.pruebatecnica.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearCuentaRequest {
    private Long clienteId;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;
}
