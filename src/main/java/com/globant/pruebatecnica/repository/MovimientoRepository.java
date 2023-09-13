package com.globant.pruebatecnica.repository;

import com.globant.pruebatecnica.entity.Cuenta;
import com.globant.pruebatecnica.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuenta(Cuenta cuenta);
    Movimiento findTopByCuentaOrderByFechaDesc(Cuenta cuenta);
    List<Movimiento> findByCuentaClienteIdAndFechaBetween(
        Long clienteId, Date fechaInicio, Date fechaFin);
}
