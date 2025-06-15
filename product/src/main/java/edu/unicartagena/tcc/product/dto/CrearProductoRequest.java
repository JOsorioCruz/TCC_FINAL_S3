package edu.unicartagena.tcc.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Request para crear un nuevo producto")
public class CrearProductoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre del producto", example = "Cerveza Águila", required = true)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Schema(description = "Descripción del producto", example = "Cerveza nacional colombiana")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio del producto", example = "2500.00", required = true)
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 1, message = "El stock debe ser mayor a 0")
    @Schema(description = "Cantidad de stock disponible", example = "1", required = true)
    private Integer stock;
}