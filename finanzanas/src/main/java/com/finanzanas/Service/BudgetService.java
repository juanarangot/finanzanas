package com.finanzanas.Service;

import com.finanzanas.Entity.Budget;
import com.finanzanas.Entity.User;
import com.finanzanas.Repository.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    UserService userService;

    // Método para guardar un presupuesto
    public Budget guardarPresupuesto(Budget budget) {
        return budgetRepository.save(budget);
    }

    // Método para obtener todos los presupuestos de un usuario por su ID
    public List<Budget> obtenerPresupuestosPorUsuario(User user) {
        return budgetRepository.findByUser(user);
    }


    // Crea un presupuesto y lo asocia a un usuario
    public Budget crearPresupuesto(User user, Budget presupuesto) {
        // Asignar el usuario al presupuesto
        presupuesto.setUser(user);

        // Guardar el presupuesto en la base de datos
        return budgetRepository.save(presupuesto);
    }

    // Actualiza un presupuesto
    // Método para actualizar un presupuesto
    @Transactional
    public Budget actualizarPresupuesto(Long userId, Long budgetId, Budget presupuestoActualizado) {
        // Verificar si el usuario existe
        User user = userService.obtenerUsuarioPorId(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el presupuesto existente por budgetId y verificar si pertenece al usuario
        Budget presupuestoExistente = budgetRepository.findByIdPresupuestoAndUser(budgetId, user)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado para el usuario"));

        // Actualizar los detalles del presupuesto
        presupuestoExistente.setNombre(presupuestoActualizado.getNombre());
        presupuestoExistente.setMonto(presupuestoActualizado.getMonto());
        presupuestoExistente.setDescripcion(presupuestoActualizado.getDescripcion());

        // Guardar los cambios
        return budgetRepository.save(presupuestoExistente);
    }

    // Método para eliminar un presupuesto
    @Transactional
    public void eliminarPresupuesto(Long userId, Long budgetId) {
        // Verificar si el usuario existe
        User user = userService.obtenerUsuarioPorId(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el presupuesto por id y verificar si pertenece al usuario
        Budget presupuesto = budgetRepository.findByIdPresupuestoAndUser(budgetId, user)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado para el usuario"));

        // Eliminar el presupuesto
        budgetRepository.delete(presupuesto);
    }
}
