package com.explicacionD1.projectD1Campuslands.controller;

import com.explicacionD1.projectD1Campuslands.dto.request.VentaRequest;
import com.explicacionD1.projectD1Campuslands.dto.response.VentaResponse;
import com.explicacionD1.projectD1Campuslands.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Venta", description = "procesa el CRUD de ventas")
@RestController
@RequestMapping("/api/venta")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;
    @Operation(
            summary = "Registrar una nueva venta",
            description = "Recibe un objeto JSON con los detalles de la venta (productos, cantidades, cliente, etc.) y la guarda en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la venta inválidos o error de validación")
    })
    @PostMapping
    public ResponseEntity<VentaResponse> crear(@RequestBody VentaRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.guardar(dto));
    }

    @Operation(
            summary = "Obtener todas las ventas",
            description = "Retorna un listado completo con todas las ventas registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de ventas obtenido exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<VentaResponse>> listar(){
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @Operation(
            summary = "Filtrar ventas por un monto mínimo",
            description = "Busca y devuelve todas las ventas cuyo valor total sea mayor o igual al monto especificado en el parámetro."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda por monto realizada exitosamente")
    })
    @GetMapping("/mayorOIgualQue")
    public ResponseEntity<List<VentaResponse>> filtrarPorTotalMayorOIgualQue(
            @Parameter(description = "Monto mínimo del total de la venta", example = "150.50", required = true)
            @RequestParam BigDecimal total
    ){
        return ResponseEntity.ok(ventaService.buscarPorTotalMayorQue(total));
    }

    @Operation(
            summary = "Filtrar ventas entre un rango de fechas",
            description = "Permite consultar el historial de ventas realizadas dentro de un periodo de tiempo específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtro por rango de fechas aplicado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido o rango incorrecto")
    })
    @GetMapping("/filtroEntreFechas")
    public ResponseEntity<List<VentaResponse>> filtrarEntreFechas(
            @Parameter(description = "Fecha de inicio del reporte (ej. YYYY-MM-DD)", example = "2026-01-01", required = true)
            @RequestParam String fechaInicio,
            @Parameter(description = "Fecha de fin del reporte (ej. YYYY-MM-DD)", example = "2026-12-31", required = true)
            @RequestParam String fechaFin
    ){
        return ResponseEntity.ok(ventaService.filtrarEntreFechas(fechaInicio, fechaFin));
    }

    @Operation(
            summary = "Obtener una venta por su ID",
            description = "Busca y devuelve los detalles estructurados de una venta específica utilizando su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe ninguna venta con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerPorId(
            @Parameter(description = "ID único de la venta a consultar", example = "5", required = true)
            @PathVariable Long id
    ){
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @Operation(
            summary = "Actualizar una venta existente por ID",
            description = "Reemplaza o modifica la información de una transacción basándose en su ID de ruta y el JSON enviado en el cuerpo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada corruptos o inválidos"),
            @ApiResponse(responseCode = "404", description = "La venta que intenta modificar no fue encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponse> actualizar(
            @Parameter(description = "ID de la venta que se desea modificar", example = "5", required = true)
            @PathVariable Long id,
            @RequestBody VentaRequest dto
    ){
        return ResponseEntity.ok(ventaService.actualizar(id, dto));
    }

    @Operation(
            summary = "Eliminar una venta por ID",
            description = "Remueve de forma permanente el registro de la venta seleccionada del sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada exitosamente (sin contenido)"),
            @ApiResponse(responseCode = "404", description = "La venta que intenta eliminar no existe en la base de datos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID de la venta a eliminar", example = "5", required = true)
            @PathVariable Long id
    ){
        ventaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
