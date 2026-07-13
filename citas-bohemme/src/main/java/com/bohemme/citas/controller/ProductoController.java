package com.bohemme.citas.controller;

import com.bohemme.citas.exception.RecursoNoEncontradoException;
import com.bohemme.citas.model.Producto;
import com.bohemme.citas.repository.ProductoRepository;
import com.bohemme.citas.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Ajustar en producción al dominio real de bohemme.com
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoService productoService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    /**
     * Busca productos activos con filtros combinables. Todos los parámetros son opcionales.
     * Ejemplo: /api/productos?deporte=Running&deporte=Ciclismo&genero=Mujer&precioMax=50&orden=precioAsc
     */
    @GetMapping
    public List<Producto> buscar(
            @RequestParam(required = false) Set<String> deporte,
            @RequestParam(required = false) Set<String> genero,
            @RequestParam(required = false) Set<String> prenda,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(required = false) String talla,
            @RequestParam(required = false, defaultValue = "relevancia") String orden) {

        return productoService.buscar(deporte, genero, prenda, busqueda, precioMax, talla, orden);
    }

    // Listado completo (activos + inactivos), para el panel de administración
    @GetMapping("/admin")
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // Obtener un producto concreto (para precargar el formulario de edición)
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id " + id));
        return ResponseEntity.ok(producto);
    }

    // Alta de un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto guardado = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // Edición de un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto cambios) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id " + id));

        existente.setNombre(cambios.getNombre());
        existente.setDeporte(cambios.getDeporte());
        existente.setGenero(cambios.getGenero());
        existente.setPrenda(cambios.getPrenda());
        existente.setPrecio(cambios.getPrecio());
        existente.setTallas(cambios.getTallas());
        existente.setStock(cambios.getStock());
        existente.setActivo(cambios.isActivo());

        return ResponseEntity.ok(productoRepository.save(existente));
    }

    // Baja lógica: el producto deja de aparecer en la tienda pero no se borra de la base de datos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id " + id));
        existente.setActivo(false);
        productoRepository.save(existente);
        return ResponseEntity.noContent().build();
    }
}

