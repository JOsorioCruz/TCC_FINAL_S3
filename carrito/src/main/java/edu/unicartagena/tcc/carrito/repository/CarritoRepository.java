package edu.unicartagena.tcc.carrito.repository;

import edu.unicartagena.tcc.carrito.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}