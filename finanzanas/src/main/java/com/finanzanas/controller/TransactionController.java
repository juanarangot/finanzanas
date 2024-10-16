package com.finanzanas.controller;

import com.finanzanas.Entity.TransactionRequest;
import com.finanzanas.Entity.User;
import com.finanzanas.Service.TransactionService;
import com.finanzanas.Service.UserService;
import com.finanzanas.Entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{idUsuario}")
    public ResponseEntity<List<Transaction>> obtenerMovimientosPorUser(@PathVariable Long idUsuario) {
        List<Transaction> transactions = transactionService.obtenerMovimientosPorUsuario(idUsuario);
        return ResponseEntity.ok(transactions);
    }

//    @PostMapping("/usuario/{idUsuario}")
//    public ResponseEntity<Transaction> crearTransaction(@PathVariable Long idUsuario, @RequestBody Transaction transaction) {
//        Transaction newTransaction = transactionService.crearTransaction(idUsuario, transaction);
//        return ResponseEntity.ok(newTransaction);
//    }

//    @PostMapping("/crear")
//    public ResponseEntity<Transaction> crearTransaction(@RequestParam(value = "tipo", required = false) String tipo,
//                                                        @RequestParam(value = "monto", required = false)Double monto,
//                                                        @RequestParam(value = "userId", required = true) Long userId,
//                                                        @RequestParam(value = "descripcion", required = false) String descripcion) {
//        // Agrega esta línea para confirmar si la solicitud llega al controlador
//        System.out.println("Solicitud recibida para crear transacción: Tipo=" + tipo + ", Monto=" + monto + ", UserId=" + userId);
//
//        // Obtener el usuario por ID (asegúrate que el userId es válido)
//        User user = userService.obtenerUsuarioPorId(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        // Crear la transacción
//        Transaction nuevoMovimiento = transactionService.crearTransaction(tipo, monto, user);
//        return ResponseEntity.ok(nuevoMovimiento);
//    }

//    @PostMapping("/crear")
//    public ResponseEntity<Transaction> crearTransaction(@RequestBody TransactionRequest request) {
//        User user = userService.obtenerUsuarioPorId(request.getUserId())
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        Transaction nuevoMovimiento = transactionService.crearTransaction(request.getTipo(), request.getMonto(), user);
//        return ResponseEntity.ok(nuevoMovimiento);
//    }


    @PostMapping("/crear")
    public ResponseEntity<Transaction> crearTransaction(@RequestBody TransactionRequest transactionRequest) {
        // Extraer los parámetros desde el objeto JSON
        String tipo = transactionRequest.getTipo();
        Double monto = transactionRequest.getMonto();
        Long userId = transactionRequest.getUserId();
        String descripcion = transactionRequest.getDescripcion();

        System.out.println("Solicitud recibida para crear transacción: Tipo=" + tipo + ", Monto=" + monto + ", UserId=" + userId);

        // Obtener el usuario por ID (asegúrate que el userId es válido)
        User user = userService.obtenerUsuarioPorId(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear la transacción
        Transaction nuevoMovimiento = transactionService.crearTransaction(tipo, monto, user, descripcion);
        return ResponseEntity.ok(nuevoMovimiento);
    }



}
