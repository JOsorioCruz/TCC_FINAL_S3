package edu.unicartagena.tcc.product.service.impl;

import edu.unicartagena.tcc.product.dto.ActualizarProductoRequest;
import edu.unicartagena.tcc.product.dto.CrearProductoRequest;
import edu.unicartagena.tcc.product.entity.Producto;
import edu.unicartagena.tcc.product.exception.ProductoNoEncontradoException;
import edu.unicartagena.tcc.product.repository.ProductoRepository;
import edu.unicartagena.tcc.product.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Producto crearProducto(CrearProductoRequest request) {
        log.info("Creando nuevo producto: {}", request.getNombre());

        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .stock(request.getStock())
                .build();

        Producto productoGuardado = productoRepository.save(producto);
        log.info("Producto creado exitosamente con ID: {}", productoGuardado.getId());

        return productoGuardado;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> obtenerTodosLosProductos(Pageable pageable) {
        log.info("Obteniendo productos paginados - Página: {}, Tamaño: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodosLosProductosLista() {
        log.info("Obteniendo todos los productos como lista");
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Producto no encontrado con ID: {}", id);
                    return new ProductoNoEncontradoException("Producto no encontrado con ID: " + id);
                });
    }

    @Override
    public Producto actualizarProducto(Long id, ActualizarProductoRequest request) {
        log.info("Actualizando producto con ID: {}", id);

        Producto producto = obtenerProductoPorId(id);

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());

        Producto productoActualizado = productoRepository.save(producto);
        //Verificar porque puede tener fallas
        if (request.getStock() != null) {
            producto.setStock(request.getStock());
        }
        log.info("Producto actualizado exitosamente con ID: {}", id);

        return productoActualizado;
    }

    @Override
    public void eliminarProducto(Long id) {
        log.info("Eliminando producto con ID: {}", id);

        if (!productoRepository.existsById(id)) {
            log.error("Intento de eliminar producto no existente con ID: {}", id);
            throw new ProductoNoEncontradoException("Producto no encontrado con ID: " + id);
        }

        productoRepository.deleteById(id);
        log.info("Producto eliminado exitosamente con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorNombre(String nombre) {
        log.info("Buscando productos por nombre: {}", nombre);
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorRangoPrecio(Double precioMin, Double precioMax) {
        log.info("Buscando productos por rango de precio: {} - {}", precioMin, precioMax);
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorTexto(String texto) {
        log.info("Buscando productos por texto: {}", texto);
        return productoRepository.buscarPorTexto(texto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeProducto(Long id) {
        return productoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeProductoPorNombre(String nombre) {
        return productoRepository.existsByNombreIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarProductos() {
        return productoRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarProductosPorRangoPrecio(Double precioMin, Double precioMax) {
        return productoRepository.countByPrecioBetween(precioMin, precioMax);
    }
}