package com.globant.pruebatecnica.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearMovimientoRequest {
    private Long numeroCuenta;
    private String tipoMovimiento; // "Retiro" o "Deposito"
    private double valor;
}
