package com.finanzanas.Service;

import com.finanzanas.Entity.Transaction;
import com.finanzanas.Entity.User;
import com.finanzanas.Repository.TransactionRepostitory;
import com.finanzanas.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepostitory transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    Método para obtener todos los movimientos financieros de un usuario
    public List<Transaction> obtenerMovimientosPorUsuario(Long idUsuario) {
        return transactionRepository.findByUser(userRepository.findById(idUsuario));
    }

//    Método para crear un nuevo movimiento financiero (transacción)
//    public Transaction crearTransaction(Long idUsuario, Transaction transaction) {
//        transaction.setUser(userRepository.findById(idUsuario).orElseThrow());
//        return transactionRepostitory.save(transaction);
//    }

    // Método para crear una nueva transacción
//    public Transaction crearTransaction(String tipo, Double monto, User user, String descripcion) {
//        Transaction transaction = new Transaction(tipo, monto, LocalDateTime.now(), user, descripcion);
//        return transactionRepository.save(transaction); // Guardar la transacción
//    }

    @Transactional // Asegura que la transacción se complete o se revierta si hay un error
    public Transaction crearTransaction(String tipo, Double monto, User user, String descripcion) {
        // Crear la nueva transacción
        Transaction transaction = new Transaction(tipo, monto, LocalDateTime.now(), user, descripcion);

        // Actualizar el balance del usuario según el tipo de transacción
        BigDecimal nuevoBalance = user.getBalance();
        BigDecimal montoDecimal = BigDecimal.valueOf(monto); // Convertir el monto a BigDecimal

        if ("INGRESO".equalsIgnoreCase(tipo)) {
            nuevoBalance = nuevoBalance.add(montoDecimal); // Sumar el monto al balance
        } else if ("EGRESO".equalsIgnoreCase(tipo)) {
            nuevoBalance = nuevoBalance.subtract(montoDecimal); // Restar el monto al balance
        }

        // Establecer el nuevo balance en el usuario y guardar el cambio
        user.setBalance(nuevoBalance); // Actualizar el balance del usuario
        userService.actualizarUsuario(user);  // actualiza el balance en la base de datos

        // Guardar la transacción
        return transactionRepository.save(transaction); // Guardar la transacción
    }


}