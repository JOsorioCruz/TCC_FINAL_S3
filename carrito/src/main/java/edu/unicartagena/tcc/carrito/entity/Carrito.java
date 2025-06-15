// Carrito.java
package edu.unicartagena.tcc.carrito.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private Long productoId;

    private int cantidad;
}
