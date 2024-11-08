package com.finanzanas.util;

import com.finanzanas.Service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;


import java.io.IOException;

//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//    /*
//     *  Filtro de solicitud JWT que intercepta todas las solicitudes entrantes y
//     * verifica la presencia de un token JWT.
//     *
//     * Si se encuentra un token JWT válido, el filtro extrae el nombre de usuario y
//     * establece la autenticación en el contexto de seguridad de Spring.
//     * */
//
//    // Inyectar el servicio de detalles de usuario y el utilitario JWT
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    // Inyectar el utilitario JWT para validar y extraer información del token
//    @Autowired
//    private JwtUtil jwtUtil;
//
//
//    // Método para filtrar las solicitudes entrantes
//    /*
//    * Este método intercepta todas las solicitudes entrantes y verifica la presencia de un token JWT.
//    * Si se encuentra un token JWT válido, el filtro extrae el nombre de usuario y establece la autenticación
//    * en el contexto de seguridad de Spring.
//    *
//    * Si el token ha expirado, se captura la excepción y se registra un mensaje de advertencia.
//    *
//    * Params:
//    * - HttpServletRequest request: solicitud HTTP entrante
//    * - HttpServletResponse response: respuesta HTTP
//    * - FilterChain chain: cadena de filtros de Spring Security para continuar con la solicitud
//    * */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        // Extraer el token JWT del encabezado de autorización
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        // Extraer el nombre de usuario del token JWT y establecer la autenticación
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            // Extraer el token JWT de la solicitud
//            jwt = authorizationHeader.substring(7);
//            try {
//                username = jwtUtil.extractUsername(jwt);
//            } catch (ExpiredJwtException e) {
//                logger.warn("JWT token has expired");
//            }
//        }
//
//        // Verificar si el nombre de usuario no es nulo y si no hay autenticación en el contexto de seguridad
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            // Cargar detalles de usuario y establecer la autenticación
//            var userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            // Si el token es válido, configura Spring Security para establecer la autenticación
//            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
//                // Crear UsernamePasswordAuthenticationToken usando el usuario, roles y credenciales
//                var authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                // Establecer los detalles de la solicitud actual
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                // Establecer la autenticación en el contexto de seguridad de Spring
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//        // Continuar con la cadena de filtros de Spring Security
//        chain.doFilter(request, response);
//    }


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt); // Extrae el correo electrónico correctamente
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, String.valueOf(userDetails))) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}



