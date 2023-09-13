package com.globant.pruebatecnica.repository;

import com.globant.pruebatecnica.entity.Cliente;
import com.globant.pruebatecnica.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByCliente(Cliente cliente);
}
