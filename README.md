# Finanzanas - Aplicación de Finanzas Personales

## Descripción

Finanzanas es una aplicación de finanzas personales diseñada para gestionar movimientos financieros de los usuarios, permitiendo el registro de ingresos y egresos, y una vista detallada de las transacciones. 

La aplicación está dividida en dos partes:
1. **Frontend**: Construido con React, maneja la interfaz de usuario.
2. **Backend**: Implementado en Spring Boot, expone una API RESTful que incluye autenticación basada en tokens JWT.

## Requisitos previos

Para ejecutar esta aplicación de manera local, necesitarás:
- Node.js (v14 o superior) y npm (incluido con Node.js) para el frontend.
- Java 17 y Maven para el backend.
- MySQL para la base de datos.

### Instalación de dependencias

#### Instalación del frontend

Primero, navega a la carpeta `frontend/` y ejecuta los siguientes comandos:

```bash
cd frontend/
npm install
```

#### Instalación del backend

Navega a la carpeta `backend/` y ejecuta el siguiente comando para descargar las dependencias de Maven:

```bash
cd backend/
mvn clean install
```

### Configuración de la base de datos

La aplicación utiliza MySQL como base de datos. Para configurarla en tu entorno local:

1. Crea una base de datos MySQL llamada `finanzanas`:

```sql
CREATE DATABASE finanzanas;
```

2. Configura las credenciales de acceso a la base de datos en el archivo `application.properties` ubicado en `backend/src/main/resources/`. Aquí deberás especificar el nombre de la base de datos, el usuario, y la contraseña:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finanzanas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### Generación de JWT Key

Para generar una clave secreta que se utilizará para firmar los tokens JWT, puedes agregar el siguiente código en tu `JwtUtil` en la parte donde defines la clave secreta:

```java
Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
```

O puedes generar una clave secreta manualmente (de al menos 256 bits) y agregarla a tu archivo `application.properties`:

```properties
jwt.secret=tu_clave_secreta
```

### Ejecución de la aplicación

#### Ejecución del backend

Navega al directorio `backend/` y ejecuta el siguiente comando para iniciar el servidor de Spring Boot:

```bash
mvn spring-boot:run
```

La API se ejecutará en `http://localhost:8080`.

#### Ejecución del frontend

Navega a la carpeta `frontend/` y ejecuta:

```bash
npm start
```

La aplicación frontend se ejecutará en `http://localhost:3000`.

### Uso de la aplicación

1. **Registro**: Los usuarios pueden registrarse mediante el formulario de registro disponible en la ruta `/register`.
2. **Inicio de sesión**: Luego de registrar una cuenta, puedes iniciar sesión en la ruta `/login`. Al iniciar sesión correctamente, el token JWT se guardará en el `localStorage`.
3. **Protección de rutas**: Algunas rutas están protegidas y requieren el token JWT para acceder. El token se envía automáticamente en cada solicitud gracias a los interceptores configurados con Axios.

### Cerrar sesión

Para cerrar sesión, el token JWT almacenado en el `localStorage` se elimina al presionar el botón de "Cerrar Sesión". Esto te redirigirá al formulario de inicio de sesión.

### Endpoints principales de la API

- **POST** `/api/auth/register`: Registra un nuevo usuario.
- **POST** `/api/auth/login`: Inicia sesión y devuelve un token JWT.
- **GET** `/api/movimientos/user/{idUsuario}`: Obtiene las transacciones de un usuario.
- **POST** `/api/movimientos/crear`: Crea una nueva transacción.

### Manejo de Errores

- **CORS**: La configuración de CORS permite solicitudes desde `http://localhost:3000`. Asegúrate de que esté configurado correctamente en tu `SecurityConfig`.
- **Autenticación fallida**: Si una solicitud no incluye el token JWT o es inválido, el servidor retornará un error 403.

### Contribuir

1. Realiza un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y realiza un commit (`git commit -m 'Agregar nueva funcionalidad'`).
4. Haz un push de la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

---
