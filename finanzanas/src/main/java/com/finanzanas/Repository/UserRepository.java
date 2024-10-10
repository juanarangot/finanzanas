package com.finanzanas.Repository;

import com.finanzanas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    mas metodos personalizados
}
