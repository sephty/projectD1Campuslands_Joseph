package com.explicacionD1.projectD1Campuslands.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductoRequest(
        @NotBlank(message = "No se permite el nombre vacío.")
        @Size(min=2, max = 50, message = "Error, el nombre debe tener entre 2 y 50 caracteres")
        String nombre,
        @NotBlank(message = "No se permite la descripcion vacía.")
        @Size(min=2, max = 50, message = "Error, el nombre debe tener entre 2 y 50 caracteres")
        String descripcion,
        @NotNull(message = "El precio de compra no puede estar nulo")
        @Positive(message = "El precio de compra debe ser positivo")
        @Digits(integer = 10, fraction = 2, message = "El precio de compra debe tener maximo 10 digitos y 2 decimales")
        BigDecimal precioCompra,
        @NotNull(message = "El precio de venta no puede estar nulo")
        @Positive(message = "El precio de venta debe ser positivo")
        @Digits(integer = 10, fraction = 2, message = "El precio de venta debe tener maximo 10 digitos y 2 decimales")
        BigDecimal precioVenta) {
}