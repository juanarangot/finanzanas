package com.finanzanas.controller;

import com.finanzanas.Entity.Budget;
import com.finanzanas.Entity.User;
import com.finanzanas.Service.BudgetService;
import com.finanzanas.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;


//    @PostMapping("/crearx")
//    public ResponseEntity<User> crearUsuario(@RequestBody User usuario) {
//        User nuevoUsuario = userService.crearUsuario(usuario);
//        return ResponseEntity.ok(nuevoUsuario);
//    }

    @GetMapping("/todos")
    public ResponseEntity<List<User>> obtenerTodosLosUsuarios() {
        List<User> usuarios = userService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<User> usuario = userService.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        userService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }


//    Movimientos financieros de un usuario

    @PostMapping("/{idUsuario}/objetivo")
    public ResponseEntity<User> definirObjetivoFinanciero(@PathVariable Long idUsuario, @RequestBody String objetivo) {
        User user = userService.obtenerUsuarioPorId(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setObjetivoFinanciero(objetivo);
        userService.registrarUsuario(user);
        return ResponseEntity.ok(user);
    }



    // Obtener todos los presupuestos de un usuario
    @GetMapping("/{userId}/presupuestos")
    public ResponseEntity<List<Budget>> obtenerPresupuestosPorUsuario(@PathVariable Long userId) {
        List<Budget> presupuestos = budgetService
                .obtenerPresupuestosPorUsuario(userService.obtenerUsuarioPorId(userId)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

        return ResponseEntity.ok(presupuestos);
    }

    // Crear un presupuesto para un usuario
    @PostMapping("/{userId}/presupuestos")
    public ResponseEntity<Budget> crearPresupuesto(@PathVariable Long userId, @RequestBody Budget presupuesto) {
        User user = userService.obtenerUsuarioPorId(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Budget nuevoPresupuesto = budgetService.crearPresupuesto(user, presupuesto);
        return ResponseEntity.ok(nuevoPresupuesto);
    }

    // Actualizar un presupuesto de un usuario
    @PutMapping("/{userId}/presupuestos/{budgetId}")
    public ResponseEntity<Budget> actualizarPresupuesto(@PathVariable Long userId, @PathVariable Long budgetId, @RequestBody Budget presupuestoActualizado) {
        Budget presupuesto = budgetService.actualizarPresupuesto(userId, budgetId, presupuestoActualizado);
        return ResponseEntity.ok(presupuesto);
    }

    // Eliminar un presupuesto de un usuario
    @DeleteMapping("/{userId}/presupuestos/{budgetId}")
    public ResponseEntity<Void> eliminarPresupuesto(@PathVariable Long userId, @PathVariable Long budgetId) {
        budgetService.eliminarPresupuesto(userId, budgetId);
        return ResponseEntity.noContent().build();
    }

}
