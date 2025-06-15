package edu.unicartagena.tcc.product.controller;

import edu.unicartagena.tcc.product.dto.CrearProductoRequest;
import edu.unicartagena.tcc.product.dto.ActualizarProductoRequest;
import edu.unicartagena.tcc.product.entity.Producto;
import edu.unicartagena.tcc.product.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestión de productos")
@SecurityRequirement(name = "bearerAuth")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<Producto> crearProducto(@RequestBody @Valid CrearProductoRequest request) {
        return ResponseEntity.ok(productoService.crearProducto(request));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista paginada de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<Page<Producto>> obtenerTodosLosProductos(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductos(pageable));
    }

    @GetMapping("/lista")
    @Operation(summary = "Obtener todos los productos sin paginación", description = "Obtiene una lista completa de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<List<Producto>> obtenerTodosLosProductosLista() {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductosLista());
    }

    @GetMapping("/lista/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<Producto> obtenerProductoPorId(
            @Parameter(description = "ID del producto a buscar", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<Producto> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar", required = true)
            @PathVariable Long id,
            @RequestBody @Valid ActualizarProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<String> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado exitosamente");
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre", description = "Busca productos que contengan el texto especificado en el nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<List<Producto>> buscarProductosPorNombre(
            @Parameter(description = "Texto a buscar en el nombre del producto", required = true)
            @RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarProductosPorNombre(nombre));
    }

    @GetMapping("/precio-rango")
    @Operation(summary = "Buscar productos por rango de precio", description = "Busca productos dentro de un rango de precios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<List<Producto>> buscarProductosPorRangoPrecio(
            @Parameter(description = "Precio mínimo", required = true)
            @RequestParam Double precioMin,
            @Parameter(description = "Precio máximo", required = true)
            @RequestParam Double precioMax) {
        return ResponseEntity.ok(productoService.buscarProductosPorRangoPrecio(precioMin, precioMax));
    }
}