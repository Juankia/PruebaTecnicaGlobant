package com.globant.pruebatecnica.controller;

import com.globant.pruebatecnica.dto.request.CrearCuentaRequest;
import com.globant.pruebatecnica.dto.response.CuentaConClienteResponse;
import com.globant.pruebatecnica.entity.Cliente;
import com.globant.pruebatecnica.entity.Cuenta;
import com.globant.pruebatecnica.repository.ClienteRepository;
import com.globant.pruebatecnica.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/crear")
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody CrearCuentaRequest request) {
        // Buscar al cliente por su ID
        Optional<Cliente> clienteOptional = clienteRepository.findById(request.getClienteId());

        if (!clienteOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Crear la nueva cuenta
        Cliente cliente = clienteOptional.get();
        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setTipoCuenta(request.getTipoCuenta());
        nuevaCuenta.setSaldoInicial(request.getSaldoInicial());
        nuevaCuenta.setCliente(cliente);
        nuevaCuenta.setEstado(true);

        Cuenta cuentaCreada = cuentaRepository.save(nuevaCuenta);
        return new ResponseEntity<>(cuentaCreada, HttpStatus.CREATED);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<CuentaConClienteResponse>> obtenerListaCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();

        List<CuentaConClienteResponse> listcuentasConClientes = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            Cliente cliente = cuenta.getCliente();
            CuentaConClienteResponse cuentaConCliente = new CuentaConClienteResponse();

            cuentaConCliente.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaConCliente.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaConCliente.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaConCliente.setEstado(cuenta.isEstado());
            cuentaConCliente.setNombreCliente(cliente.getNombre());
            cuentaConCliente.setIdentificacionCliente(cliente.getIdentificacion());

            listcuentasConClientes.add(cuentaConCliente);
        }

        return new ResponseEntity<>(listcuentasConClientes, HttpStatus.OK);
    }
}
