package com.finanzanas.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity // Indica que es una entidad de JPA
@Data // Lombok genera los getters y setters
@Table(name = "presupuestos") // Nombre de la tabla en la base de datos
public class Budget {

    // ID del presupuesto, clave primaria, autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presupuesto") // Nombre de la columna en la base de datos
    private Long idPresupuesto; // ID del presupuesto

    // campo de nombre, obligatorio
    @Column(nullable = false)
    private String nombre; // Nombre del presupuesto

    // campo de monto, obligatorio
    @Column(nullable = false)
    private BigDecimal monto; // Monto del presupuesto

    // Nuevo campo de descripción, opcional
    @Column(length = 500)
    private String descripcion; // Descripción opcional del presupuesto

    // Relación muchos a uno con User, para saber a qué usuario pertenece el presupuesto
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con User, carga perezosa, no se carga automáticamente
    @JoinColumn(name = "id_usuario", nullable = false) // Llave foránea
    @JsonIgnore // Ignorar este campo al serializar a JSON para evitar referencias circulares
    private User user;

}
