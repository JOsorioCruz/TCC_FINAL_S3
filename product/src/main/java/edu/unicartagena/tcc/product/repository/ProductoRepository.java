package edu.unicartagena.tcc.product.repository;

import edu.unicartagena.tcc.product.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);

    List<Producto> findByNombreIgnoreCase(String nombre);

    @Query("SELECT p FROM Producto p WHERE " +
            "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
            "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Producto> buscarPorTexto(@Param("texto") String texto);

    long countByPrecioBetween(Double precioMin, Double precioMax);

    List<Producto> findAllByOrderByPrecioAsc();
    List<Producto> findAllByOrderByPrecioDesc();

    boolean existsByNombreIgnoreCase(String nombre);
}