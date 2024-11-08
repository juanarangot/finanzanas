package com.finanzanas.Service;

import com.finanzanas.Entity.User;
import com.finanzanas.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repositorio de usuarios

    @Autowired
    @Qualifier("customPasswordEncoder")
    private PasswordEncoder passwordEncoder; // Encriptador de contraseñas


    /*
        Se extrae el correo del texto, ya que se recibe en el formato

    *  UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User
    * [Username=arangot@gmail.com, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true,
    *  AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=null,
    *  Granted Authorities=[ROLE_USER]]

        y solo necesitamos el correo, entonces lo extraigo con una expresion regular
    * */
    public static String extractEmail(String text) {
        String regex = "Username=(.*?),";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1); // Retorna el correo extraído
        }
        else if (text.contains("@")) {
            return text; // Si ya es un correo
        }
        return null; // Si no se encuentra un correo


    }

// L

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        System.out.println("AGUACATE");


        correo = extractEmail(correo); // Extraer el correo del texto sea cual sea el formato
        String finalCorreo = correo; // Correo final

        System.out.println("Correo: " + finalCorreo);

        User user = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + finalCorreo));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getCorreo())
                .password(user.getPassword())
                .roles("USER")  // Puedes ajustar roles según sea necesario
                .build();
    }

    public void registrarUsuario(User usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));  // Encriptar la contraseña
        userRepository.save(usuario);
    }
}
