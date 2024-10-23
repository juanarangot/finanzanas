package com.finanzanas.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private String tipo;
    private Double monto;
    private Long userId;
    private String descripcion;

    // Getters y setters

    /*
    * Esta clase se usa para recibir los datos de una transacción (movimiento)
    * y pasarlos al metodo crearTransaction en TransactionService.
    * esto mediante @ResquestBody en el controlador TransactionController.
    *
    *
    * Sigue usando una clase auxiliar (TransactionRequest con @RequestBody) para
    * mapear el JSON en tu controlador. Este es el enfoque correcto para manejar
    * solicitudes POST que envían datos en formato JSON.
    * */
}
