package com.explicacionD1.projectD1Campuslands.controller;

import com.explicacionD1.projectD1Campuslands.dto.request.ProductoRequest;
import com.explicacionD1.projectD1Campuslands.dto.response.ProductoResponse;
import com.explicacionD1.projectD1Campuslands.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Producto", description = "procesa el CRUD de productos")
@RestController
//http://localhost:8080/api/productos
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;
    @Operation(
            summary = "Registrar un nuevo producto",
            description = "Requiere un cuerpo de petición (RequestBody) en formato JSON para registrar la información de un nuevo producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación")
    })
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody ProductoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(dto));
    }

    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve una lista completa con todos los productos registrados en el sistema sin necesidad de parámetros."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(){
        return ResponseEntity.ok(productoService.obtenerTodas());
    }

    @Operation(
            summary = "Filtrar todos los productos filtrados por nombre",
            description = "Filtra la lista de productos buscando coincidencia con el parámetro de consulta 'nombre' enviado en la URL."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente (puede retornar una lista vacía si no hay coincidencias)")
    })
    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<ProductoResponse>> mostrarPorNombre(
            @Parameter(description = "Nombre o palabra clave del producto a filtrar", example = "tomate", required = true)
            @RequestParam String nombre
    ){
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @Operation(
            summary = "Filtrar todos los productos filtrados por ID",
            description = "Busca y devuelve los detalles de un producto específico utilizando su identificador único numérico en la ruta."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún producto con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @Parameter(description = "ID único del producto a buscar", example = "2", required = true)
            @PathVariable Long id
    ){
        return ResponseEntity.ok(productoService.obtenerrPorId(id));
    }

    @Operation(
            summary = "Actualizar los productos seleccionados por ID",
            description = "Modifica los datos de un producto basándose en su ID de la ruta y en la información actualizada provista en el cuerpo JSON."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación"),
            @ApiResponse(responseCode = "404", description = "El producto que intenta actualizar no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @Parameter(description = "ID del producto que se va a modificar", example = "2", required = true)
            @PathVariable Long id,
            @RequestBody ProductoRequest dto
    ){
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    @Operation(
            summary = "Remover de la base de datos los productos seleccionados por ID",
            description = "Borra de forma permanente un producto del sistema utilizando su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente (sin contenido en la respuesta)"),
            @ApiResponse(responseCode = "404", description = "El producto que intenta eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID del producto que se va a eliminar", example = "2", required = true)
            @PathVariable Long id
    ){
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
