package edu.unicartagena.tcc.carrito.service;

import edu.unicartagena.tcc.carrito.dto.CrearCarritoRequest;
import edu.unicartagena.tcc.carrito.entity.Carrito;

public interface CarritoService {
    Carrito agregarAlCarrito(CrearCarritoRequest request);
    void eliminarCarrito(Long id);
}