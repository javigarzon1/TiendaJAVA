package com.JAVI.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bohemme.citas.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
}
