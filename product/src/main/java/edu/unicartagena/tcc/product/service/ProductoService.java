package edu.unicartagena.tcc.product.service;

import edu.unicartagena.tcc.product.dto.ActualizarProductoRequest;
import edu.unicartagena.tcc.product.dto.CrearProductoRequest;
import edu.unicartagena.tcc.product.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    Producto crearProducto(CrearProductoRequest request);

    Page<Producto> obtenerTodosLosProductos(Pageable pageable);
    List<Producto> obtenerTodosLosProductosLista();
    Producto obtenerProductoPorId(Long id);

    Producto actualizarProducto(Long id, ActualizarProductoRequest request);

    void eliminarProducto(Long id);

    List<Producto> buscarProductosPorNombre(String nombre);
    List<Producto> buscarProductosPorRangoPrecio(Double precioMin, Double precioMax);
    List<Producto> buscarPorTexto(String texto);

    boolean existeProducto(Long id);
    boolean existeProductoPorNombre(String nombre);
    long contarProductos();
    long contarProductosPorRangoPrecio(Double precioMin, Double precioMax);
}