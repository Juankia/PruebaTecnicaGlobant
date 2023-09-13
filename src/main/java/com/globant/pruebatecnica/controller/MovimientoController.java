package com.globant.pruebatecnica.controller;

import com.globant.pruebatecnica.dto.request.CrearMovimientoRequest;
import com.globant.pruebatecnica.dto.response.MovimientoDetalleResponse;
import com.globant.pruebatecnica.entity.Cuenta;
import com.globant.pruebatecnica.entity.Movimiento;
import com.globant.pruebatecnica.repository.CuentaRepository;
import com.globant.pruebatecnica.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @PostMapping("/crear")
    public ResponseEntity<?> crearMovimiento(@RequestBody CrearMovimientoRequest request) {
        // Buscar la cuenta por su número de cuenta
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(request.getNumeroCuenta());

        if (!cuentaOptional.isPresent()) {
            return new ResponseEntity<>("Cuenta no encontrada", HttpStatus.NOT_FOUND);
        }

        // Obtener la cuenta y el último movimiento
        Cuenta cuenta = cuentaOptional.get();
        Movimiento ultimoMovimiento = movimientoRepository.findTopByCuentaOrderByFechaDesc(cuenta);

        // Validar si el saldo es suficiente para un retiro
        if (request.getTipoMovimiento().equals("Retiro")) {
            if (ultimoMovimiento == null) {
                if (cuenta.getSaldoInicial() < request.getValor()) {
                    return new ResponseEntity<>("Saldo no disponible", HttpStatus.BAD_REQUEST);
                }
            }else if (ultimoMovimiento == null || ultimoMovimiento.getSaldo() < request.getValor()) {
                return new ResponseEntity<>("Saldo no disponible", HttpStatus.BAD_REQUEST);
            }
        }

        // Crear el nuevo movimiento
        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setFecha(new Date());
        nuevoMovimiento.setTipoMovimiento(request.getTipoMovimiento());
        nuevoMovimiento.setValor(request.getValor());
        // Asociar el movimiento con la cuenta
        nuevoMovimiento.setCuenta(cuenta);

        // Calcular el saldo actualizado
        if (ultimoMovimiento == null) {
            if (request.getTipoMovimiento().equals("Retiro")) {
                nuevoMovimiento.setSaldo(cuenta.getSaldoInicial() - request.getValor());
            } else if (request.getTipoMovimiento().equals("Deposito")) {
                nuevoMovimiento.setSaldo(cuenta.getSaldoInicial() + request.getValor());
            } else {
                return new ResponseEntity<>("Tipo de movimiento no válido", HttpStatus.BAD_REQUEST);
            }
        } else {
            if (request.getTipoMovimiento().equals("Retiro")) {
                nuevoMovimiento.setSaldo(ultimoMovimiento.getSaldo() - request.getValor());
            } else if (request.getTipoMovimiento().equals("Deposito")) {
                nuevoMovimiento.setSaldo(ultimoMovimiento.getSaldo() + request.getValor());
            } else {
                return new ResponseEntity<>("Tipo de movimiento no válido", HttpStatus.BAD_REQUEST);
            }
        }

        Movimiento movimientoCreado = movimientoRepository.save(nuevoMovimiento);
        return new ResponseEntity<>(movimientoCreado, HttpStatus.CREATED);
    }

    @GetMapping("/listadoPorFechas")
    public ResponseEntity<List<MovimientoDetalleResponse>> obtenerMovimientosPorFechas(
        @RequestParam("clienteId") Long clienteId,
        @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        // Obtener todos los movimientos para el cliente dentro del rango de fechas
        List<Movimiento> movimientos = movimientoRepository.findByCuentaClienteIdAndFechaBetween(clienteId, fechaInicio, fechaFin);

        List<MovimientoDetalleResponse> movimientosDetalle = new ArrayList<>();
        for (Movimiento movimiento : movimientos) {
            MovimientoDetalleResponse movimientoDetalle = new MovimientoDetalleResponse();

            movimientoDetalle.setFecha(movimiento.getFecha());
            movimientoDetalle.setCliente(movimiento.getCuenta().getCliente().getNombre());
            movimientoDetalle.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
            movimientoDetalle.setTipoCuenta(movimiento.getCuenta().getTipoCuenta());
            movimientoDetalle.setSaldoInicial(movimiento.getCuenta().getSaldoInicial());
            movimientoDetalle.setEstado(movimiento.getCuenta().isEstado());
            movimientoDetalle.setTipoMovimiento(movimiento.getTipoMovimiento());
            movimientoDetalle.setValor(movimiento.getValor());
            movimientoDetalle.setSaldo(movimiento.getSaldo());

            movimientosDetalle.add(movimientoDetalle);
        }

        return new ResponseEntity<>(movimientosDetalle, HttpStatus.OK);
    }
}
