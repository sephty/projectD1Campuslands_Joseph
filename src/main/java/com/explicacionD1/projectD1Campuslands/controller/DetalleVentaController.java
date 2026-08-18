package com.explicacionD1.projectD1Campuslands.controller;

import com.explicacionD1.projectD1Campuslands.dto.request.DetalleVentaRequest;
import com.explicacionD1.projectD1Campuslands.dto.response.DetalleVentaResponse;
import com.explicacionD1.projectD1Campuslands.service.DetalleVentaService;
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

@Tag(name = "Detalle de ventas", description = "procesa el CRUD de Detalle de ventas")
@RestController
//http:localhost/api/detalle
@RequestMapping("/api/detalle")
@RequiredArgsConstructor
public class DetalleVentaController {
    private final DetalleVentaService detalleVentaService;

    @Operation(
            summary = "Registrar un nuevo detalle de venta",
            description = "Recibe un objeto JSON con los detalles de la venta (productos, cantidades, cliente, etc.) y la guarda en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalle de venta registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del detalle de venta inválidos o error de validación")
    })
    @PostMapping
    public ResponseEntity<DetalleVentaResponse> crear(@RequestBody DetalleVentaRequest dto){
        System.out.println("ENTRA");
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleVentaService.crear(dto));
    }

    @Operation(
            summary = "Obtener todos los detalles de ventas",
            description = "Retorna un listado completo con todos los detalles registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de ventas obtenido exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<DetalleVentaResponse>> listar(){
        return ResponseEntity.ok(detalleVentaService.listarTodos());
    }

    @Operation(
            summary = "Obtener un detalle de venta por su ID",
            description = "Busca y devuelve los detalles estructurados de una venta específica utilizando su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe ninguna venta con el ID proporcionado")
    })
    //http://localhost:8080/api/detalle/2
    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaResponse> obtenerPorId(
            @Parameter(description = "ID único del detalle a consultar", example = "5", required = true)
            @PathVariable Long id){
        return ResponseEntity.ok(detalleVentaService.buscarPorId(id));
    }

    @Operation(
            summary = "Actualizar un detale existente por ID",
            description = "Reemplaza o modifica la información de una transacción basándose en su ID de ruta y el JSON enviado en el cuerpo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada corruptos o inválidos"),
            @ApiResponse(responseCode = "404", description = "La venta que intenta modificar no fue encontrada")
    })
    //http://localhost:8080/api/detalle/2
    @PutMapping("/{id}")
    public ResponseEntity<DetalleVentaResponse> actualizar(
            @Parameter(description = "ID del detalle que se desea modificar", example = "5", required = true)
            @PathVariable Long id, @RequestBody DetalleVentaRequest dto){
        return ResponseEntity.ok(detalleVentaService.actualizar(id, dto));
    }

    @Operation(
            summary = "Eliminar un detalle por ID",
            description = "Remueve de forma permanente el registro de la venta seleccionada del sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada exitosamente (sin contenido)"),
            @ApiResponse(responseCode = "404", description = "La venta que intenta eliminar no existe en la base de datos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la venta a eliminar", example = "5", required = true)
            @PathVariable Long id){
        detalleVentaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Filtrar un detalle por ID del producto",
            description = "Busca y devuelve los detalles estructurados usando el ID del producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalles con producto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El producto que intenta buscar no existe o no tiene ninguna venta en la base de datos")
    })
    //http://localhost:8080/api/detalle/producto/2
    @GetMapping("/producto/{id}")
    public ResponseEntity<List<DetalleVentaResponse>> listarPorIdProducto(
            @Parameter(description = "ID del producto del detalle a filtrar", example = "5", required = true)
            @PathVariable Long id){
        return ResponseEntity.ok(detalleVentaService.buscarPorIdProducto(id));
    }

    @Operation(
            summary = "Filtrar un detalle por ID de venta",
            description = "Busca y devuelve los detalles estructurados usando el ID de su venta."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalles con venta encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "La venta que intenta buscar no existe en la base de datos")
    })
    @GetMapping("/venta/{id}")
    public ResponseEntity<List<DetalleVentaResponse>> listarPorIdVenta(
            @Parameter(description = "ID de la venta a filtrar", example = "5", required = true)
            @PathVariable Long id){
        return ResponseEntity.ok(detalleVentaService.buscarPorIdVenta(id));
    }

    @Operation(
            summary = "Filtrar un detalle por cantidad menor o igual que",
            description = "Busca y devuelve detalles por igual o menor que la cantidad dada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalles encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Los detalles que cumplen con los requerimientos de cantidad no existen en la base de datos")
    })
    //http://localhost:8080/api/detalle/filtro?cantidades=3&stock=5
    @GetMapping("/filtro")
    public ResponseEntity<List<DetalleVentaResponse>> listarPorCantidadesMenorQue(
            @Parameter(description = "Cantidad de venta a filtrar menor o igual", example = "5", required = true)
            @RequestParam Double cantidades){
        return ResponseEntity.ok(detalleVentaService.filtrarPorCantidadesMenorOIgualQue(cantidades));
    }
}