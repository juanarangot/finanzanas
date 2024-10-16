package com.finanzanas.Repository;

import com.finanzanas.Entity.Transaction;
import com.finanzanas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepostitory extends JpaRepository<Transaction, Long> {
    // Método que busca todas las transacciones de un usuario en particular
    List<Transaction> findAllByUser(User user);

    // Obtener todos los movimientos de un usuario específico
    List<Transaction> findByUser(Optional<User> user);

//    @Query("SELECT t FROM Transaction t WHERE t.usuario.id_usuario = :userId")
//    List<Transaction> findByUserId(@Param("userId") Long userId);
}
