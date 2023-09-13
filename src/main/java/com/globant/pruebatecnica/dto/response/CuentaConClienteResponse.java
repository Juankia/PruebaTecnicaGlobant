package com.globant.pruebatecnica.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaConClienteResponse {
    private Long numeroCuenta;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;
    private String nombreCliente;
    private String identificacionCliente;

}
