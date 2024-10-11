package com.finanzanas.Repository;

import com.finanzanas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCorreo(String correo);
    // Busca un usuario por correo

//    User findByCorreo(String correo);
//    mas metodos personalizados
}
