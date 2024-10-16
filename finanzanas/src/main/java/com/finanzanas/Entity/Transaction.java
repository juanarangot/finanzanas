package com.finanzanas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "movimientos") // Nombre de la tabla en la base de datos
// Clase que representa una transacción financiera (movimiento)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    public enum TipoMovimiento {
        INGRESO,
        EGRESO
    }

//    @Column(nullable = false, name = "tipo_movimiento")
//    private String tipo; // Puede ser "INGRESO" o "EGRESO"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tipo_movimiento")
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private Double monto; // El monto de la transacción (ej 15.95)

    @Column(nullable = false)
    private LocalDateTime fecha; // Fecha del movimiento (ej 2021-10-15T12:00:00)

    @Column(length = 255)
    private String descripcion; // Una descripción opcional del movimiento


//    Llave foránea que relaciona la transacción con un usuario en la base de datos
//    para saber a qué usuario pertenece la transacción (quién la hizo)
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con User
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private User user;

    // Constructor vacío
    public Transaction() {}

    // Constructor con parámetros
    public Transaction(String tipo, Double monto, LocalDateTime fecha, User user, String descripcion) {
        this.tipo = TipoMovimiento.valueOf(tipo); // Convertir el String a un valor del enum TipoMovimiento
        this.monto = monto;
        this.fecha = fecha;
        this.user = user;
        this.descripcion = descripcion;
    }

}
