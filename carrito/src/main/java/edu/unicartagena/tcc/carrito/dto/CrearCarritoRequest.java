package edu.unicartagena.tcc.carrito.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearCarritoRequest {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long productoId;

    @Min(1)
    private Integer cantidad;
}