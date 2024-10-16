package com.finanzanas.Service;

import com.finanzanas.Entity.Transaction;
import com.finanzanas.Entity.User;
import com.finanzanas.Repository.TransactionRepostitory;
import com.finanzanas.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepostitory transactionRepostitory;

    @Autowired
    private UserRepository userRepository;

//    Método para obtener todos los movimientos financieros de un usuario
    public List<Transaction> obtenerMovimientosPorUsuario(Long idUsuario) {
        return transactionRepostitory.findByUser(userRepository.findById(idUsuario));
    }

//    Método para crear un nuevo movimiento financiero (transacción)
//    public Transaction crearTransaction(Long idUsuario, Transaction transaction) {
//        transaction.setUser(userRepository.findById(idUsuario).orElseThrow());
//        return transactionRepostitory.save(transaction);
//    }

    // Método para crear una nueva transacción
    public Transaction crearTransaction(String tipo, Double monto, User user, String descripcion) {
        Transaction transaction = new Transaction(tipo, monto, LocalDateTime.now(), user, descripcion);
        return transactionRepostitory.save(transaction); // Guardar la transacción
    }
}