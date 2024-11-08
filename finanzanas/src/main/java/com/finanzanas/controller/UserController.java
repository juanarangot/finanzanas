package com.finanzanas.controller;

import com.finanzanas.DTO.UserUpdateDTO;
import com.finanzanas.Entity.Budget;
import com.finanzanas.Entity.User;
import com.finanzanas.Service.BudgetService;
import com.finanzanas.Service.UserService;
import com.finanzanas.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/crearx")
    public ResponseEntity<User> crearUsuario(@RequestBody User usuario) {
        User nuevoUsuario = userService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

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




    // Actualizar usuario cualquier atributo
//    @PutMapping("/actualizar/{id}")
//    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody UserUpdateDTO usuarioDTO) {
//        User usuario = userService.obtenerUsuarioPorId(id)
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        // Actualizar los atributos del usuario, solo los que se le pasen se actualizaran
//        if (usuarioDTO.getNombre() != null) usuario.setNombre(usuarioDTO.getNombre());
//        if (usuarioDTO.getCorreo() != null) usuario.setCorreo(usuarioDTO.getCorreo());
//        if (usuarioDTO.getPassword() != null) usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()));
//        if (usuarioDTO.getBalance() != null) usuario.setBalance(usuarioDTO.getBalance());
//        if (usuarioDTO.getObjetivoFinanciero() != null) usuario.setObjetivoFinanciero(usuarioDTO.getObjetivoFinanciero());
//
//        User usuarioActualizado = userService.actualizarUsuario(usuario);
//        return ResponseEntity.ok(usuarioActualizado);
//    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@PathVariable Long id, @RequestBody UserUpdateDTO usuarioDTO) {
        User usuario = userService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean correoActualizado = false;

        // Verificar y actualizar los atributos del usuario
        if (usuarioDTO.getNombre() != null) usuario.setNombre(usuarioDTO.getNombre());

        if (usuarioDTO.getCorreo() != null && !usuarioDTO.getCorreo().equals(usuario.getCorreo())) {
            usuario.setCorreo(usuarioDTO.getCorreo());
            correoActualizado = true;
        }

        if (usuarioDTO.getPassword() != null) usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()));
        if (usuarioDTO.getBalance() != null) usuario.setBalance(usuarioDTO.getBalance());
        if (usuarioDTO.getObjetivoFinanciero() != null) usuario.setObjetivoFinanciero(usuarioDTO.getObjetivoFinanciero());

        // Guardar los cambios en la base de datos
        User usuarioActualizado = userService.actualizarUsuario(usuario);

        // Preparar la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("usuario", usuarioActualizado);

        // Si el correo fue actualizado, generar y añadir el nuevo token JWT a la respuesta
        if (correoActualizado) {
            String nuevoJwt = jwtUtil.generateToken(usuarioActualizado.getCorreo());
            response.put("token", nuevoJwt);
        }

        return ResponseEntity.ok(response);
    }



    // Obtener balance
    @GetMapping("/{idUsuario}/balance")
    public ResponseEntity<BigDecimal> obtenerBalance(@PathVariable Long idUsuario) {
        BigDecimal balance = userService.obtenerBalance(idUsuario);
        return ResponseEntity.ok(balance);
    }


    // Obtener objetivo financiero
    @GetMapping("/{idUsuario}/objetivo")
    public ResponseEntity<String> obtenerObjetivoFinanciero(@PathVariable Long idUsuario) {
        String objetivoFinanciero = userService.obtenerObjetivoFinanciero(idUsuario);
        return ResponseEntity.ok(objetivoFinanciero);
    }

}
