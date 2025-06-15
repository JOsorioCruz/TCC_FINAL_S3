package edu.unicartagena.tcc.carrito.service.impl;

import edu.unicartagena.tcc.carrito.dto.CrearCarritoRequest;
import edu.unicartagena.tcc.carrito.entity.Carrito;
import edu.unicartagena.tcc.carrito.repository.CarritoRepository;
import edu.unicartagena.tcc.carrito.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;

    @Override
    public Carrito agregarAlCarrito(CrearCarritoRequest request) {
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(request.getUsuarioId());
        carrito.setProductoId(request.getProductoId());
        carrito.setCantidad(request.getCantidad());
        return carritoRepository.save(carrito);
    }

    @Override
    public void eliminarCarrito(Long id) {
        carritoRepository.deleteById(id);
    }
}