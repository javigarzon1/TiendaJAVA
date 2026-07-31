package com.JAVI.citas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JAVI.citas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByTelefono(String telefono);
}
