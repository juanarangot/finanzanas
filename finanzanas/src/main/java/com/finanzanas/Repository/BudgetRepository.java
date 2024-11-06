package com.finanzanas.Repository;

import com.finanzanas.Entity.Budget;
import com.finanzanas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
//    List<Budget> findByUserId(Long userId);
    List<Budget> findByUser(User user);

    Optional<Budget> findByIdPresupuestoAndUser(Long idPresupuesto, User user);
//    Optional<Budget> findByidAndUser(Long idPresupuesto, User user);
}
