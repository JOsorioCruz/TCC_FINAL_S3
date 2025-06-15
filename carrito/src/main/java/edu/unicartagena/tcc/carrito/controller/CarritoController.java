package edu.unicartagena.tcc.carrito.controller;

import edu.unicartagena.tcc.carrito.dto.CrearCarritoRequest;
import edu.unicartagena.tcc.carrito.entity.Carrito;
import edu.unicartagena.tcc.carrito.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarProducto(@RequestBody @Valid CrearCarritoRequest request) {
        return ResponseEntity.ok(carritoService.agregarAlCarrito(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.ok("Carrito eliminado exitosamente");
    }
}