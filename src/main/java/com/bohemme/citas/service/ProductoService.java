package com.bohemme.citas.service;

import com.bohemme.citas.model.Producto;
import com.bohemme.citas.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Filtra y ordena el catálogo activo según los criterios recibidos.
     * Todos los parámetros son opcionales: si vienen vacíos/null, no se aplican.
     */
    public List<Producto> buscar(Set<String> deportes, Set<String> generos, Set<String> prendas,
                                  String busqueda, BigDecimal precioMax, String talla, String orden) {

        List<Producto> resultado = productoRepository.findByActivoTrue().stream()
                .filter(p -> deportes == null || deportes.isEmpty() || deportes.contains(p.getDeporte()))
                .filter(p -> generos == null || generos.isEmpty() || generos.contains(p.getGenero()))
                .filter(p -> prendas == null || prendas.isEmpty() || prendas.contains(p.getPrenda()))
                .filter(p -> busqueda == null || busqueda.isBlank()
                        || p.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
                .filter(p -> precioMax == null || p.getPrecio().compareTo(precioMax) <= 0)
                .filter(p -> talla == null || talla.isBlank() || p.getTallas().contains(talla))
                .collect(Collectors.toList());

        if (orden != null) {
            switch (orden) {
                case "precioAsc" -> resultado.sort(Comparator.comparing(Producto::getPrecio));
                case "precioDesc" -> resultado.sort(Comparator.comparing(Producto::getPrecio).reversed());
                case "nombre" -> resultado.sort(Comparator.comparing(Producto::getNombre));
                default -> { /* relevancia: se mantiene el orden natural */ }
            }
        }

        return resultado;
    }
}
